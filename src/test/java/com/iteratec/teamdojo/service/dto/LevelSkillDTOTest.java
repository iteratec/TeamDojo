package com.iteratec.teamdojo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelSkillDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelSkillDTO.class);
        LevelSkillDTO levelSkillDTO1 = new LevelSkillDTO();
        levelSkillDTO1.setId(1L);
        LevelSkillDTO levelSkillDTO2 = new LevelSkillDTO();
        assertThat(levelSkillDTO1).isNotEqualTo(levelSkillDTO2);
        levelSkillDTO2.setId(levelSkillDTO1.getId());
        assertThat(levelSkillDTO1).isEqualTo(levelSkillDTO2);
        levelSkillDTO2.setId(2L);
        assertThat(levelSkillDTO1).isNotEqualTo(levelSkillDTO2);
        levelSkillDTO1.setId(null);
        assertThat(levelSkillDTO1).isNotEqualTo(levelSkillDTO2);
    }
}
