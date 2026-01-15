# QueuePilot

QueuePilot is an SLA-aware incident triage and auto-remediation platform that ingests operational events (support tickets, alerts, customer incidents), automatically deduplicates and clusters them, assigns priority and owners, and triggers safe, policy-based remediation workflows with full audit trails. It reduces alert noise, enforces SLA policies, and transforms manual triage into a repeatable, measurable pipeline.

## Architecture

- **Event Ingestion API**: REST endpoint that accepts events, validates and normalizes them into a canonical schema
- **Deduplication & Clustering**: Fingerprint-based event grouping to prevent duplicate incidents and cluster related events
- **SLA Policy Engine**: Automatic priority scoring, SLA timer tracking, and escalation policies based on severity levels
- **Incident Management**: Tracks incident lifecycle (OPEN → ACKED → ESCALATED → RESOLVED) with acknowledgment deadlines
- **Idempotency**: Request-level deduplication using idempotency keys to prevent double-processing
- **PostgreSQL**: Primary data store for events, incidents, and audit logs
- **Redis**: Idempotency key storage, rate limiting, and hot incident state caching
- **Scheduled Jobs**: Background workers for SLA monitoring and escalation

## Quickstart

### Prerequisites

- Java 25+
- Maven 3.8+
- Docker and Docker Compose

### Start Infrastructure

```bash
docker-compose up -d
```

This starts PostgreSQL (port 5432) and Redis (port 6379) with persistent volumes.

### Run Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080` and automatically run Flyway migrations to create the database schema.

## API Examples

### Post an Event

```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "source": "pagerduty",
    "service": "payment-service",
    "severity": "P1",
    "title": "Payment processing timeout",
    "description": "Payment requests timing out after 30s",
    "fingerprint": "payment-timeout-001"
  }'
```

Response includes the incident ID and details:

```json
{
  "incidentId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "OPEN",
  "severity": "P1",
  "service": "payment-service",
  "fingerprint": "payment-timeout-001",
  "eventCount": 1,
  "firstSeenAt": "2024-01-14T10:00:00Z",
  "lastSeenAt": "2024-01-14T10:00:00Z",
  "ackDeadlineAt": "2024-01-14T10:30:00Z"
}
```

### Post Duplicate Event (Deduplication)

Posting the same event again with the same fingerprint increments the event count:

```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "source": "pagerduty",
    "service": "payment-service",
    "severity": "P1",
    "title": "Payment processing timeout",
    "description": "Payment requests timing out after 30s",
    "fingerprint": "payment-timeout-001"
  }'
```

Response shows the same incident with `eventCount: 2`:

```json
{
  "incidentId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "OPEN",
  "severity": "P1",
  "service": "payment-service",
  "fingerprint": "payment-timeout-001",
  "eventCount": 2,
  "firstSeenAt": "2024-01-14T10:00:00Z",
  "lastSeenAt": "2024-01-14T10:05:00Z",
  "ackDeadlineAt": "2024-01-14T10:30:00Z"
}
```

### Fetch Incident by ID

```bash
curl http://localhost:8080/api/incidents/550e8400-e29b-41d4-a716-446655440000
```

### List Incidents by Status

```bash
curl "http://localhost:8080/api/incidents?status=OPEN"
```

## Technology Stack

- **Spring Boot 4.0.1** - Application framework
- **PostgreSQL** - Primary database
- **Redis** - Caching and rate limiting
- **Flyway** - Database migrations
- **Testcontainers** - Integration testing

## License

This project is part of a portfolio demonstration.
