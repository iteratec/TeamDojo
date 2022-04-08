package com.iteratec.teamdojo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class PersistentAuditEventDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersistentAuditEventData.class);
        PersistentAuditEventData persistentAuditEventData1 = new PersistentAuditEventData();
        persistentAuditEventData1.setId(1L);
        PersistentAuditEventData persistentAuditEventData2 = new PersistentAuditEventData();
        persistentAuditEventData2.setId(persistentAuditEventData1.getId());
        assertThat(persistentAuditEventData1).isEqualTo(persistentAuditEventData2);
        persistentAuditEventData2.setId(2L);
        assertThat(persistentAuditEventData1).isNotEqualTo(persistentAuditEventData2);
        persistentAuditEventData1.setId(null);
        assertThat(persistentAuditEventData1).isNotEqualTo(persistentAuditEventData2);
    }
}
