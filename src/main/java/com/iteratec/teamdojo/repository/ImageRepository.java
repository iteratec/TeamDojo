package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Image;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface ImageRepository extends JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {}
