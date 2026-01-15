package com.queuepilot.queuepilot.api.dto;

import com.queuepilot.queuepilot.domain.IncidentStatus;
import com.queuepilot.queuepilot.domain.Severity;
import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO for incident information.
 */
public class IncidentResponse {
    
    private UUID incidentId;
    private IncidentStatus status;
    private Severity severity;
    private String service;
    private String fingerprint;
    private long eventCount;
    private Instant firstSeenAt;
    private Instant lastSeenAt;
    private Instant ackDeadlineAt;

    // Getters and setters
    public UUID getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(UUID incidentId) {
        this.incidentId = incidentId;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public long getEventCount() {
        return eventCount;
    }

    public void setEventCount(long eventCount) {
        this.eventCount = eventCount;
    }

    public Instant getFirstSeenAt() {
        return firstSeenAt;
    }

    public void setFirstSeenAt(Instant firstSeenAt) {
        this.firstSeenAt = firstSeenAt;
    }

    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    public Instant getAckDeadlineAt() {
        return ackDeadlineAt;
    }

    public void setAckDeadlineAt(Instant ackDeadlineAt) {
        this.ackDeadlineAt = ackDeadlineAt;
    }
}
