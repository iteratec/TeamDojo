/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.*;
import com.iteratec.teamdojo.domain.enumeration.ActivityType;
import com.iteratec.teamdojo.repository.ActivityRepository;
import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.repository.TeamRepository;
import com.iteratec.teamdojo.service.custom.ExtendedActivityService;
import com.iteratec.teamdojo.service.dto.ActivityDTO;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.impl.ActivityServiceImpl;
import com.iteratec.teamdojo.service.mapper.ActivityMapper;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class ExtendedActivityServiceImpl extends ActivityServiceImpl implements ExtendedActivityService {

    private final BadgeRepository badgeRepository;
    private final TeamRepository teamRepository;
    private final SkillRepository skillRepository;
    private final AuditableDataTracker<ActivityDTO, Activity> tracker;

    public ExtendedActivityServiceImpl(
        ActivityRepository activityRepository,
        ActivityMapper activityMapper,
        BadgeRepository badgeRepository,
        TeamRepository teamRepository,
        SkillRepository skillRepository
    ) {
        super(activityRepository, activityMapper);
        this.badgeRepository = badgeRepository;
        this.teamRepository = teamRepository;
        this.skillRepository = skillRepository;
        this.tracker = new AuditableDataTracker<>(activityMapper, activityRepository::findById);
    }

    @Override
    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public ActivityDTO createForNewBadge(BadgeDTO badgeDTO) {
        Badge badge = badgeRepository.getOne(badgeDTO.getId());
        JSONObject data = new JSONObject();
        data.put("badgeId", badge.getId());
        data.put("badgeName", badge.getTitleEN());

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setType(ActivityType.BADGE_CREATED);
        activityDTO.setCreatedAt(Instant.now());
        activityDTO.setData(data.toString());
        log.debug("Request to create activity for BADGE_CREATED {}", activityDTO);

        return save(activityDTO);
    }

    @Override
    public ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill) {
        Team team = teamRepository.getOne(teamSkill.getTeam().getId());
        Skill skill = skillRepository.getOne(teamSkill.getSkill().getId());

        JSONObject data = new JSONObject();
        data.put("teamId", team.getId());
        data.put("teamName", team.getTitle());
        data.put("skillId", skill.getId());
        data.put("skillTitle", skill.getTitleEN());

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setType(ActivityType.SKILL_COMPLETED);
        activityDTO.setCreatedAt(Instant.now());
        activityDTO.setData(data.toString());
        log.debug("Request to create activity for SKILL_COMPLETED {}", activityDTO);

        return save(activityDTO);
    }

    @Override
    public ActivityDTO save(final ActivityDTO activity) {
        tracker.modifyCreatedAndUpdatedAt(activity);
        return super.save(activity);
    }
}
