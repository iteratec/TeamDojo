package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.Training;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Training entity.
 */
@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    @Query(
        value = "select distinct training from Training training left join fetch training.skills",
        countQuery = "select count(distinct training) from Training training"
    )
    Page<Training> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct training from Training training left join fetch training.skills")
    List<Training> findAllWithEagerRelationships();

    @Query("select training from Training training left join fetch training.skills where training.id =:id")
    Optional<Training> findOneWithEagerRelationships(@Param("id") Long id);
}
