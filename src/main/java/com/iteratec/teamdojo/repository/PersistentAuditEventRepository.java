package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersistentAuditEvent entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface PersistentAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {}
