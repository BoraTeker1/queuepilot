-- Flyway migration V1: Initial schema creation
-- Creates tables for Event, Incident, and IdempotencyKey

-- Enable required extension for UUID generation
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Events table: stores individual operational events
CREATE TABLE events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    source VARCHAR(255) NOT NULL,
    service VARCHAR(255) NOT NULL,
    severity VARCHAR(10) NOT NULL,
    title VARCHAR(500) NOT NULL,
    description TEXT,
    fingerprint VARCHAR(255) NOT NULL,
    occurred_at TIMESTAMP NOT NULL,
    received_at TIMESTAMP NOT NULL
);

-- Indexes for events table
CREATE INDEX idx_event_service ON events(service);
CREATE INDEX idx_event_fingerprint ON events(fingerprint);
CREATE INDEX idx_event_received_at ON events(received_at);

-- Incidents table: stores clustered incidents
CREATE TABLE incidents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    service VARCHAR(255) NOT NULL,
    severity VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    fingerprint VARCHAR(255) NOT NULL,
    first_seen_at TIMESTAMP NOT NULL,
    last_seen_at TIMESTAMP NOT NULL,
    event_count BIGINT NOT NULL,
    ack_deadline_at TIMESTAMP
);

-- Indexes for incidents table
CREATE INDEX idx_incident_service ON incidents(service);
CREATE INDEX idx_incident_fingerprint ON incidents(fingerprint);
CREATE INDEX idx_incident_status ON incidents(status);

-- Idempotency keys table: prevents duplicate request processing
CREATE TABLE idempotency_keys (
    key VARCHAR(255) PRIMARY KEY,
    request_hash VARCHAR(255) NOT NULL,
    incident_id UUID,
    created_at TIMESTAMP NOT NULL
);
