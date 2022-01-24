package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.repository.TeamRepository;
import com.iteratec.teamdojo.service.custom.ExtendedTeamService;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.impl.TeamServiceImpl;
import com.iteratec.teamdojo.service.mapper.TeamMapper;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ExtendedTeamServiceImpl extends TeamServiceImpl implements ExtendedTeamService {

    private final AuditableDataTracker<TeamDTO, Team> tracker;

    public ExtendedTeamServiceImpl(TeamRepository repo, TeamMapper mapper) {
        super(repo, mapper);
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    @Override
    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public TeamDTO save(final TeamDTO dto) {
        tracker.modifyCreatedAndUpdatedAt(dto);
        return super.save(dto);
    }
}
