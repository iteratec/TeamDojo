package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.Badge;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Badge entity.
 */
public interface ExtendedBadgeRepository extends BadgeRepository {
    List<Badge> findAllByDimensionsIsNull();

    Page<Badge> findByIdIn(List<Long> badgeIds, Pageable pageable);
}
