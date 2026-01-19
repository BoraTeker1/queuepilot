package com.queuepilot.queuepilot.service;

import com.queuepilot.queuepilot.api.dto.EventIngestRequest;

import com.queuepilot.queuepilot.api.dto.IncidentResponse;
import com.queuepilot.queuepilot.domain.Event;
import com.queuepilot.queuepilot.domain.IdempotencyKey;
import com.queuepilot.queuepilot.domain.Incident;
import com.queuepilot.queuepilot.domain.IncidentStatus;
import com.queuepilot.queuepilot.domain.Severity;
import com.queuepilot.queuepilot.mapper.EventMapper;
import com.queuepilot.queuepilot.mapper.IncidentMapper;
import com.queuepilot.queuepilot.repo.EventRepository;
import com.queuepilot.queuepilot.repo.IdempotencyKeyRepository;
import com.queuepilot.queuepilot.repo.IncidentRepository;
import com.queuepilot.queuepilot.util.EventFingerprintUtils;
import com.queuepilot.queuepilot.util.IdempotencyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Skeleton implementation of TriageService.
 */
@Service
public class TriageServiceImpl implements TriageService {

    private static final Logger logger = LoggerFactory.getLogger(TriageServiceImpl.class);

    private final IncidentRepository incidentRepository;
    private final IdempotencyKeyRepository idempotencyKeyRepository;

    private final EventRepository eventRepository;

    @Override
    public IncidentResponse ingestEvent(EventIngestRequest request, String idempotencyKey) {
        logger.debug("TriageService.ingestEvent called");
        Instant now = Instant.now();
        Instant occurredAt = request.getOccurredAt() != null ? request.getOccurredAt() : now;
        String fingerprint = request.getFingerprint();
        if (fingerprint == null || fingerprint.isBlank()) {
            fingerprint = EventFingerprintUtils.computeFingerprint(
                    request.getService(),
                    request.getTitle(),
                    request.getDescription()
            );
        }
        String requestHash = null;
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            String body = buildIdempotencyBody(request);
            requestHash = IdempotencyUtils.computeRequestHash("POST", "/api/events", body);
            Optional<IdempotencyKey> existingKey = idempotencyKeyRepository.findById(idempotencyKey);
            if (existingKey.isPresent()) {
                IdempotencyKey stored = existingKey.get();
                if (!requestHash.equals(stored.getRequestHash())) {
                    throw new ResponseStatusException(CONFLICT, "Idempotency-Key reused with different request");
                }
                UUID existingIncidentId = stored.getIncidentId();
                if (existingIncidentId == null) {
                    throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Idempotency-Key missing incident reference");
                }
                return incidentRepository.findById(existingIncidentId)
                        .map(IncidentMapper::toResponse)
                        .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "Incident not found for Idempotency-Key"));
            }
        }

        List<IncidentStatus> activeStatuses = List.of(IncidentStatus.OPEN, IncidentStatus.ESCALATED);
        Optional<Incident> existingIncident =
                incidentRepository.findTopByServiceAndFingerprintAndStatusInOrderByLastSeenAtDesc(
                        request.getService(),
                        fingerprint,
                        activeStatuses
                );
        Incident incident;
        if (existingIncident.isPresent()) {
            incident = existingIncident.get();
            incident.setEventCount(incident.getEventCount() + 1);
            incident.setLastSeenAt(now);
        } else {
            incident = new Incident();
            incident.setService(request.getService());
            incident.setSeverity(request.getSeverity());
            incident.setStatus(IncidentStatus.OPEN);
            incident.setFingerprint(fingerprint);
            incident.setFirstSeenAt(now);
            incident.setLastSeenAt(now);
            incident.setEventCount(1);
            incident.setAckDeadlineAt(calculateAckDeadline(request.getSeverity(), now));
        }

        Event event = new Event();
        EventMapper.populateEvent(event, request, fingerprint, occurredAt, now);
        eventRepository.save(event);

        incidentRepository.save(incident);
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            IdempotencyKey key = new IdempotencyKey();
            key.setKey(idempotencyKey);
            key.setRequestHash(requestHash);
            key.setIncidentId(incident.getId());
            key.setCreatedAt(now);
            idempotencyKeyRepository.save(key);
        }
        return IncidentMapper.toResponse(incident);
    }

    @Override
    public IncidentResponse getIncidentById(UUID incidentId) {
        logger.debug("TriageService.getIncidentById called for id={}", incidentId);
        return incidentRepository.findById(incidentId)
                .map(IncidentMapper::toResponse)
                .orElse(null);
    }

    @Override
    public List<IncidentResponse> getIncidentsByStatus(IncidentStatus status) {
        logger.debug("TriageService.getIncidentsByStatus called for status={}", status);
        List<Incident> incidents = status == null
                ? incidentRepository.findAll()
                : incidentRepository.findByStatus(status);
        return incidents.stream()
                .map(IncidentMapper::toResponse)
                .collect(Collectors.toList());
    }


    public TriageServiceImpl(IncidentRepository incidentRepository, EventRepository eventRepository, IdempotencyKeyRepository idempotencyKeyRepository){
        this.incidentRepository = incidentRepository;
        this.idempotencyKeyRepository = idempotencyKeyRepository;
        this.eventRepository = eventRepository;
    }

    private Instant calculateAckDeadline(Severity severity, Instant baseTime) {
        Duration sla;
        switch (severity) {
            case P0 -> sla = Duration.ofMinutes(5);
            case P1 -> sla = Duration.ofMinutes(15);
            case P2 -> sla = Duration.ofHours(1);
            case P3 -> sla = Duration.ofHours(24);
            default -> sla = Duration.ofHours(1);
        }
        return baseTime.plus(sla);
    }

    private String buildIdempotencyBody(EventIngestRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("source=").append(nullToEmpty(request.getSource())).append('|');
        builder.append("service=").append(nullToEmpty(request.getService())).append('|');
        builder.append("severity=").append(request.getSeverity() != null ? request.getSeverity().name() : "").append('|');
        builder.append("title=").append(nullToEmpty(request.getTitle())).append('|');
        builder.append("description=").append(nullToEmpty(request.getDescription())).append('|');
        builder.append("fingerprint=").append(nullToEmpty(request.getFingerprint())).append('|');
        builder.append("occurredAt=").append(request.getOccurredAt() != null ? request.getOccurredAt().toString() : "");
        return builder.toString();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }




}
