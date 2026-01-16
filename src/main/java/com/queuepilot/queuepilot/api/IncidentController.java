package com.queuepilot.queuepilot.api;

import com.queuepilot.queuepilot.api.dto.ErrorResponse;
import com.queuepilot.queuepilot.api.dto.IncidentResponse;
import com.queuepilot.queuepilot.domain.IncidentStatus;
import com.queuepilot.queuepilot.service.TriageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Incidents", description = "Incident retrieval and query endpoints")
public class IncidentController {
    
    private final TriageService triageService;
    
    public IncidentController(TriageService triageService) {
        this.triageService = triageService;
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get incident by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Incident found",
                    content = @Content(schema = @Schema(implementation = IncidentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Incident not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<IncidentResponse> getIncidentById(@PathVariable UUID id) {
        IncidentResponse response = triageService.getIncidentById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "List incidents", description = "Optionally filter incidents by status.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Incidents returned",
                    content = @Content(schema = @Schema(implementation = IncidentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<IncidentResponse>> getIncidents(
            @RequestParam(required = false) IncidentStatus status) {
        List<IncidentResponse> responses = triageService.getIncidentsByStatus(status);
        return ResponseEntity.ok(responses);
    }
}
