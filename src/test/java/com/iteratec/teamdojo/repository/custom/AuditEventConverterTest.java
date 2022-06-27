/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.repository.custom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import com.iteratec.teamdojo.domain.PersistentAuditEventData;
import java.time.Instant;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

class AuditEventConverterTest {

    private final AuditEventConverter sut = new AuditEventConverter();

    private PersistentAuditEvent createEventFixture(final int suffix, final Instant date) {
        final var event = new PersistentAuditEvent();
        event.setPrincipal("principal" + suffix);
        event.setAuditEventDate(date);
        event.setAuditEventType("type" + suffix);
        event.setData(createEventDataSetFixture(suffix));
        return event;
    }

    private Set<PersistentAuditEventData> createEventDataSetFixture(int suffix) {
        final var data = new HashSet<PersistentAuditEventData>();
        data.add(createEventDataFixture("foo" + suffix, "one" + suffix));
        data.add(createEventDataFixture("bar" + suffix, "two" + suffix));
        data.add(createEventDataFixture("baz" + suffix, "three" + suffix));
        return data;
    }

    private PersistentAuditEventData createEventDataFixture(String name, String value) {
        final var data = new PersistentAuditEventData();
        data.setName(name);
        data.setValue(value);
        return data;
    }

    @Test
    void toAuditEvents_nullConvertsToEmptyList() {
        assertThat(sut.toAuditEvents(null)).isEmpty();
    }

    @Test
    void toAuditEvents_inputContainsNull() {
        final var one = createEventFixture(1, Instant.parse("2021-05-29T12:00:00.00Z"));
        final var three = createEventFixture(3, Instant.parse("2021-05-31T12:00:00.00Z"));

        final var result = sut.toAuditEvents(Arrays.asList(one, null, three));

        // We can not use a default list matcher/assertion here, but must access each individual
        // element ant assert each field of it because AuditEvent does not implement proper equals method!
        assertAll(
            () -> assertThat(result).hasSize(3),
            () -> assertThat(result.get(0).getPrincipal()).isEqualTo("principal1"),
            () -> assertThat(result.get(0).getType()).isEqualTo("type1"),
            () -> assertThat(result.get(0).getTimestamp()).isEqualTo(Instant.parse("2021-05-29T12:00:00.00Z")),
            () -> assertThat(result.get(0).getData()).contains(entry("foo1", "one1"), entry("bar1", "two1"), entry("baz1", "three1")),
            () -> assertThat(result.get(1)).isNull(),
            () -> assertThat(result.get(2).getPrincipal()).isEqualTo("principal3"),
            () -> assertThat(result.get(2).getType()).isEqualTo("type3"),
            () -> assertThat(result.get(2).getTimestamp()).isEqualTo(Instant.parse("2021-05-31T12:00:00.00Z")),
            () -> assertThat(result.get(2).getData()).contains(entry("foo3", "one3"), entry("bar3", "two3"), entry("baz3", "three3"))
        );
    }

    @Test
    void toAuditEvents() {
        final var one = createEventFixture(1, Instant.parse("2021-05-29T12:00:00.00Z"));
        final var two = createEventFixture(2, Instant.parse("2021-05-30T12:00:00.00Z"));
        final var three = createEventFixture(3, Instant.parse("2021-05-31T12:00:00.00Z"));

        final var result = sut.toAuditEvents(Arrays.asList(one, two, three));

        // We can not use a default list matcher/assertion here, but must access each individual
        // element ant assert each field of it because AuditEvent does not implement proper equals method!
        assertAll(
            () -> assertThat(result).hasSize(3),
            () -> assertThat(result.get(0).getPrincipal()).isEqualTo("principal1"),
            () -> assertThat(result.get(0).getType()).isEqualTo("type1"),
            () -> assertThat(result.get(0).getTimestamp()).isEqualTo(Instant.parse("2021-05-29T12:00:00.00Z")),
            () -> assertThat(result.get(0).getData()).contains(entry("foo1", "one1"), entry("bar1", "two1"), entry("baz1", "three1")),
            () -> assertThat(result.get(1).getPrincipal()).isEqualTo("principal2"),
            () -> assertThat(result.get(1).getType()).isEqualTo("type2"),
            () -> assertThat(result.get(1).getTimestamp()).isEqualTo(Instant.parse("2021-05-30T12:00:00.00Z")),
            () -> assertThat(result.get(1).getData()).contains(entry("foo2", "one2"), entry("bar2", "two2"), entry("baz2", "three2")),
            () -> assertThat(result.get(2).getPrincipal()).isEqualTo("principal3"),
            () -> assertThat(result.get(2).getType()).isEqualTo("type3"),
            () -> assertThat(result.get(2).getTimestamp()).isEqualTo(Instant.parse("2021-05-31T12:00:00.00Z")),
            () -> assertThat(result.get(2).getData()).contains(entry("foo3", "one3"), entry("bar3", "two3"), entry("baz3", "three3"))
        );
    }

    @Test
    void toPersistentAuditEvent_nullConvertsToNull() {
        assertThat(sut.toPersistentAuditEvent(null)).isNull();
    }

    @Test
    void toPersistentAuditEvent_emptyDataMap() {
        final var now = Instant.now();
        final var input = new AuditEvent(now, "principal", "type", Collections.emptyMap());

        final var result = sut.toPersistentAuditEvent(input);

        assertAll(
            () -> assertThat(result.getPrincipal()).isEqualTo("principal"),
            () -> assertThat(result.getAuditEventType()).isEqualTo("type"),
            () -> assertThat(result.getAuditEventDate()).isEqualTo(now),
            () -> assertThat(result.getData()).isEmpty()
        );
    }

