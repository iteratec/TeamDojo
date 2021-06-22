package com.iteratec.teamdojo.config.ext;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AuditEventConverterTest {

    private final AuditEventConverter sut = new AuditEventConverter();

    @Test
    void toAuditEvents_nullConvertsToEmptyList() {
        assertThat(sut.toAuditEvents(null)).isEmpty();
    }

    @Test
    @Disabled
    void toAuditEvents_inputContainsNull() {}

    @Test
    @Disabled
    void toAuditEvents() {}

    @Test
    void toAuditEvent_nullConvertsToNull() {
        assertThat(sut.toAuditEvent(null)).isNull();
    }

    @Test
    @Disabled
    void toAuditEvent() {}
}
