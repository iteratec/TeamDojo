package com.iteratec.teamdojo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class TeamSkillDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamSkillDTO.class);
        TeamSkillDTO teamSkillDTO1 = new TeamSkillDTO();
        teamSkillDTO1.setId(1L);
        TeamSkillDTO teamSkillDTO2 = new TeamSkillDTO();
        assertThat(teamSkillDTO1).isNotEqualTo(teamSkillDTO2);
        teamSkillDTO2.setId(teamSkillDTO1.getId());
        assertThat(teamSkillDTO1).isEqualTo(teamSkillDTO2);
        teamSkillDTO2.setId(2L);
        assertThat(teamSkillDTO1).isNotEqualTo(teamSkillDTO2);
        teamSkillDTO1.setId(null);
        assertThat(teamSkillDTO1).isNotEqualTo(teamSkillDTO2);
    }
}
