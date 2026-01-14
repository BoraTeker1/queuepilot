package com.queuepilot.queuepilot.repo;

import com.queuepilot.queuepilot.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for Event entities.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // TODO: Add custom query methods for deduplication, clustering, etc.
}
