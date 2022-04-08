package com.iteratec.teamdojo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class LevelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelDTO.class);
        LevelDTO levelDTO1 = new LevelDTO();
        levelDTO1.setId(1L);
        LevelDTO levelDTO2 = new LevelDTO();
        assertThat(levelDTO1).isNotEqualTo(levelDTO2);
        levelDTO2.setId(levelDTO1.getId());
        assertThat(levelDTO1).isEqualTo(levelDTO2);
        levelDTO2.setId(2L);
        assertThat(levelDTO1).isNotEqualTo(levelDTO2);
        levelDTO1.setId(null);
        assertThat(levelDTO1).isNotEqualTo(levelDTO2);
    }
}
