package com.iteratec.teamdojo.repository.ext;

import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.repository.BadgeRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Badge entity.
 */
@Repository
public interface ExtendedBadgeRepository extends BadgeRepository {
    List<Badge> findAllByDimensionsIsNull();

    Page<Badge> findByIdIn(List<Long> badgeIds, Pageable pageable);
}
