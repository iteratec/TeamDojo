package com.iteratec.teamdojo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class TeamGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamGroupDTO.class);
        TeamGroupDTO teamGroupDTO1 = new TeamGroupDTO();
        teamGroupDTO1.setId(1L);
        TeamGroupDTO teamGroupDTO2 = new TeamGroupDTO();
        assertThat(teamGroupDTO1).isNotEqualTo(teamGroupDTO2);
        teamGroupDTO2.setId(teamGroupDTO1.getId());
        assertThat(teamGroupDTO1).isEqualTo(teamGroupDTO2);
        teamGroupDTO2.setId(2L);
        assertThat(teamGroupDTO1).isNotEqualTo(teamGroupDTO2);
        teamGroupDTO1.setId(null);
        assertThat(teamGroupDTO1).isNotEqualTo(teamGroupDTO2);
    }
}
