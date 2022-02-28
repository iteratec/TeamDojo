package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.BadgeSkill;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BadgeSkill entity.
 */
@Repository
public interface BadgeSkillRepository extends JpaRepository<BadgeSkill, Long>, JpaSpecificationExecutor<BadgeSkill> {
    default Optional<BadgeSkill> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BadgeSkill> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BadgeSkill> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct badgeSkill from BadgeSkill badgeSkill left join fetch badgeSkill.badge left join fetch badgeSkill.skill",
        countQuery = "select count(distinct badgeSkill) from BadgeSkill badgeSkill"
    )
    Page<BadgeSkill> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct badgeSkill from BadgeSkill badgeSkill left join fetch badgeSkill.badge left join fetch badgeSkill.skill")
    List<BadgeSkill> findAllWithToOneRelationships();

    @Query(
        "select badgeSkill from BadgeSkill badgeSkill left join fetch badgeSkill.badge left join fetch badgeSkill.skill where badgeSkill.id =:id"
    )
    Optional<BadgeSkill> findOneWithToOneRelationships(@Param("id") Long id);
}
