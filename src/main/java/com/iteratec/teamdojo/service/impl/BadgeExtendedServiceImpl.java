package com.iteratec.teamdojo.service.impl;

import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.repository.BadgeExtendedRepository;
import com.iteratec.teamdojo.service.BadgeExtendedService;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.mapper.BadgeMapper;
import java.util.List;
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
public class BadgeExtendedServiceImpl extends BadgeServiceImpl implements BadgeExtendedService {

    private final Logger log = LoggerFactory.getLogger(BadgeExtendedServiceImpl.class);

    private final BadgeExtendedRepository badgeRepository;

    private final BadgeMapper badgeMapper;

    public BadgeExtendedServiceImpl(BadgeExtendedRepository badgeRepository, BadgeMapper badgeMapper) {
        super(badgeRepository, badgeMapper);
        this.badgeRepository = badgeRepository;
        this.badgeMapper = badgeMapper;
    }

    public Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable) {
        log.debug("Request to get Badges by Badge Ids: {}", badgeIds);
        return badgeRepository.findByIdIn(badgeIds, pageable).map(badgeMapper::toDto);
    }
}
