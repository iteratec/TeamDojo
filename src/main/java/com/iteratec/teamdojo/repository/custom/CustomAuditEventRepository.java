/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.config.custom.CustomConstants;
import java.time.Instant;
import java.util.List;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * An implementation of Spring Boot's {@link AuditEventRepository}
 *
 * <p>This implementation realizes the repository functions to add and find audit events. This implementation does not
 * persist any data itself, but delegates to JPA repository to persist the data and therefore acts as a facade. The reason
 * for this separation of concerns is that audit events may be persisted in any system and not mandatory in a SQL database.
 * Also we have generated JPA repositories for all database entities and we need to combine the interface of this repository
 * somehow with the generated repository w/o modifying generated code. The solution is to make an extended JPA repository
 * (as described in ADR-0001) and delegate to it.</p>
 */
@Repository
public class CustomAuditEventRepository implements AuditEventRepository {

    private static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";

    /**
     * JPA repository delegate which actually persists the data.
     */
    private final ExtendedPersistentAuditEventRepository persistentAuditEventRepository;

    private final AuditEventConverter converter;

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
