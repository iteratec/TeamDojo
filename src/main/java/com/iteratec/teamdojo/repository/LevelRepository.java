package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.Level;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Level entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelRepository extends JpaRepository<Level, Long>, JpaSpecificationExecutor<Level> {}
