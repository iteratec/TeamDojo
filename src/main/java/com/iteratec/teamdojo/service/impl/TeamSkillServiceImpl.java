package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.TeamSkill;
import com.iteratec.teamdojo.repository.TeamSkillRepository;
import com.iteratec.teamdojo.service.TeamSkillService;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.mapper.TeamSkillMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TeamSkill}.
 */
@Service
@Transactional
@GeneratedByJHipster
public class TeamSkillServiceImpl implements TeamSkillService {

    private final Logger log = LoggerFactory.getLogger(TeamSkillServiceImpl.class);

    private final TeamSkillRepository teamSkillRepository;

    private final TeamSkillMapper teamSkillMapper;

    public TeamSkillServiceImpl(TeamSkillRepository teamSkillRepository, TeamSkillMapper teamSkillMapper) {
        this.teamSkillRepository = teamSkillRepository;
        this.teamSkillMapper = teamSkillMapper;
    }

    @Override
    public TeamSkillDTO save(TeamSkillDTO teamSkillDTO) {
        log.debug("Request to save TeamSkill : {}", teamSkillDTO);
        TeamSkill teamSkill = teamSkillMapper.toEntity(teamSkillDTO);
        teamSkill = teamSkillRepository.save(teamSkill);
        return teamSkillMapper.toDto(teamSkill);
    }

    @Override
    public TeamSkillDTO update(TeamSkillDTO teamSkillDTO) {
        log.debug("Request to save TeamSkill : {}", teamSkillDTO);
        TeamSkill teamSkill = teamSkillMapper.toEntity(teamSkillDTO);
        teamSkill = teamSkillRepository.save(teamSkill);
        return teamSkillMapper.toDto(teamSkill);
    }

    @Override
    public Optional<TeamSkillDTO> partialUpdate(TeamSkillDTO teamSkillDTO) {
        log.debug("Request to partially update TeamSkill : {}", teamSkillDTO);

        return teamSkillRepository
            .findById(teamSkillDTO.getId())
            .map(existingTeamSkill -> {
                teamSkillMapper.partialUpdate(existingTeamSkill, teamSkillDTO);

                return existingTeamSkill;
            })
            .map(teamSkillRepository::save)
            .map(teamSkillMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeamSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeamSkills");
        return teamSkillRepository.findAll(pageable).map(teamSkillMapper::toDto);
    }

    public Page<TeamSkillDTO> findAllWithEagerRelationships(Pageable pageable) {
        return teamSkillRepository.findAllWithEagerRelationships(pageable).map(teamSkillMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeamSkillDTO> findOne(Long id) {
        log.debug("Request to get TeamSkill : {}", id);
        return teamSkillRepository.findOneWithEagerRelationships(id).map(teamSkillMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeamSkill : {}", id);
        teamSkillRepository.deleteById(id);
    }
}
