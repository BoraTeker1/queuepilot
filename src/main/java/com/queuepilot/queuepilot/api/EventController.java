package com.queuepilot.queuepilot.api;

import com.queuepilot.queuepilot.api.dto.EventIngestRequest;
import com.queuepilot.queuepilot.api.dto.IncidentResponse;
import com.queuepilot.queuepilot.service.TriageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for event ingestion.
 * Handles incoming operational events (tickets, alerts, incidents).
 */
@RestController
@RequestMapping("/api/events")
public class EventController {
    
    private final TriageService triageService;
    
    public EventController(TriageService triageService) {
        this.triageService = triageService;
    }
    
    @PostMapping
    public ResponseEntity<IncidentResponse> ingestEvent(@Valid @RequestBody EventIngestRequest request) {
        IncidentResponse response = triageService.ingestEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
