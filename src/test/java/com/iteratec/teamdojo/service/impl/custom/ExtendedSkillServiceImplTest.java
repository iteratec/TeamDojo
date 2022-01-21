package com.iteratec.teamdojo.service.impl.custom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import com.iteratec.teamdojo.service.mapper.SkillMapperImpl;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

class ExtendedSkillServiceImplTest {

    public static final double PRECISION = 0.00001;
    private final SkillRepository repository = mock(SkillRepository.class);
    private final SkillMapper mapper = new SkillMapperImpl();
    private final ExtendedSkillServiceImpl sut = new ExtendedSkillServiceImpl(repository, mapper);

    private static Skill newSkill(final int rateCount, final double rateScore) {
        final var skill = new Skill();
        skill.setRateCount(rateCount);
        skill.setRateScore(rateScore);
        return skill;
    }

    @Test
    void createVote_noSkillFoundForId() {
        when(repository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));
        final var skill = sut.createVote(42, 23);

        assertAll(
            () -> assertThat(skill).isNotNull(),
            () -> assertThat(skill.getRateScore()).isCloseTo(23.0, within(PRECISION)),
            () -> assertThat(skill.getRateCount()).isEqualTo(1)
        );
    }

    @Test
    void createVote() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(newSkill(5, 2.5)));
        when(repository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));
        final var skill = sut.createVote(42, 23);

        assertAll(
            () -> assertThat(skill).isNotNull(),
            () -> assertThat(skill.getRateScore()).isCloseTo(5.91666666666666, within(PRECISION)),
            () -> assertThat(skill.getRateCount()).isEqualTo(6)
        );
    }

    private static Stream<Arguments> calculateAverageRate_defaultSkillObjectWithNullValues() {
        return Stream.of(
            Arguments.of(-23, -23.0),
            Arguments.of(-1, -1.0),
            Arguments.of(0, 0.0),
            Arguments.of(1, 1.0),
            Arguments.of(2, 2.0),
            Arguments.of(42, 42.0)
        );
    }

    @MethodSource
    @ParameterizedTest
    void calculateAverageRate_defaultSkillObjectWithNullValues(final int rateScore, final double expectedAverageRate) {
        assertThat(sut.calculateAverageRate(new Skill(), rateScore)).isCloseTo(expectedAverageRate, within(PRECISION));
    }

    private static Stream<Arguments> calculateAverageRate_skillObjectWithAllFiveScores() {
        return Stream.of(
            Arguments.of(0, 4.16666666666666),
            Arguments.of(1, 4.33333333333333),
            Arguments.of(2, 4.5),
            Arguments.of(3, 4.66666666666666),
            Arguments.of(4, 4.83333333333333),
            Arguments.of(5, 5.0)
        );
    }

    @MethodSource
    @ParameterizedTest
    void calculateAverageRate_skillObjectWithAllFiveScores(final int rateScore, final double expectedAverageRate) {
        final var skill = newSkill(5, 5.0);

        assertThat(sut.calculateAverageRate(skill, rateScore)).isCloseTo(expectedAverageRate, within(PRECISION));
    }

    private static Stream<Arguments> calculateAverageRate_skillObjectWithDifferentScores() {
        return Stream.of(
            Arguments.of(newSkill(1, 1.0), 1, 1.0),
            Arguments.of(newSkill(2, 2.0), 2, 2.0),
            Arguments.of(newSkill(3, 3.0), 3, 3.0),
            Arguments.of(newSkill(4, 4.0), 4, 4.0),
            Arguments.of(newSkill(5, 5.0), 5, 5.0),
            Arguments.of(newSkill(6, 6.0), 6, 6.0),
            Arguments.of(newSkill(23, 42.0), 666, 68.0)
        );
    }

    @MethodSource
    @ParameterizedTest
    void calculateAverageRate_skillObjectWithDifferentScores(final Skill skill, final int rateScore, final double expectedAverageRate) {
        assertThat(sut.calculateAverageRate(skill, rateScore)).isCloseTo(expectedAverageRate, within(PRECISION));
    }
}
