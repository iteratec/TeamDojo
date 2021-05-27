package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.repository.ext.ExtendedLevelRepository;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.ext.ExtendedLevelService;
import com.iteratec.teamdojo.service.impl.LevelServiceImpl;
import com.iteratec.teamdojo.service.mapper.LevelMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ExtendedLevelServiceImpl extends LevelServiceImpl implements ExtendedLevelService {

    private final Logger log = LoggerFactory.getLogger(ExtendedLevelServiceImpl.class);

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
