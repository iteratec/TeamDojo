package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.repository.ext.ExtendedBadgeRepository;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeService;
import com.iteratec.teamdojo.service.impl.BadgeServiceImpl;
import com.iteratec.teamdojo.service.mapper.BadgeMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ExtendedBadgeServiceImpl extends BadgeServiceImpl implements ExtendedBadgeService {

    private final Logger log = LoggerFactory.getLogger(ExtendedBadgeServiceImpl.class);
    private final ExtendedBadgeRepository badgeRepository;
    private final BadgeMapper badgeMapper;

    public ExtendedBadgeServiceImpl(ExtendedBadgeRepository badgeRepository, BadgeMapper badgeMapper) {
        super(badgeRepository, badgeMapper);
        this.badgeRepository = badgeRepository;
        this.badgeMapper = badgeMapper;
    }

    @Override
    public Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable) {
        log.debug("Request to get Badges by Badge Ids: {}", badgeIds);
        return badgeRepository.findByIdIn(badgeIds, pageable).map(badgeMapper::toDto);
    }
}