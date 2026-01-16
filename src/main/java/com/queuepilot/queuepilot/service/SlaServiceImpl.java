package com.queuepilot.queuepilot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Skeleton implementation of SlaService.
 */
@Service
public class SlaServiceImpl implements SlaService {

    private static final Logger logger = LoggerFactory.getLogger(SlaServiceImpl.class);

    @Override
    public void escalateOverdueIncidents() {
        logger.debug("SlaService.escalateOverdueIncidents called");
        throw new UnsupportedOperationException("SlaService.escalateOverdueIncidents not implemented yet");
    }
}
