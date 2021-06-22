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

    /**
     * Should be the same as in Liquibase migration.
     */
    protected static final int EVENT_DATA_COLUMN_MAX_LENGTH = 255;

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
            PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
            persistentAuditEvent.setPrincipal(event.getPrincipal());
            persistentAuditEvent.setAuditEventType(event.getType());
            persistentAuditEvent.setAuditEventDate(event.getTimestamp());
            Map<String, String> eventData = auditEventConverter.convertDataToStrings(event.getData());
            // FIXME:
            //            persistentAuditEvent.setData(truncate(eventData));
            persistentAuditEventRepository.save(persistentAuditEvent);
        }
    }

    /**
     * Truncate event data that might exceed column length.
     */
    private Map<String, String> truncate(Map<String, String> data) {
        Map<String, String> results = new HashMap<>();

        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    int length = value.length();
                    if (length > EVENT_DATA_COLUMN_MAX_LENGTH) {
                        value = value.substring(0, EVENT_DATA_COLUMN_MAX_LENGTH);
                        log.warn(
                            "Event data for {} too long ({}) has been truncated to {}. Consider increasing column width.",
                            entry.getKey(),
                            length,
                            EVENT_DATA_COLUMN_MAX_LENGTH
                        );
                    }
                }
                results.put(entry.getKey(), value);
            }
        }
        return results;
    }
}
