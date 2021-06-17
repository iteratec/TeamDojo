package com.iteratec.teamdojo.repository.ext;

import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import com.iteratec.teamdojo.repository.PersistentAuditEventRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface ExtendedPersistentAuditEventRepository extends PersistentAuditEventRepository {
    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principal, Instant after, String type);
}
