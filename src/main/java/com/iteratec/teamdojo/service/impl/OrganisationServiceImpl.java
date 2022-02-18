package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.domain.Organisation;
import com.iteratec.teamdojo.repository.OrganisationRepository;
import com.iteratec.teamdojo.service.OrganisationService;
import com.iteratec.teamdojo.service.dto.OrganisationDTO;
import com.iteratec.teamdojo.service.mapper.OrganisationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Organisation}.
 */
@Service
@Transactional
public class OrganisationServiceImpl implements OrganisationService {

    private final Logger log = LoggerFactory.getLogger(OrganisationServiceImpl.class);

    private final OrganisationRepository organisationRepository;

    private final OrganisationMapper organisationMapper;

    public OrganisationServiceImpl(OrganisationRepository organisationRepository, OrganisationMapper organisationMapper) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
    }

    @Override
    public OrganisationDTO save(OrganisationDTO organisationDTO) {
        log.debug("Request to save Organisation : {}", organisationDTO);
        Organisation organisation = organisationMapper.toEntity(organisationDTO);
        organisation = organisationRepository.save(organisation);
        return organisationMapper.toDto(organisation);
    }

    @Override
    public Optional<OrganisationDTO> partialUpdate(OrganisationDTO organisationDTO) {
        log.debug("Request to partially update Organisation : {}", organisationDTO);

        return organisationRepository
            .findById(organisationDTO.getId())
            .map(existingOrganisation -> {
                organisationMapper.partialUpdate(existingOrganisation, organisationDTO);

                return existingOrganisation;
            })
            .map(organisationRepository::save)
            .map(organisationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganisationDTO> findAll() {
        log.debug("Request to get all Organisations");
        return organisationRepository.findAll().stream().map(organisationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganisationDTO> findOne(Long id) {
        log.debug("Request to get Organisation : {}", id);
        return organisationRepository.findById(id).map(organisationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organisation : {}", id);
        organisationRepository.deleteById(id);
    }
}
