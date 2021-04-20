package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.repository.BadgeExtendedRepository;
import com.iteratec.teamdojo.service.ActivityExtendedService;
import com.iteratec.teamdojo.service.BadgeService;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.mapper.BadgeMapper;
import java.util.List;
import java.util.Optional;
import org.json.JSONException;
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
public class BadgeServiceExtendedImpl implements BadgeService {

    private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);

    private final BadgeExtendedRepository badgeRepository;

    private final BadgeMapper badgeMapper;

    private final ActivityExtendedService activityService;

    public BadgeServiceExtendedImpl(
        BadgeExtendedRepository badgeRepository,
        BadgeMapper badgeMapper,
        ActivityExtendedService activityService
    ) {
        this.activityService = activityService;
        this.badgeRepository = badgeRepository;
        this.badgeMapper = badgeMapper;
    }

    @Override
    public BadgeDTO save(BadgeDTO badgeDTO) {
        log.debug("Request to save Badge : {}", badgeDTO);
        boolean newBadge = badgeDTO.getId() == null;
        Badge badge = badgeMapper.toEntity(badgeDTO);
        badge = badgeRepository.save(badge);
        badgeDTO = badgeMapper.toDto(badge);
        if (newBadge) {
            /** @diff added try catch */
            try {
                this.activityService.createForNewBadge(badgeDTO);
            } catch (JSONException error) {
                log.error("Error: Coudlnt createNewBadge", error);
            }
        }
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

    public Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable) {
        log.debug("Request to get Badges by Badge Ids: {}", badgeIds);
        return badgeRepository.findByIdIn(badgeIds, pageable).map(badgeMapper::toDto);
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
