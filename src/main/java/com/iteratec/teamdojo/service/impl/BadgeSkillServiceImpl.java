package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.repository.BadgeSkillRepository;
import com.iteratec.teamdojo.service.BadgeSkillService;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.mapper.BadgeSkillMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BadgeSkill}.
 */
@Service
@Transactional
public class BadgeSkillServiceImpl implements BadgeSkillService {

    private final Logger log = LoggerFactory.getLogger(BadgeSkillServiceImpl.class);

    private final BadgeSkillRepository badgeSkillRepository;

    private final BadgeSkillMapper badgeSkillMapper;

    public BadgeSkillServiceImpl(BadgeSkillRepository badgeSkillRepository, BadgeSkillMapper badgeSkillMapper) {
        this.badgeSkillRepository = badgeSkillRepository;
        this.badgeSkillMapper = badgeSkillMapper;
    }

    @Override
    public BadgeSkillDTO save(BadgeSkillDTO badgeSkillDTO) {
        log.debug("Request to save BadgeSkill : {}", badgeSkillDTO);
        BadgeSkill badgeSkill = badgeSkillMapper.toEntity(badgeSkillDTO);
        badgeSkill = badgeSkillRepository.save(badgeSkill);
        return badgeSkillMapper.toDto(badgeSkill);
    }

    @Override
    public Optional<BadgeSkillDTO> partialUpdate(BadgeSkillDTO badgeSkillDTO) {
        log.debug("Request to partially update BadgeSkill : {}", badgeSkillDTO);

        return badgeSkillRepository
            .findById(badgeSkillDTO.getId())
            .map(existingBadgeSkill -> {
                badgeSkillMapper.partialUpdate(existingBadgeSkill, badgeSkillDTO);

                return existingBadgeSkill;
            })
            .map(badgeSkillRepository::save)
            .map(badgeSkillMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BadgeSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BadgeSkills");
        return badgeSkillRepository.findAll(pageable).map(badgeSkillMapper::toDto);
    }

    public Page<BadgeSkillDTO> findAllWithEagerRelationships(Pageable pageable) {
        return badgeSkillRepository.findAllWithEagerRelationships(pageable).map(badgeSkillMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeSkillDTO> findOne(Long id) {
        log.debug("Request to get BadgeSkill : {}", id);
        return badgeSkillRepository.findOneWithEagerRelationships(id).map(badgeSkillMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BadgeSkill : {}", id);
        badgeSkillRepository.deleteById(id);
    }
}
