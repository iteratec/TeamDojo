/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.repository.custom.ExtendedLevelRepository;
import com.iteratec.teamdojo.service.custom.ExtendedLevelService;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.impl.LevelServiceImpl;
import com.iteratec.teamdojo.service.mapper.LevelMapper;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class ExtendedLevelServiceImpl extends LevelServiceImpl implements ExtendedLevelService {

    private ExtendedLevelRepository repo;
    private LevelMapper mapper;
    private final AuditableDataTracker<LevelDTO, Level> tracker;

    public ExtendedLevelServiceImpl(ExtendedLevelRepository repo, LevelMapper mapper) {
        super(repo, mapper);
        this.repo = repo;
        this.mapper = mapper;
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public Page<LevelDTO> findByIdIn(List<Long> levelIds, Pageable pageable) {
        log.debug("Request to get Levels by level Ids: {}", levelIds);
        return repo.findByIdIn(levelIds, pageable).map(mapper::toDto);
    }

    @Override
    public LevelDTO save(final LevelDTO level) {
        tracker.modifyCreatedAndUpdatedAt(level);
        return super.save(level);
    }
}
