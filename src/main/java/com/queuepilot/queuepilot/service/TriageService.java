package com.queuepilot.queuepilot.service;

import com.queuepilot.queuepilot.api.dto.EventIngestRequest;
import com.queuepilot.queuepilot.api.dto.IncidentResponse;
import com.queuepilot.queuepilot.domain.IncidentStatus;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for incident triage operations.
 * Handles event ingestion, deduplication, clustering, and incident management.
 */
public interface TriageService {
    
    /**
     * Ingests an event and returns the associated incident.
     * Performs deduplication, clustering, and priority assignment.
     * 
     * @param request The event ingestion request
     * @return The incident response (new or existing)
     */
    IncidentResponse ingestEvent(EventIngestRequest request);
    
    /**
     * Retrieves an incident by its ID.
     * 
     * @param incidentId The incident UUID
     * @return The incident response, or null if not found
     */
    IncidentResponse getIncidentById(UUID incidentId);
    
    /**
     * Retrieves incidents filtered by status.
     * 
     * @param status The incident status to filter by (optional)
     * @return List of incident responses
     */
    List<IncidentResponse> getIncidentsByStatus(IncidentStatus status);
}
