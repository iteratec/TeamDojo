package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Report;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Report entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {}
