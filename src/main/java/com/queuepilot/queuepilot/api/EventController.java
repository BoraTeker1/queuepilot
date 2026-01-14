package com.queuepilot.queuepilot.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for event ingestion.
 * Handles incoming operational events (tickets, alerts, incidents).
 */
@RestController
@RequestMapping("/api/events")
public class EventController {
    // TODO: Implement event ingestion endpoints
}
