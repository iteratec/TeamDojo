package com.iteratec.teamdojo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class LevelSkillTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelSkill.class);
        LevelSkill levelSkill1 = new LevelSkill();
        levelSkill1.setId(1L);
        LevelSkill levelSkill2 = new LevelSkill();
        levelSkill2.setId(levelSkill1.getId());
        assertThat(levelSkill1).isEqualTo(levelSkill2);
        levelSkill2.setId(2L);
        assertThat(levelSkill1).isNotEqualTo(levelSkill2);
        levelSkill1.setId(null);
        assertThat(levelSkill1).isNotEqualTo(levelSkill2);
    }
}
