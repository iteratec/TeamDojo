package com.iteratec.teamdojo.config.ext;

import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import com.iteratec.teamdojo.domain.PersistentAuditEventData;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * This component converts from the built in {@link AuditEvent} to our custom {@link PersistentAuditEvent} and vice versa
 */
@Slf4j
@Component
public class AuditEventConverter {

    /**
     * Should be the same as in Liquibase migration.
     */
    static final int EVENT_DATA_COLUMN_MAX_LENGTH = 255;

    /**
     * Convert a list of PersistentAuditEvent to a list of AuditEvent
     *
     * @param input may be {@code null}
     * @return never {@code null}, maybe empty list
     */
    public List<AuditEvent> toAuditEvents(final Collection<PersistentAuditEvent> input) {
        if (input == null) {
            return Collections.emptyList();
        }

        final List<AuditEvent> auditEvents = new ArrayList<>();

        for (final PersistentAuditEvent persistentAuditEvent : input) {
            auditEvents.add(toAuditEvent(persistentAuditEvent));
        }

        return auditEvents;
    }

    /**
     * Convert a AuditEvent to an PersistentAuditEvent
     *
     * @param input may be {@code null}
     * @return may be {@code null}
     */
    public PersistentAuditEvent toPersistentAuditEvent(final AuditEvent input) {
        if (input == null) {
            return null;
        }

        final var output = new PersistentAuditEvent();
        output.setPrincipal(input.getPrincipal());
        output.setAuditEventType(input.getType());
        output.setAuditEventDate(input.getTimestamp());
        output.setData(toPersistentAuditEventDataSet(input.getData()));

        return output;
    }

    /**
     * Convert a PersistentAuditEvent to an AuditEvent
     *
     * @param input may be {@code null}
     * @return may be {@code null}
     */
    AuditEvent toAuditEvent(final PersistentAuditEvent input) {
        if (input == null) {
            return null;
        }

        return new AuditEvent(input.getAuditEventDate(), input.getPrincipal(), input.getAuditEventType(), toMap(input.getData()));
    }

    /**
     * Internal conversion. This is needed to support the current SpringBoot actuator AuditEventRepository interface
     *
     * @param input may be {@code null}
     * @return never {@code null}, maybe empty
     */
    Map<String, Object> toMap(final Set<PersistentAuditEventData> input) {
        if (input == null) {
            return Collections.emptyMap();
        }

        return input.stream().collect(Collectors.toMap(PersistentAuditEventData::getName, PersistentAuditEventData::getValue));
    }

    Set<PersistentAuditEventData> toPersistentAuditEventDataSet(final Map<String, Object> input) {
        final var extracted = convertDataToStrings(input);
        final var truncated = truncate(extracted);
        return truncated.entrySet().stream().map(this::toPersistentAuditEventData).collect(Collectors.toSet());
    }

    PersistentAuditEventData toPersistentAuditEventData(final Map.Entry<String, String> input) {
        final var output = new PersistentAuditEventData();
        output.setName(input.getKey());
        output.setValue(input.getValue());
        return output;
    }

    /**
     * Internal conversion. This method will allow to save additional data.
     * By default, it will save the object as string
     *
     * @param data the data to convert
     * @return a map of String, String
     */
    public Map<String, String> convertDataToStrings(Map<String, Object> data) {
        Map<String, String> results = new HashMap<>();

        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                // Extract the data that will be saved.
                if (entry.getValue() instanceof WebAuthenticationDetails) {
                    WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) entry.getValue();
                    results.put("remoteAddress", authenticationDetails.getRemoteAddress());
                    results.put("sessionId", authenticationDetails.getSessionId());
                } else {
                    results.put(entry.getKey(), Objects.toString(entry.getValue()));
                }
            }
        }
        return results;
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
