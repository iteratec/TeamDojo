package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.repository.LevelRepository;
import com.iteratec.teamdojo.service.LevelService;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.mapper.LevelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Level}.
 */
@Service
@Transactional
@GeneratedByJHipster
public class LevelServiceImpl implements LevelService {

    private final Logger log = LoggerFactory.getLogger(LevelServiceImpl.class);

    private final LevelRepository levelRepository;

    private final LevelMapper levelMapper;

    public LevelServiceImpl(LevelRepository levelRepository, LevelMapper levelMapper) {
        this.levelRepository = levelRepository;
        this.levelMapper = levelMapper;
    }

    @Override
    public LevelDTO save(LevelDTO levelDTO) {
        log.debug("Request to save Level : {}", levelDTO);
        Level level = levelMapper.toEntity(levelDTO);
        level = levelRepository.save(level);
        return levelMapper.toDto(level);
    }

    @Override
    public LevelDTO update(LevelDTO levelDTO) {
        log.debug("Request to save Level : {}", levelDTO);
        Level level = levelMapper.toEntity(levelDTO);
        level = levelRepository.save(level);
        return levelMapper.toDto(level);
    }

    @Override
    public Optional<LevelDTO> partialUpdate(LevelDTO levelDTO) {
        log.debug("Request to partially update Level : {}", levelDTO);

        return levelRepository
            .findById(levelDTO.getId())
            .map(existingLevel -> {
                levelMapper.partialUpdate(existingLevel, levelDTO);

                return existingLevel;
            })
            .map(levelRepository::save)
            .map(levelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Levels");
        return levelRepository.findAll(pageable).map(levelMapper::toDto);
    }

    public Page<LevelDTO> findAllWithEagerRelationships(Pageable pageable) {
        return levelRepository.findAllWithEagerRelationships(pageable).map(levelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LevelDTO> findOne(Long id) {
        log.debug("Request to get Level : {}", id);
        return levelRepository.findOneWithEagerRelationships(id).map(levelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Level : {}", id);
        levelRepository.deleteById(id);
    }
}
