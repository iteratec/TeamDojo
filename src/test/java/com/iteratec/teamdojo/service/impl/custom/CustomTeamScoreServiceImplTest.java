package com.iteratec.teamdojo.service.impl.custom;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.repository.LevelRepository;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.TeamSkillQueryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 *
 */
class CustomTeamScoreServiceImplTest {

    @Mock
    private TeamSkillQueryService teamSkillQueryService;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @Mock
    private LevelRepository levelRepository;

    private final CustomTeamScoreServiceImpl sut = new CustomTeamScoreServiceImpl(
        teamSkillQueryService,
        skillRepository,
        badgeRepository,
        levelRepository
    );

    @Test
    void calculateProgress() {}

    @Test
    void calculateTotalScore() {}

    @Test
    void calculateRequired() {}

    @Test
    void calculateAchieved() {}

    @Test
    void calculateProgressInPercent() {}

    @Test
    void calculateCompleted() {
        assertAll(
            () -> assertThat(sut.calculateCompleted(-1, 0)).isFalse(),
            () -> assertThat(sut.calculateCompleted(0, -1)).isTrue(),
            () -> assertThat(sut.calculateCompleted(-1, -1)).isTrue(),
            () -> assertThat(sut.calculateCompleted(0, 0)).isTrue(),
            () -> assertThat(sut.calculateCompleted(1, 0)).isTrue(),
            () -> assertThat(sut.calculateCompleted(2, 0)).isTrue(),
            () -> assertThat(sut.calculateCompleted(3, 0)).isTrue(),
            () -> assertThat(sut.calculateCompleted(3, 1)).isTrue(),
            () -> assertThat(sut.calculateCompleted(3, 2)).isTrue(),
            () -> assertThat(sut.calculateCompleted(3, 3)).isTrue(),
            () -> assertThat(sut.calculateCompleted(3, 4)).isFalse(),
            () -> assertThat(sut.calculateCompleted(3, 5)).isFalse(),
            () -> assertThat(sut.calculateCompleted(Integer.MAX_VALUE, Integer.MAX_VALUE)).isTrue(),
            () -> assertThat(sut.calculateCompleted(Integer.MAX_VALUE, Integer.MIN_VALUE)).isTrue(),
            () -> assertThat(sut.calculateCompleted(Integer.MIN_VALUE, Integer.MAX_VALUE)).isFalse(),
            () -> assertThat(sut.calculateCompleted(Integer.MIN_VALUE, Integer.MIN_VALUE)).isTrue()
        );
    }
}
