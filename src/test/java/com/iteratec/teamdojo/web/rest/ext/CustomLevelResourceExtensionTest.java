package com.iteratec.teamdojo.web.rest.ext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iteratec.teamdojo.service.criteria.LevelCriteria;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedLevelService;
import com.iteratec.teamdojo.service.ext.ExtendedLevelSkillService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

class CustomLevelResourceExtensionTest {

    private final ExtendedLevelSkillService levelSkills = mock(ExtendedLevelSkillService.class);
    private final ExtendedLevelService levels = mock(ExtendedLevelService.class);
    private final CustomLevelResourceExtension sut = new CustomLevelResourceExtension(levels, levelSkills);

    private List<LevelSkillDTO> createLevelSkillFixtures(final List<Long> badgeIds) {
        return createLevelFixtures(badgeIds)
            .stream()
            .map(
                level -> {
                    var levelSkill = new LevelSkillDTO();
                    levelSkill.setLevel(level);
                    return levelSkill;
                }
            )
            .collect(Collectors.toList());
    }

    private List<LevelDTO> createLevelFixtures(final List<Long> levelIds) {
        return levelIds
            .stream()
            .map(
                id -> {
                    var level = new LevelDTO();
                    level.setId(id);
                    return level;
                }
            )
            .collect(Collectors.toList());
    }

    @Test
    void shouldFindBadgesBySkills_falseIfCriteriaIsNull() {
        Assertions.assertThat(sut.shouldFindLevelsBySkillId(null)).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_falseIfSkillsIdIsNull() {
        Assertions.assertThat(sut.shouldFindLevelsBySkillId(new LevelCriteria())).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_falseIfSkillsIdsGetInIsNull() {
        final var criteria = new LevelCriteria();
        criteria.skillsId(); // This initializes the value.

        Assertions.assertThat(sut.shouldFindLevelsBySkillId(criteria)).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_trueIfSkillsIdAndGetInAreSet() {
        final var criteria = new LevelCriteria();
        criteria.skillsId().setIn(Arrays.asList(1L, 2L, 3L)); // This initializes the value.

        Assertions.assertThat(sut.shouldFindLevelsBySkillId(criteria)).isTrue();
    }

    @Test
    @Disabled
    void findLevelsBySkills() {}

    @Test
    void findSkillsAndMapToLevelIds() {
        when(levelSkills.findBySkillIdIn(anyList(), any(Pageable.class))).thenReturn(createLevelSkillFixtures(Arrays.asList(1L, 2L, 3L)));

        final var result = sut.findSkillsAndMapToLevelIds(Collections.emptyList(), mock(Pageable.class));

        assertThat(result).contains(1L, 2L, 3L);
    }
}
