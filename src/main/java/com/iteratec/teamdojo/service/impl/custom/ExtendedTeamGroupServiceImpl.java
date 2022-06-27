/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.TeamGroup;
import com.iteratec.teamdojo.repository.TeamGroupRepository;
import com.iteratec.teamdojo.service.custom.ExtendedTeamGroupService;
import com.iteratec.teamdojo.service.dto.TeamGroupDTO;
import com.iteratec.teamdojo.service.impl.TeamGroupServiceImpl;
import com.iteratec.teamdojo.service.mapper.TeamGroupMapper;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ExtendedTeamGroupServiceImpl extends TeamGroupServiceImpl implements ExtendedTeamGroupService {

    private final AuditableDataTracker<TeamGroupDTO, TeamGroup> tracker;

    public ExtendedTeamGroupServiceImpl(final TeamGroupRepository repo, final TeamGroupMapper mapper) {
        super(repo, mapper);
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    @Override
    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public TeamGroupDTO save(final TeamGroupDTO dto) {
        tracker.modifyCreatedAndUpdatedAt(dto);
        return super.save(dto);
    }
}
