package com.queuepilot.queuepilot.repo;

import com.queuepilot.queuepilot.domain.Incident;
import com.queuepilot.queuepilot.domain.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data repository for Incident entities.
 */
@Repository
public interface IncidentRepository extends JpaRepository<Incident, UUID> {
    
    Optional<Incident> findTopByServiceAndFingerprintAndStatusInOrderByLastSeenAtDesc(
        String service, 
        String fingerprint, 
        List<IncidentStatus> statuses
    );
    
    List<Incident> findByStatusAndAckDeadlineAtBefore(IncidentStatus status, Instant deadline);
}
