package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import com.iteratec.teamdojo.repository.PersistentAuditEventRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Custom extension for {@link PersistentAuditEventRepository}
 *
 * <p>We use separate interfaces extending the generated one to avoid modification of generated code (see ADR-0001).</p>
 */
@Repository
public interface ExtendedPersistentAuditEventRepository extends PersistentAuditEventRepository {
    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principal, Instant after, String type);
}
