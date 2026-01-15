package com.queuepilot.queuepilot.domain;

/**
 * Enumeration of incident status values.
 */
public enum IncidentStatus {
    OPEN,        // Incident is open and unacknowledged
    ACKED,       // Incident has been acknowledged by an owner
    ESCALATED,   // Incident has been escalated (e.g., SLA breach approaching)
    RESOLVED     // Incident has been resolved
}
