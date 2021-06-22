package com.iteratec.teamdojo.config.ext;

import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import com.iteratec.teamdojo.domain.PersistentAuditEventData;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * This component converts from the built in {@link AuditEvent} to our custom {@link PersistentAuditEvent} and vice versa
 */
@Component
public class AuditEventConverter {

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
     * Convert a PersistentAuditEvent to an AuditEvent
     *
     * @param input the event to convert
     * @return the converted list.
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
}
