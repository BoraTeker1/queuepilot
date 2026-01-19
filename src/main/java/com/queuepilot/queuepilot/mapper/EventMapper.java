package com.queuepilot.queuepilot.mapper;

import com.queuepilot.queuepilot.api.dto.EventIngestRequest;
import com.queuepilot.queuepilot.domain.Event;
import java.time.Instant;

/**
 * Mapping helpers for Event entities.
 */
public final class EventMapper {

    private EventMapper() {
    }

    public static void populateEvent(Event event,
                                     EventIngestRequest request,
                                     String fingerprint,
                                     Instant occurredAt,
                                     Instant receivedAt) {
        event.setSource(request.getSource());
        event.setService(request.getService());
        event.setSeverity(request.getSeverity());
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setFingerprint(fingerprint);
        event.setOccurredAt(occurredAt);
        event.setReceivedAt(receivedAt);
    }
}
