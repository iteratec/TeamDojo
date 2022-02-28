package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.repository.LevelSkillRepository;
import com.iteratec.teamdojo.service.LevelSkillService;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.mapper.LevelSkillMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LevelSkill}.
 */
@Service
@Transactional
public class LevelSkillServiceImpl implements LevelSkillService {

    private final Logger log = LoggerFactory.getLogger(LevelSkillServiceImpl.class);

    private final LevelSkillRepository levelSkillRepository;

    private final LevelSkillMapper levelSkillMapper;

    public LevelSkillServiceImpl(LevelSkillRepository levelSkillRepository, LevelSkillMapper levelSkillMapper) {
        this.levelSkillRepository = levelSkillRepository;
        this.levelSkillMapper = levelSkillMapper;
    }

    @Override
    public LevelSkillDTO save(LevelSkillDTO levelSkillDTO) {
        log.debug("Request to save LevelSkill : {}", levelSkillDTO);
        LevelSkill levelSkill = levelSkillMapper.toEntity(levelSkillDTO);
        levelSkill = levelSkillRepository.save(levelSkill);
        return levelSkillMapper.toDto(levelSkill);
    }

    @Override
    public Optional<LevelSkillDTO> partialUpdate(LevelSkillDTO levelSkillDTO) {
        log.debug("Request to partially update LevelSkill : {}", levelSkillDTO);

        return levelSkillRepository
            .findById(levelSkillDTO.getId())
            .map(existingLevelSkill -> {
                levelSkillMapper.partialUpdate(existingLevelSkill, levelSkillDTO);

                return existingLevelSkill;
            })
            .map(levelSkillRepository::save)
            .map(levelSkillMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LevelSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LevelSkills");
        return levelSkillRepository.findAll(pageable).map(levelSkillMapper::toDto);
    }

    public Page<LevelSkillDTO> findAllWithEagerRelationships(Pageable pageable) {
        return levelSkillRepository.findAllWithEagerRelationships(pageable).map(levelSkillMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LevelSkillDTO> findOne(Long id) {
        log.debug("Request to get LevelSkill : {}", id);
        return levelSkillRepository.findOneWithEagerRelationships(id).map(levelSkillMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LevelSkill : {}", id);
        levelSkillRepository.deleteById(id);
    }
}
