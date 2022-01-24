package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.BadgeService;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.impl.custom.InstantProviderInjectable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExtendedBadgeService extends BadgeService, InstantProviderInjectable {
    Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable);
}
