package com.iteratec.teamdojo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Level.class);
        Level level1 = new Level();
        level1.setId(1L);
        Level level2 = new Level();
        level2.setId(level1.getId());
        assertThat(level1).isEqualTo(level2);
        level2.setId(2L);
        assertThat(level1).isNotEqualTo(level2);
        level1.setId(null);
        assertThat(level1).isNotEqualTo(level2);
    }
}
