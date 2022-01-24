package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.repository.custom.ExtendedBadgeRepository;
import com.iteratec.teamdojo.service.custom.ExtendedBadgeService;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.impl.BadgeServiceImpl;
import com.iteratec.teamdojo.service.mapper.BadgeMapper;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class ExtendedBadgeServiceImpl extends BadgeServiceImpl implements ExtendedBadgeService {

    private final ExtendedBadgeRepository repo;
    private final BadgeMapper mapper;
    private final AuditableDataTracker<BadgeDTO, Badge> tracker;

    public ExtendedBadgeServiceImpl(ExtendedBadgeRepository repo, BadgeMapper mapper) {
        super(repo, mapper);
        this.repo = repo;
        this.mapper = mapper;
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable) {
        log.debug("Request to get Badges by Badge Ids: {}", badgeIds);
        return repo.findByIdIn(badgeIds, pageable).map(mapper::toDto);
    }

    @Override
    public BadgeDTO save(final BadgeDTO badge) {
        tracker.modifyCreatedAndUpdatedAt(badge);
        return super.save(badge);
    }
}
