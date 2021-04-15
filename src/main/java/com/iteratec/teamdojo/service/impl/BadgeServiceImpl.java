package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.service.BadgeService;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.mapper.BadgeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Badge}.
 */
@Service
@Transactional
public class BadgeServiceImpl implements BadgeService {

    private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);

    private final BadgeRepository badgeRepository;

    private final BadgeMapper badgeMapper;

    public BadgeServiceImpl(BadgeRepository badgeRepository, BadgeMapper badgeMapper) {
        this.badgeRepository = badgeRepository;
        this.badgeMapper = badgeMapper;
    }

    @Override
    public BadgeDTO save(BadgeDTO badgeDTO) {
        log.debug("Request to save Badge : {}", badgeDTO);
        Badge badge = badgeMapper.toEntity(badgeDTO);
        badge = badgeRepository.save(badge);
        return badgeMapper.toDto(badge);
    }

    @Override
    public Optional<BadgeDTO> partialUpdate(BadgeDTO badgeDTO) {
        log.debug("Request to partially update Badge : {}", badgeDTO);

        return badgeRepository
            .findById(badgeDTO.getId())
            .map(
                existingBadge -> {
                    badgeMapper.partialUpdate(existingBadge, badgeDTO);
                    return existingBadge;
                }
            )
            .map(badgeRepository::save)
            .map(badgeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BadgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Badges");
        return badgeRepository.findAll(pageable).map(badgeMapper::toDto);
    }

    public Page<BadgeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return badgeRepository.findAllWithEagerRelationships(pageable).map(badgeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeDTO> findOne(Long id) {
        log.debug("Request to get Badge : {}", id);
        return badgeRepository.findOneWithEagerRelationships(id).map(badgeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Badge : {}", id);
        badgeRepository.deleteById(id);
    }
}
