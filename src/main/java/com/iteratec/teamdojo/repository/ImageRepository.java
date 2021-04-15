package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.Image;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {}
