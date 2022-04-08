package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.LevelSkill;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LevelSkill entity.
 */
@Repository
@GeneratedByJHipster
public interface LevelSkillRepository extends JpaRepository<LevelSkill, Long>, JpaSpecificationExecutor<LevelSkill> {
    default Optional<LevelSkill> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LevelSkill> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LevelSkill> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct levelSkill from LevelSkill levelSkill left join fetch levelSkill.skill left join fetch levelSkill.level",
        countQuery = "select count(distinct levelSkill) from LevelSkill levelSkill"
    )
    Page<LevelSkill> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct levelSkill from LevelSkill levelSkill left join fetch levelSkill.skill left join fetch levelSkill.level")
    List<LevelSkill> findAllWithToOneRelationships();

    @Query(
        "select levelSkill from LevelSkill levelSkill left join fetch levelSkill.skill left join fetch levelSkill.level where levelSkill.id =:id"
    )
    Optional<LevelSkill> findOneWithToOneRelationships(@Param("id") Long id);
}
