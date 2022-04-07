package com.iteratec.teamdojo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamGroup.class);
        TeamGroup teamGroup1 = new TeamGroup();
        teamGroup1.setId(1L);
        TeamGroup teamGroup2 = new TeamGroup();
        teamGroup2.setId(teamGroup1.getId());
        assertThat(teamGroup1).isEqualTo(teamGroup2);
        teamGroup2.setId(2L);
        assertThat(teamGroup1).isNotEqualTo(teamGroup2);
        teamGroup1.setId(null);
        assertThat(teamGroup1).isNotEqualTo(teamGroup2);
    }
}
