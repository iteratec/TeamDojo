package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.Badge;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data SQL repository for the Badge entity.
 */
public interface BadgeExtendedRepository extends BadgeRepository {
    List<Badge> findAllByDimensionsIsNull();

    Page<Badge> findByIdIn(List<Long> badgeIds, Pageable pageable);
}
