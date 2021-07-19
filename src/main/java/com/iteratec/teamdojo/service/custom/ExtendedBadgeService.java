package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.BadgeService;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExtendedBadgeService extends BadgeService {
    Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable);
}
