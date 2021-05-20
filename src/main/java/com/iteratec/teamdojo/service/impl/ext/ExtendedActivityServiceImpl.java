package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.repository.ActivityRepository;
import com.iteratec.teamdojo.service.dto.ActivityDTO;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedActivityService;
import com.iteratec.teamdojo.service.impl.ActivityServiceImpl;
import com.iteratec.teamdojo.service.mapper.ActivityMapper;
import org.springframework.stereotype.Service;

@Service
public class ExtendedActivityServiceImpl extends ActivityServiceImpl implements ExtendedActivityService {

    public ExtendedActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        super(activityRepository, activityMapper);
    }

    @Override
    public ActivityDTO createForNewBadge(BadgeDTO badgeDTO) {
        return null;
    }

    @Override
    public ActivityDTO createForCompletedSkill(TeamSkillDTO teamSkill) {
        return null;
    }

    @Override
    public void createForSuggestedSkill(TeamSkillDTO teamSkill) {}
}
