package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.TeamSkill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TeamSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamSkillRepository extends JpaRepository<TeamSkill, Long>, JpaSpecificationExecutor<TeamSkill> {}
