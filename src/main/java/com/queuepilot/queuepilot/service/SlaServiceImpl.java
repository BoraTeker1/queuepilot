package com.queuepilot.queuepilot.service;

import com.queuepilot.queuepilot.domain.Incident;
import com.queuepilot.queuepilot.domain.IncidentStatus;
import com.queuepilot.queuepilot.repo.IncidentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Skeleton implementation of SlaService.
 */
@Service
public class SlaServiceImpl implements SlaService {

    private static final Logger logger = LoggerFactory.getLogger(SlaServiceImpl.class);
    private final IncidentRepository incidentRepository;

    public SlaServiceImpl(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Override
    @Transactional
    public void escalateOverdueIncidents() {
        Instant now = Instant.now();
        List<Incident> overdue = incidentRepository.findByStatusAndAckDeadlineAtBefore(IncidentStatus.OPEN, now);
        if (overdue.isEmpty()) {
            logger.debug("No overdue incidents found for escalation");
            return;
        }
        for (Incident incident : overdue) {
            incident.setStatus(IncidentStatus.ESCALATED);
        }
        incidentRepository.saveAll(overdue);
        logger.info("Escalated {} incident(s) due to SLA breach", overdue.size());
    }
}
