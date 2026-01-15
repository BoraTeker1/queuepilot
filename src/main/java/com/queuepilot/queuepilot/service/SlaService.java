package com.queuepilot.queuepilot.service;

/**
 * Service interface for SLA management operations.
 * Handles SLA deadline tracking, escalation, and breach detection.
 */
public interface SlaService {
    
    /**
     * Escalates incidents that have exceeded their acknowledgment deadline.
     * This method is called periodically by the SLA escalation job.
     */
    void escalateOverdueIncidents();
}
