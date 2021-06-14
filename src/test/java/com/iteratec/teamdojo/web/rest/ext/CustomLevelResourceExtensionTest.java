package com.iteratec.teamdojo.web.rest.ext;

import static org.mockito.Mockito.mock;

import com.iteratec.teamdojo.service.criteria.LevelCriteria;
import com.iteratec.teamdojo.service.ext.ExtendedLevelService;
import com.iteratec.teamdojo.service.ext.ExtendedLevelSkillService;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomLevelResourceExtensionTest {

    private final ExtendedLevelSkillService levelSkills = mock(ExtendedLevelSkillService.class);
    private final ExtendedLevelService levels = mock(ExtendedLevelService.class);
    private final CustomLevelResourceExtension sut = new CustomLevelResourceExtension(levels, levelSkills);

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
}
