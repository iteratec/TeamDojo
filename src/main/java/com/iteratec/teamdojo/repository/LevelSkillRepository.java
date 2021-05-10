package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.LevelSkill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LevelSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelSkillRepository extends JpaRepository<LevelSkill, Long>, JpaSpecificationExecutor<LevelSkill> {}
