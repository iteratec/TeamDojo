package com.iteratec.teamdojo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class BadgeSkillTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeSkill.class);
        BadgeSkill badgeSkill1 = new BadgeSkill();
        badgeSkill1.setId(1L);
        BadgeSkill badgeSkill2 = new BadgeSkill();
        badgeSkill2.setId(badgeSkill1.getId());
        assertThat(badgeSkill1).isEqualTo(badgeSkill2);
        badgeSkill2.setId(2L);
        assertThat(badgeSkill1).isNotEqualTo(badgeSkill2);
        badgeSkill1.setId(null);
        assertThat(badgeSkill1).isNotEqualTo(badgeSkill2);
    }
}
