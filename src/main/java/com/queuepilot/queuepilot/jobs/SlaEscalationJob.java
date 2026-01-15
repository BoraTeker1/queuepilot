package com.queuepilot.queuepilot.jobs;

import com.queuepilot.queuepilot.service.SlaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled job for escalating incidents that have exceeded their SLA deadlines.
 * Runs every 60 seconds to check for overdue incidents and trigger escalations.
 */
@Component
public class SlaEscalationJob {
    
    private static final Logger logger = LoggerFactory.getLogger(SlaEscalationJob.class);
    
    private final SlaService slaService;
    
    public SlaEscalationJob(SlaService slaService) {
        this.slaService = slaService;
    }
    
    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void escalateOverdueIncidents() {
        logger.debug("Starting SLA escalation job");
        try {
            slaService.escalateOverdueIncidents();
            logger.debug("SLA escalation job completed successfully");
        } catch (Exception e) {
            logger.error("Error during SLA escalation job execution", e);
        }
    }
}
