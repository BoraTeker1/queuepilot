package com.queuepilot.queuepilot.util;

/**
 * Utility class for idempotency key generation and validation.
 */
public class IdempotencyUtils {

    private IdempotencyUtils() {
    }

    /**
     * Compute a request hash for idempotency comparison.
     * TODO: Define the canonical input (method + path + body) and hashing algorithm.
     */
    public static String computeRequestHash(String method, String path, String body) {
        if (method == null) {
            method = "";
        }
        if (path == null) {
            path = "";
        }
        if (body == null) {
            body = "";
        }
        String canonical = method + "\n" + path + "\n" + body;
        return sha256Hex(canonical);
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
