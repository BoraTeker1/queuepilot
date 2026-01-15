package com.queuepilot.queuepilot.repo;

import com.queuepilot.queuepilot.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data repository for Event entities.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    // TODO: Add custom query methods for deduplication, clustering, etc.
}
