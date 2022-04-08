package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Organisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Organisation entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {}
