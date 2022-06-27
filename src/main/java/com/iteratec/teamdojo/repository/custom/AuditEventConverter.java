/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import com.iteratec.teamdojo.domain.PersistentAuditEventData;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Value;
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
        return input
            .entrySet()
            .stream()
            .map(this::extractAuditEventData)
            .flatMap(Collection::stream)
            // FIXME: #27 Should we also truncate the name to not exceed the database column size?
            .map(this::truncateValue)
            .map(this::toPersistentAuditEventData)
            .collect(Collectors.toSet());
    }

    PersistentAuditEventData toPersistentAuditEventData(final DataPair input) {
        final var output = new PersistentAuditEventData();
        output.setName(input.getName());
        output.setValue(input.getValue());
        return output;
    }

    /**
     * Extracts event data from the given data entry object.
     * <p>
     * By default the given object is simply converted to a string. But this method may also extract more data from that
     * object depending on its type. That's the reason why it returns a collection of data pairs.
     * </p>
     *
     * @param input may be {@code null}
     * @return never {@code null}, maybe empty
     */
    private Collection<DataPair> extractAuditEventData(final Map.Entry<String, Object> input) {
        if (input == null) {
            return Collections.emptyList();
        }

        // Extract the data that will be saved.
        if (input.getValue() instanceof WebAuthenticationDetails) {
            final var details = (WebAuthenticationDetails) input.getValue();
            return Arrays.asList(
                DataPair.of("remoteAddress", details.getRemoteAddress()),
                DataPair.of("sessionId", details.getSessionId())
            );
        } else {
            return Collections.singleton(DataPair.of(input.getKey(), Objects.toString(input.getValue())));
        }
    }

    /**
     * Truncate event data value that might exceed column length
     *
     * @param input never {@code null}
     * @return never {@code null}
     */
    private DataPair truncateValue(final DataPair input) {
        if (input.getValue() == null) {
            return input;
        }

        if (input.getValue().length() <= EVENT_DATA_COLUMN_MAX_LENGTH) {
            return input;
        }

        log.warn(
            "Event data for {} too long ({}) has been truncated to {}. Consider increasing column width.",
            input.getName(),
            input.getValue().length(),
            EVENT_DATA_COLUMN_MAX_LENGTH
        );

        return DataPair.of(input.getName(), input.getValue().substring(0, EVENT_DATA_COLUMN_MAX_LENGTH));
    }

    /**
     * Internal value object to hold two values.
     * <p>
     * This is necessary because java does not provide such a generic class since ages!
     * </p>
     */
    @Value
    private static class DataPair {

        String name;
        /**
         * May be {@code null}
         */
        String value;

        static DataPair of(final String name, final Object value) {
            return new DataPair(name, Objects.toString(value));
        }
    }
}
