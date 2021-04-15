package com.iteratec.teamdojo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamSkillTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamSkill.class);
        TeamSkill teamSkill1 = new TeamSkill();
        teamSkill1.setId(1L);
        TeamSkill teamSkill2 = new TeamSkill();
        teamSkill2.setId(teamSkill1.getId());
        assertThat(teamSkill1).isEqualTo(teamSkill2);
        teamSkill2.setId(2L);
        assertThat(teamSkill1).isNotEqualTo(teamSkill2);
        teamSkill1.setId(null);
        assertThat(teamSkill1).isNotEqualTo(teamSkill2);
    }
}
