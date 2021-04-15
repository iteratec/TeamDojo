package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.Activity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Activity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {}
