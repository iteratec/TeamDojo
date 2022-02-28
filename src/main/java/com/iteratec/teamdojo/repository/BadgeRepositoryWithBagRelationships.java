package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.Badge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface BadgeRepositoryWithBagRelationships {
    Optional<Badge> fetchBagRelationships(Optional<Badge> badge);

    List<Badge> fetchBagRelationships(List<Badge> badges);

    Page<Badge> fetchBagRelationships(Page<Badge> badges);
}
