package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.TeamGroup;
import com.iteratec.teamdojo.repository.TeamGroupRepository;
import com.iteratec.teamdojo.service.TeamGroupService;
import com.iteratec.teamdojo.service.dto.TeamGroupDTO;
import com.iteratec.teamdojo.service.mapper.TeamGroupMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TeamGroup}.
 */
@Service
@Transactional
@GeneratedByJHipster
public class TeamGroupServiceImpl implements TeamGroupService {

    private final Logger log = LoggerFactory.getLogger(TeamGroupServiceImpl.class);

    private final TeamGroupRepository teamGroupRepository;

    private final TeamGroupMapper teamGroupMapper;

    public TeamGroupServiceImpl(TeamGroupRepository teamGroupRepository, TeamGroupMapper teamGroupMapper) {
        this.teamGroupRepository = teamGroupRepository;
        this.teamGroupMapper = teamGroupMapper;
    }

    @Override
    public TeamGroupDTO save(TeamGroupDTO teamGroupDTO) {
        log.debug("Request to save TeamGroup : {}", teamGroupDTO);
        TeamGroup teamGroup = teamGroupMapper.toEntity(teamGroupDTO);
        teamGroup = teamGroupRepository.save(teamGroup);
        return teamGroupMapper.toDto(teamGroup);
    }

    @Override
    public Optional<TeamGroupDTO> partialUpdate(TeamGroupDTO teamGroupDTO) {
        log.debug("Request to partially update TeamGroup : {}", teamGroupDTO);

        return teamGroupRepository
            .findById(teamGroupDTO.getId())
            .map(existingTeamGroup -> {
                teamGroupMapper.partialUpdate(existingTeamGroup, teamGroupDTO);

                return existingTeamGroup;
            })
            .map(teamGroupRepository::save)
            .map(teamGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeamGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeamGroups");
        return teamGroupRepository.findAll(pageable).map(teamGroupMapper::toDto);
    }

    public Page<TeamGroupDTO> findAllWithEagerRelationships(Pageable pageable) {
        return teamGroupRepository.findAllWithEagerRelationships(pageable).map(teamGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeamGroupDTO> findOne(Long id) {
        log.debug("Request to get TeamGroup : {}", id);
        return teamGroupRepository.findOneWithEagerRelationships(id).map(teamGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeamGroup : {}", id);
        teamGroupRepository.deleteById(id);
    }
}
