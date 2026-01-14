package com.queuepilot.queuepilot.repo;

import com.queuepilot.queuepilot.domain.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for Incident entities.
 */
@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    // TODO: Add custom query methods for SLA checks, assignment, etc.
}
