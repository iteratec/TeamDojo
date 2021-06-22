package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.PersistentAuditEventData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersistentAuditEventData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersistentAuditEventDataRepository extends JpaRepository<PersistentAuditEventData, Long> {}
