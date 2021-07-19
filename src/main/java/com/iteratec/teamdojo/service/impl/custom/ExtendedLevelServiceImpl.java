package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.repository.custom.ExtendedLevelRepository;
import com.iteratec.teamdojo.service.custom.ExtendedLevelService;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.impl.LevelServiceImpl;
import com.iteratec.teamdojo.service.mapper.LevelMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class ExtendedLevelServiceImpl extends LevelServiceImpl implements ExtendedLevelService {

    private ExtendedLevelRepository levelRepository;
    private LevelMapper levelMapper;

    public ExtendedLevelServiceImpl(ExtendedLevelRepository levelRepository, LevelMapper levelMapper) {
        super(levelRepository, levelMapper);
        this.levelRepository = levelRepository;
        this.levelMapper = levelMapper;
    }

    @Override
    public Page<LevelDTO> findByIdIn(List<Long> levelIds, Pageable pageable) {
        log.debug("Request to get Levels by level Ids: {}", levelIds);
        return levelRepository.findByIdIn(levelIds, pageable).map(levelMapper::toDto);
    }
}
