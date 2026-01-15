package com.queuepilot.queuepilot.api.dto;

import com.queuepilot.queuepilot.domain.Severity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Request DTO for event ingestion.
 */
public class EventIngestRequest {
    
    @NotBlank(message = "Source is required")
    private String source;
    
    @NotBlank(message = "Service is required")
    private String service;
    
    @NotNull(message = "Severity is required")
    private Severity severity;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    private String fingerprint;
    
    private Instant occurredAt;

    // Getters and setters
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }
}
