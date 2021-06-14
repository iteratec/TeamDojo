package com.iteratec.teamdojo.web.rest.ext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iteratec.teamdojo.service.criteria.BadgeCriteria;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeService;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeSkillService;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

class CustomBadgeResourceExtensionTest {

    private final ExtendedBadgeService badges = mock(ExtendedBadgeService.class);
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

    @Test
    @Disabled
    void findBadgesBySkills() {}

    @Test
    void findSkillsAndMapToBadgeIds() {
        final var badgeOne = new BadgeDTO();
        badgeOne.setId(1L);
        final var badgeTwo = new BadgeDTO();
        badgeTwo.setId(2L);
        final var badgeThree = new BadgeDTO();
        badgeThree.setId(3L);
        final var badgeSkillOne = new BadgeSkillDTO();
        badgeSkillOne.setBadge(badgeOne);
        final var badgeSkillTwo = new BadgeSkillDTO();
        badgeSkillTwo.setBadge(badgeTwo);
        final var badgeSkillThree = new BadgeSkillDTO();
        badgeSkillThree.setBadge(badgeThree);
        when(badgeSkills.findBySkillIdIn(anyList(), any(Pageable.class)))
            .thenReturn(Arrays.asList(badgeSkillOne, badgeSkillTwo, badgeSkillThree));

        final var result = sut.findSkillsAndMapToBadgeIds(Collections.emptyList(), mock(Pageable.class));

        assertThat(result).contains(1L, 2L, 3L);
    }
}
