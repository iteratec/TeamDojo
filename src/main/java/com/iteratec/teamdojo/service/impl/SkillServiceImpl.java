package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.SkillService;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Skill}.
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillServiceImpl.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public SkillDTO save(SkillDTO skillDTO) {
        log.debug("Request to save Skill : {}", skillDTO);
        Skill skill = skillMapper.toEntity(skillDTO);
        skill = skillRepository.save(skill);
        return skillMapper.toDto(skill);
    }

    @Override
    public Optional<SkillDTO> partialUpdate(SkillDTO skillDTO) {
        log.debug("Request to partially update Skill : {}", skillDTO);

        return skillRepository
            .findById(skillDTO.getId())
            .map(existingSkill -> {
                skillMapper.partialUpdate(existingSkill, skillDTO);

                return existingSkill;
            })
            .map(skillRepository::save)
            .map(skillMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Skills");
        return skillRepository.findAll(pageable).map(skillMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SkillDTO> findOne(Long id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findById(id).map(skillMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
    }
}
