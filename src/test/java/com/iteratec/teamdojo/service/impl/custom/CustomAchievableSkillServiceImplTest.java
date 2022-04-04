package com.iteratec.teamdojo.service.impl.custom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.repository.TeamRepository;
import com.iteratec.teamdojo.repository.custom.CustomAchievableSkillRepository;
import com.iteratec.teamdojo.repository.custom.ExtendedBadgeRepository;
import com.iteratec.teamdojo.service.TeamSkillService;
import com.iteratec.teamdojo.service.custom.ExtendedActivityService;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.dto.custom.AchievableSkillDTO;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import com.iteratec.teamdojo.service.mapper.TeamMapper;
import com.iteratec.teamdojo.service.mapper.custom.AchievableSkillMapper;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class CustomAchievableSkillServiceImplTest {

    @Mock
    private CustomAchievableSkillRepository achievableSkillRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ExtendedBadgeRepository badgeRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private TeamSkillService teamSkillService;

    @Mock
    private ExtendedActivityService activityService;

    @Mock
    private AchievableSkillMapper achievableSkillMapper;

    @Mock
    private TeamMapper teamMapper;

    @Mock
    private SkillMapper skillMapper;

    private final CustomAchievableSkillServiceImpl sut = new CustomAchievableSkillServiceImpl(
        achievableSkillRepository,
        teamRepository,
        badgeRepository,
        skillRepository,
        teamSkillService,
        activityService,
        achievableSkillMapper,
        teamMapper,
        skillMapper
    );

    @Test
    void isCreateForCompletedSkill() {
        final var originSkill = new AchievableSkillDTO();
        final var originSkillWitAchievedAt = new AchievableSkillDTO();
        originSkillWitAchievedAt.setAchievedAt(Instant.now());
        final var teamSkill = new TeamSkillDTO();
        final var teamSkillWithCompletedAt = new TeamSkillDTO();
        teamSkillWithCompletedAt.setCompletedAt(Instant.now());

        assertAll(
            () -> assertThat(sut.isCreateForCompletedSkill(null, null)).isFalse(),
            () -> assertThat(sut.isCreateForCompletedSkill(null, teamSkill)).isFalse(),
            () -> assertThat(sut.isCreateForCompletedSkill(null, teamSkillWithCompletedAt)).isTrue(),
            () -> assertThat(sut.isCreateForCompletedSkill(originSkill, null)).isFalse(),
            () -> assertThat(sut.isCreateForCompletedSkill(originSkill, teamSkill)).isFalse(),
            () -> assertThat(sut.isCreateForCompletedSkill(originSkill, teamSkillWithCompletedAt)).isTrue(),
            () -> assertThat(sut.isCreateForCompletedSkill(originSkill, teamSkillWithCompletedAt)).isTrue(),
            () -> assertThat(sut.isCreateForCompletedSkill(originSkillWitAchievedAt, teamSkill)).isFalse(),
            () -> assertThat(sut.isCreateForCompletedSkill(originSkillWitAchievedAt, teamSkillWithCompletedAt)).isFalse()
        );
    }

    @Test
    void isOriginSkillNullAndTeamSkillNotNullAndTeamSkillCompletedAtIsSet() {
        final var originSkill = new AchievableSkillDTO();
        final var teamSkill = new TeamSkillDTO();
        final var teamSkillWithCompletedAt = new TeamSkillDTO();
        teamSkillWithCompletedAt.setCompletedAt(Instant.now());

        assertAll(
            () -> assertThat(sut.isOriginSkillNullAndTeamSkillNotNullAndTeamSkillCompletedAtIsSet(null, null)).isFalse(),
            () -> assertThat(sut.isOriginSkillNullAndTeamSkillNotNullAndTeamSkillCompletedAtIsSet(originSkill, null)).isFalse(),
            () -> assertThat(sut.isOriginSkillNullAndTeamSkillNotNullAndTeamSkillCompletedAtIsSet(null, teamSkill)).isFalse(),
            () -> assertThat(sut.isOriginSkillNullAndTeamSkillNotNullAndTeamSkillCompletedAtIsSet(originSkill, teamSkill)).isFalse(),
            () ->
                assertThat(sut.isOriginSkillNullAndTeamSkillNotNullAndTeamSkillCompletedAtIsSet(originSkill, teamSkillWithCompletedAt))
                    .isFalse(),
            () -> assertThat(sut.isOriginSkillNullAndTeamSkillNotNullAndTeamSkillCompletedAtIsSet(null, teamSkillWithCompletedAt)).isTrue()
        );
    }

    @Test
    void areBothSkillsNotNullAndOriginSkillAchievedAtIsSet() {
        final var originSkill = new AchievableSkillDTO();
        final var originSkillWitAchievedAt = new AchievableSkillDTO();
        originSkillWitAchievedAt.setAchievedAt(Instant.now());
        final var teamSkill = new TeamSkillDTO();
        final var teamSkillWithCompletedAt = new TeamSkillDTO();
        teamSkillWithCompletedAt.setCompletedAt(Instant.now());

        assertAll(
            () -> assertThat(sut.areBothSkillsNotNullAndOriginSkillAchievedAtIsSet(null, teamSkill)).isFalse(),
            () -> assertThat(sut.areBothSkillsNotNullAndOriginSkillAchievedAtIsSet(originSkill, teamSkill)).isFalse(),
            () -> assertThat(sut.areBothSkillsNotNullAndOriginSkillAchievedAtIsSet(originSkillWitAchievedAt, teamSkill)).isFalse(),
            () -> assertThat(sut.areBothSkillsNotNullAndOriginSkillAchievedAtIsSet(originSkill, teamSkillWithCompletedAt)).isTrue(),
            () ->
                assertThat(sut.areBothSkillsNotNullAndOriginSkillAchievedAtIsSet(originSkillWitAchievedAt, teamSkillWithCompletedAt))
                    .isFalse()
        );
    }
}
