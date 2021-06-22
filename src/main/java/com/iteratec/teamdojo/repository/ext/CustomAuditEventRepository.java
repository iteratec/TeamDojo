package com.iteratec.teamdojo.repository.ext;

import com.iteratec.teamdojo.config.ext.AuditEventConverter;
import com.iteratec.teamdojo.config.ext.CustomConstants;
import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * An implementation of Spring Boot's AuditEventRepository.
 */
@Repository
public class CustomAuditEventRepository implements AuditEventRepository {

    private static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";

    private final ExtendedPersistentAuditEventRepository persistentAuditEventRepository;

    private final AuditEventConverter converter;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public CustomAuditEventRepository(
        ExtendedPersistentAuditEventRepository persistentAuditEventRepository,
        AuditEventConverter converter
    ) {
        this.persistentAuditEventRepository = persistentAuditEventRepository;
        this.converter = converter;
    }

    @Override
    public List<AuditEvent> find(String principal, Instant after, String type) {
        final var found = persistentAuditEventRepository.findByPrincipalAndAuditEventDateAfterAndAuditEventType(principal, after, type);
        return converter.toAuditEvents(found);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add(AuditEvent event) {
        if (!AUTHORIZATION_FAILURE.equals(event.getType()) && !CustomConstants.ANONYMOUS_USER.equals(event.getPrincipal())) {
            persistentAuditEventRepository.save(converter.toPersistentAuditEvent(event));
        }
    }
}
