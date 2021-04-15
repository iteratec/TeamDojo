package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.Badge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Badge entity.
 */
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long>, JpaSpecificationExecutor<Badge> {
    @Query(
        value = "select distinct badge from Badge badge left join fetch badge.dimensions",
        countQuery = "select count(distinct badge) from Badge badge"
    )
    Page<Badge> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct badge from Badge badge left join fetch badge.dimensions")
    List<Badge> findAllWithEagerRelationships();

    @Query("select badge from Badge badge left join fetch badge.dimensions where badge.id =:id")
    Optional<Badge> findOneWithEagerRelationships(@Param("id") Long id);
}
