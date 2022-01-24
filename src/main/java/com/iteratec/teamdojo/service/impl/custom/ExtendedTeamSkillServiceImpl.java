package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.TeamSkill;
import com.iteratec.teamdojo.repository.TeamSkillRepository;
import com.iteratec.teamdojo.service.custom.ExtendedTeamSkillService;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.impl.TeamSkillServiceImpl;
import com.iteratec.teamdojo.service.mapper.TeamSkillMapper;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ExtendedTeamSkillServiceImpl extends TeamSkillServiceImpl implements ExtendedTeamSkillService {

    private final AuditableDataTracker<TeamSkillDTO, TeamSkill> tracker;

    public ExtendedTeamSkillServiceImpl(TeamSkillRepository repo, TeamSkillMapper mapper) {
        super(repo, mapper);
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    @Override
    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public TeamSkillDTO save(final TeamSkillDTO dto) {
        tracker.modifyCreatedAndUpdatedAt(dto);
        return super.save(dto);
    }
}