    @Test
    void toPersistentAuditEvent_plainDataMap() {
        final var data = new HashMap<String, Object>();
        data.put("foo", "snafu");
        data.put("bar", 42);
        final var o = new Object();
        data.put("baz", o);
        final var input = new AuditEvent("principal", "type", data);

        final var result = sut.toPersistentAuditEvent(input);
        // This is a workaround to access the indexed elements in reliable order
        // because we can not use simply the contains() matcher due to the implementation
        // of the generated entities equals() method which only compares the id.
        final var resultData = new ArrayList<>(result.getData());

        assertAll(
            () -> assertThat(result.getData()).hasSize(3),
            () -> assertThat(resultData.get(0).getName()).isEqualTo("bar"),
            () -> assertThat(resultData.get(0).getValue()).isEqualTo("42"),
            () -> assertThat(resultData.get(1).getName()).isEqualTo("foo"),
            () -> assertThat(resultData.get(1).getValue()).isEqualTo("snafu"),
            () -> assertThat(resultData.get(2).getName()).isEqualTo("baz"),
            () -> assertThat(resultData.get(2).getValue()).isEqualTo(o.toString())
        );
    }

    @Test
    void toPersistentAuditEvent_specialExtractedDataMap() {
        final var request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("the-remote-address");
        final var session = mock(HttpSession.class);
        when(session.getId()).thenReturn("the-session-id");
        when(request.getSession(false)).thenReturn(session);
        final var details = new WebAuthenticationDetails(request);
        final var data = new HashMap<String, Object>();
        data.put("ignored", details);
        final var input = new AuditEvent("principal", "type", data);

        final var result = sut.toPersistentAuditEvent(input);
        // This is a workaround to access the indexed elements in reliable order
        // because we can not use simply the contains() matcher due to the implementation
        // of the generated entities equals() method which only compares the id.
        final var resultData = new ArrayList<>(result.getData());

        assertAll(
            () -> assertThat(result.getData()).hasSize(2),
            () -> assertThat(resultData.get(0).getName()).isEqualTo("remoteAddress"),
            () -> assertThat(resultData.get(0).getValue()).isEqualTo("the-remote-address"),
            () -> assertThat(resultData.get(1).getName()).isEqualTo("sessionId"),
            () -> assertThat(resultData.get(1).getValue()).isEqualTo("the-session-id")
        );
    }

    @Test
    void toPersistentAuditEvent_truncateTooLargeData() {
        final var data = new HashMap<String, Object>();
        data.put("foo", "x".repeat(300));
        final var input = new AuditEvent("principal", "type", data);

        final var result = sut.toPersistentAuditEvent(input);
        // This is a workaround to access the indexed elements in reliable order
        // because we can not use simply the contains() matcher due to the implementation
        // of the generated entities equals() method which only compares the id.
        final var resultData = new ArrayList<>(result.getData());

        assertAll(
            () -> assertThat(result.getData()).hasSize(1),
            () -> assertThat(resultData.get(0).getName()).isEqualTo("foo"),
            () -> assertThat(resultData.get(0).getValue()).hasSizeLessThanOrEqualTo(AuditEventConverter.EVENT_DATA_COLUMN_MAX_LENGTH)
        );
    }

    @Test
    void toAuditEvent_nullConvertsToNull() {
        assertThat(sut.toAuditEvent(null)).isNull();
    }

    @Test
    void toAuditEvent_dataIsEmpty() {
        final var now = Instant.now();
        final var input = new PersistentAuditEvent();
        input.setPrincipal("principal");
        input.setAuditEventType("type");
        input.setAuditEventDate(now);

        final var result = sut.toAuditEvent(input);

        assertAll(
            () -> assertThat(result.getPrincipal()).isEqualTo("principal"),
            () -> assertThat(result.getType()).isEqualTo("type"),
            () -> assertThat(result.getTimestamp()).isEqualTo(now)
        );
    }

    @Test
    void toAuditEvent() {
        final var data = new HashSet<PersistentAuditEventData>();
        data.add(createEventDataFixture("foo", "one"));
        final var now = Instant.now();
        final var input = new PersistentAuditEvent();
        input.setPrincipal("principal");
        input.setAuditEventType("type");
        input.setAuditEventDate(now);
        input.setData(data);

        final var result = sut.toAuditEvent(input);

        assertAll(
            () -> assertThat(result.getPrincipal()).isEqualTo("principal"),
            () -> assertThat(result.getType()).isEqualTo("type"),
            () -> assertThat(result.getTimestamp()).isEqualTo(now),
            () -> assertThat(result.getData()).contains(entry("foo", "one"))
        );
    }

    @Test
    void toMap_nullConvertsToEmpty() {
        assertThat(sut.toMap(null)).isEmpty();
    }

    @Test
    void toMap_emptyConvertsToEmpty() {
        assertThat(sut.toMap(new HashSet<>())).isEmpty();
    }

    @Test
    void toMap() {
        final var input = new HashSet<PersistentAuditEventData>();
        input.add(createEventDataFixture("foo", "one"));
        input.add(createEventDataFixture("bar", "two"));
        input.add(createEventDataFixture("baz", "three"));

        assertThat(sut.toMap(input)).contains(entry("foo", "one"), entry("bar", "two"), entry("baz", "three"));
    }
}
