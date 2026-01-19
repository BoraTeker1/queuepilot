package com.queuepilot.queuepilot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Domain entity for tracking idempotency keys.
 * Prevents duplicate processing of the same request.
 */
@Entity
@Table(name = "idempotency_keys")
public class IdempotencyKey {
    @Id
    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private String requestHash;

    @Column
    private UUID incidentId;

    @Column(nullable = false)
    private Instant createdAt;

    // Default constructor for JPA
    public IdempotencyKey() {
    }

    // Getters and setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRequestHash() {
        return requestHash;
    }

    public void setRequestHash(String requestHash) {
        this.requestHash = requestHash;
    }

    public UUID getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(UUID incidentId) {
        this.incidentId = incidentId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
