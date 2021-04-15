package com.iteratec.teamdojo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BadgeSkillDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeSkillDTO.class);
        BadgeSkillDTO badgeSkillDTO1 = new BadgeSkillDTO();
        badgeSkillDTO1.setId(1L);
        BadgeSkillDTO badgeSkillDTO2 = new BadgeSkillDTO();
        assertThat(badgeSkillDTO1).isNotEqualTo(badgeSkillDTO2);
        badgeSkillDTO2.setId(badgeSkillDTO1.getId());
        assertThat(badgeSkillDTO1).isEqualTo(badgeSkillDTO2);
        badgeSkillDTO2.setId(2L);
        assertThat(badgeSkillDTO1).isNotEqualTo(badgeSkillDTO2);
        badgeSkillDTO1.setId(null);
        assertThat(badgeSkillDTO1).isNotEqualTo(badgeSkillDTO2);
    }
}
