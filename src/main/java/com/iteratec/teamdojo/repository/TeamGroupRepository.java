package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.TeamGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TeamGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamGroupRepository extends JpaRepository<TeamGroup, Long>, JpaSpecificationExecutor<TeamGroup> {}
