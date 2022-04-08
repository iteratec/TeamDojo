package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Dimension;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Dimension entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface DimensionRepository extends JpaRepository<Dimension, Long>, JpaSpecificationExecutor<Dimension> {}
