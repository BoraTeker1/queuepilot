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
        String normalizedService = normalize(service);
        String normalizedTitle = normalize(title);
        String normalizedDescription = normalize(description);
        String canonical = normalizedService + "|" + normalizedTitle + "|" + normalizedDescription;
        return sha256Hex(canonical);
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }
        String trimmed = value.trim().toLowerCase();
        return trimmed.replaceAll("\\s+", " ");
    }

    private static String sha256Hex(String value) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                String part = Integer.toHexString(0xff & b);
                if (part.length() == 1) {
                    hex.append('0');
                }
                hex.append(part);
            }
            return hex.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 not available", ex);
        }
    }
}
