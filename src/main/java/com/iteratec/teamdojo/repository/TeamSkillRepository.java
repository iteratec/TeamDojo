package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.TeamSkill;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TeamSkill entity.
 */
@Repository
public interface TeamSkillRepository extends JpaRepository<TeamSkill, Long>, JpaSpecificationExecutor<TeamSkill> {
    default Optional<TeamSkill> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TeamSkill> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TeamSkill> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct teamSkill from TeamSkill teamSkill left join fetch teamSkill.skill left join fetch teamSkill.team",
        countQuery = "select count(distinct teamSkill) from TeamSkill teamSkill"
    )
    Page<TeamSkill> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct teamSkill from TeamSkill teamSkill left join fetch teamSkill.skill left join fetch teamSkill.team")
    List<TeamSkill> findAllWithToOneRelationships();

    @Query(
        "select teamSkill from TeamSkill teamSkill left join fetch teamSkill.skill left join fetch teamSkill.team where teamSkill.id =:id"
    )
    Optional<TeamSkill> findOneWithToOneRelationships(@Param("id") Long id);
}
