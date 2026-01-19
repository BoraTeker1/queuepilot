package com.queuepilot.queuepilot.repo;

import com.queuepilot.queuepilot.domain.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for IdempotencyKey entities.
 */
@Repository
public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey, String> {
    boolean existsByKey(String key);
}
