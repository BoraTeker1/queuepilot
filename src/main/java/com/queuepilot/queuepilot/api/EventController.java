package com.queuepilot.queuepilot.api;

import com.queuepilot.queuepilot.api.dto.ErrorResponse;
import com.queuepilot.queuepilot.api.dto.EventIngestRequest;
import com.queuepilot.queuepilot.api.dto.IncidentResponse;
import com.queuepilot.queuepilot.service.TriageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Events", description = "Event ingestion endpoints")
public class EventController {
    
    private final TriageService triageService;
    
    public EventController(TriageService triageService) {
        this.triageService = triageService;
    }
    
    @PostMapping
    @Operation(summary = "Ingest an event", description = "Accepts an operational event and returns the associated incident.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Incident created or updated",
                    content = @Content(schema = @Schema(implementation = IncidentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<IncidentResponse> ingestEvent(@Valid @RequestBody EventIngestRequest request) {
        IncidentResponse response = triageService.ingestEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
