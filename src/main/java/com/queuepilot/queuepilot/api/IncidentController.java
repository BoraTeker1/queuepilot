package com.queuepilot.queuepilot.api;

import com.queuepilot.queuepilot.api.dto.IncidentResponse;
import com.queuepilot.queuepilot.domain.IncidentStatus;
import com.queuepilot.queuepilot.service.TriageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for incident management.
 * Handles incident retrieval and querying.
 */
@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    
    private final TriageService triageService;
    
    public IncidentController(TriageService triageService) {
        this.triageService = triageService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponse> getIncidentById(@PathVariable UUID id) {
        IncidentResponse response = triageService.getIncidentById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<IncidentResponse>> getIncidents(
            @RequestParam(required = false) IncidentStatus status) {
        List<IncidentResponse> responses = triageService.getIncidentsByStatus(status);
        return ResponseEntity.ok(responses);
    }
}
