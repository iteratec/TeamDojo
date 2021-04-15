package com.iteratec.teamdojo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Training.class);
        Training training1 = new Training();
        training1.setId(1L);
        Training training2 = new Training();
        training2.setId(training1.getId());
        assertThat(training1).isEqualTo(training2);
        training2.setId(2L);
        assertThat(training1).isNotEqualTo(training2);
        training1.setId(null);
        assertThat(training1).isNotEqualTo(training2);
    }
}
