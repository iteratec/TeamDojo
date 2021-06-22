package com.iteratec.teamdojo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersistentAuditEventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersistentAuditEvent.class);
        PersistentAuditEvent persistentAuditEvent1 = new PersistentAuditEvent();
        persistentAuditEvent1.setId(1L);
        PersistentAuditEvent persistentAuditEvent2 = new PersistentAuditEvent();
        persistentAuditEvent2.setId(persistentAuditEvent1.getId());
        assertThat(persistentAuditEvent1).isEqualTo(persistentAuditEvent2);
        persistentAuditEvent2.setId(2L);
        assertThat(persistentAuditEvent1).isNotEqualTo(persistentAuditEvent2);
        persistentAuditEvent1.setId(null);
        assertThat(persistentAuditEvent1).isNotEqualTo(persistentAuditEvent2);
    }
}
