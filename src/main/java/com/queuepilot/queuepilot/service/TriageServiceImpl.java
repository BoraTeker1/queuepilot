package com.queuepilot.queuepilot.service;

import com.queuepilot.queuepilot.api.dto.EventIngestRequest;
import com.queuepilot.queuepilot.api.dto.IncidentResponse;
import com.queuepilot.queuepilot.domain.IncidentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Skeleton implementation of TriageService.
 */
@Service
public class TriageServiceImpl implements TriageService {

    private static final Logger logger = LoggerFactory.getLogger(TriageServiceImpl.class);

    @Override
    public IncidentResponse ingestEvent(EventIngestRequest request) {
        logger.debug("TriageService.ingestEvent called");
        throw new UnsupportedOperationException("TriageService.ingestEvent not implemented yet");
    }

    @Override
    public IncidentResponse getIncidentById(UUID incidentId) {
        logger.debug("TriageService.getIncidentById called for id={}", incidentId);
        throw new UnsupportedOperationException("TriageService.getIncidentById not implemented yet");
    }

    @Override
    public List<IncidentResponse> getIncidentsByStatus(IncidentStatus status) {
        logger.debug("TriageService.getIncidentsByStatus called for status={}", status);
        throw new UnsupportedOperationException("TriageService.getIncidentsByStatus not implemented yet");
    }
}
