package com.queuepilot.queuepilot.mapper;

import com.queuepilot.queuepilot.api.dto.IncidentResponse;
import com.queuepilot.queuepilot.domain.Incident;

/**
 * Mapping helpers for Incident responses.
 */
public final class IncidentMapper {

    private IncidentMapper() {
    }

    public static IncidentResponse toResponse(Incident incident) {
        if (incident == null) {
            return null;
        }
        IncidentResponse response = new IncidentResponse();
        response.setIncidentId(incident.getId());
        response.setStatus(incident.getStatus());
        response.setSeverity(incident.getSeverity());
        response.setService(incident.getService());
        response.setFingerprint(incident.getFingerprint());
        response.setEventCount(incident.getEventCount());
        response.setFirstSeenAt(incident.getFirstSeenAt());
        response.setLastSeenAt(incident.getLastSeenAt());
        response.setAckDeadlineAt(incident.getAckDeadlineAt());
        return response;
    }
}
