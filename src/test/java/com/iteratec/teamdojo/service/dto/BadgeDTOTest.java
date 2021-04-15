package com.iteratec.teamdojo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BadgeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeDTO.class);
        BadgeDTO badgeDTO1 = new BadgeDTO();
        badgeDTO1.setId(1L);
        BadgeDTO badgeDTO2 = new BadgeDTO();
        assertThat(badgeDTO1).isNotEqualTo(badgeDTO2);
        badgeDTO2.setId(badgeDTO1.getId());
        assertThat(badgeDTO1).isEqualTo(badgeDTO2);
        badgeDTO2.setId(2L);
        assertThat(badgeDTO1).isNotEqualTo(badgeDTO2);
        badgeDTO1.setId(null);
        assertThat(badgeDTO1).isNotEqualTo(badgeDTO2);
    }
}
