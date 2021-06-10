package com.iteratec.teamdojo.web.rest.ext;

import com.iteratec.teamdojo.service.criteria.BadgeCriteria;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeService;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeSkillService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CustomBadgeResourceExtensionTest {

    private final ExtendedBadgeService badges= mock(ExtendedBadgeService.class);
    private final ExtendedBadgeSkillService badgeSkills = mock(ExtendedBadgeSkillService.class);
    private final CustomBadgeResourceExtension sut = new CustomBadgeResourceExtension(badges, badgeSkills);

    @Test
    void shouldFindBadgesBySkills_falseIfCriteriaIsNull() {
        assertThat(sut.shouldFindBadgesBySkills(null)).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_falseIfSkillsIdIsNull() {
        assertThat(sut.shouldFindBadgesBySkills(new BadgeCriteria())).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_falseIfSkillsIdsGetInIsNull() {
        final var criteria = new BadgeCriteria();
        criteria.skillsId(); // This initializes the value.

        assertThat(sut.shouldFindBadgesBySkills(criteria)).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_trueIfSkillsIdAndGetInAreSet() {
        final var criteria = new BadgeCriteria();
        criteria.skillsId().setIn(Arrays.asList(1L, 2L, 3L)); // This initializes the value.

        assertThat(sut.shouldFindBadgesBySkills(criteria)).isTrue();
    }
}
