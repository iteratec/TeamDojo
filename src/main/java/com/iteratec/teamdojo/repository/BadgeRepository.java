package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
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
@GeneratedByJHipster
public interface BadgeRepository extends BadgeRepositoryWithBagRelationships, JpaRepository<Badge, Long>, JpaSpecificationExecutor<Badge> {
    default Optional<Badge> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Badge> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Badge> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct badge from Badge badge left join fetch badge.image",
        countQuery = "select count(distinct badge) from Badge badge"
    )
    Page<Badge> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct badge from Badge badge left join fetch badge.image")
    List<Badge> findAllWithToOneRelationships();

    @Query("select badge from Badge badge left join fetch badge.image where badge.id =:id")
    Optional<Badge> findOneWithToOneRelationships(@Param("id") Long id);
}
