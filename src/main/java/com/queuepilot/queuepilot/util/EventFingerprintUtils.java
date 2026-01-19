package com.queuepilot.queuepilot.util;

/**
 * Utility class for event fingerprinting.
 * Used for deduplication and clustering of similar events.
 */
public class EventFingerprintUtils {

    private EventFingerprintUtils() {
    }

    /**
     * Compute a stable fingerprint for an event.
     * TODO: Implement fingerprinting strategy (e.g., hash of service + title + description).
     */
    public static String computeFingerprint(String service, String title, String description) {
        throw new UnsupportedOperationException("EventFingerprintUtils.computeFingerprint not implemented yet");
    }
}
