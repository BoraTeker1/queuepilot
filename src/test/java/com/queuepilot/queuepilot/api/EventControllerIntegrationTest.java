package com.queuepilot.queuepilot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queuepilot.queuepilot.TestcontainersConfiguration;
import com.queuepilot.queuepilot.api.dto.EventIngestRequest;
import com.queuepilot.queuepilot.api.dto.IncidentResponse;
import com.queuepilot.queuepilot.domain.Severity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for EventController.
 * Tests event ingestion, deduplication, and idempotency behavior.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @BeforeEach
    void setUp() {
        // TODO: Clean up test data before each test
    }

    @Test
    void dedup_shouldIncrementEventCount() throws Exception {
        // TODO: Implement deduplication test
        // 1. Send first event with fingerprint
        // 2. Verify incident created with eventCount = 1
        // 3. Send duplicate event with same fingerprint
        // 4. Verify same incident returned with eventCount = 2
    }

    @Test
    void idempotencyKey_shouldReturnSameIncident() throws Exception {
        // TODO: Implement idempotency test
        // 1. Send event with Idempotency-Key header
        // 2. Store the returned incident ID
        // 3. Send same event again with same Idempotency-Key header
        // 4. Verify same incident ID is returned
    }

    // Helper methods

    /**
     * Creates a valid EventIngestRequest for testing.
     */
    private EventIngestRequest createEventRequest() {
        EventIngestRequest request = new EventIngestRequest();
        request.setSource("test-source");
        request.setService("test-service");
        request.setSeverity(Severity.P1);
        request.setTitle("Test Event");
        request.setDescription("Test description");
        request.setOccurredAt(Instant.now());
        return request;
    }

    /**
     * Creates an EventIngestRequest with a specific fingerprint.
     */
    private EventIngestRequest createEventRequestWithFingerprint(String fingerprint) {
        EventIngestRequest request = createEventRequest();
        request.setFingerprint(fingerprint);
        return request;
    }

    /**
     * Sends a POST request to /api/events and returns the MvcResult.
     */
    private MvcResult postEvent(EventIngestRequest request) throws Exception {
        return mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    /**
     * Sends a POST request to /api/events with an Idempotency-Key header.
     */
    private MvcResult postEventWithIdempotencyKey(EventIngestRequest request, String idempotencyKey) throws Exception {
        return mockMvc.perform(post("/api/events")
                .header("Idempotency-Key", idempotencyKey)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    /**
     * Parses the response body into an IncidentResponse.
     */
    private IncidentResponse parseIncidentResponse(MvcResult result) throws Exception {
        String content = result.getResponse().getContentAsString();
        return objectMapper.readValue(content, IncidentResponse.class);
    }
}
