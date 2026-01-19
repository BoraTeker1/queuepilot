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
        throw new UnsupportedOperationException("IdempotencyUtils.computeRequestHash not implemented yet");
    }
}
