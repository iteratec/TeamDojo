package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.BadgeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Badge}.
 */
public interface BadgeExtendedService extends BadgeService {
    BadgeDTO save(BadgeDTO badgeDTO);

    Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable);
}
