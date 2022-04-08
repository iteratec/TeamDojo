package com.iteratec.teamdojo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class DimensionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DimensionDTO.class);
        DimensionDTO dimensionDTO1 = new DimensionDTO();
        dimensionDTO1.setId(1L);
        DimensionDTO dimensionDTO2 = new DimensionDTO();
        assertThat(dimensionDTO1).isNotEqualTo(dimensionDTO2);
        dimensionDTO2.setId(dimensionDTO1.getId());
        assertThat(dimensionDTO1).isEqualTo(dimensionDTO2);
        dimensionDTO2.setId(2L);
        assertThat(dimensionDTO1).isNotEqualTo(dimensionDTO2);
        dimensionDTO1.setId(null);
        assertThat(dimensionDTO1).isNotEqualTo(dimensionDTO2);
    }
}
