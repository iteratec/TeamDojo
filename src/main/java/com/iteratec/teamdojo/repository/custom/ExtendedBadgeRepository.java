package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.repository.BadgeRepository;
import java.util.List;

import com.iteratec.teamdojo.repository.BadgeRepositoryWithBagRelationships;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Custom extension for {@link BadgeRepository}
 *
 * <p>We use separate interfaces extending the generated one to avoid modification of generated code (see ADR-0001).</p>
 */
@Repository
public interface ExtendedBadgeRepository extends BadgeRepository, BadgeRepositoryWithBagRelationships {
    List<Badge> findAllByDimensionsIsNull();

    Page<Badge> findByIdIn(List<Long> badgeIds, Pageable pageable);
}
