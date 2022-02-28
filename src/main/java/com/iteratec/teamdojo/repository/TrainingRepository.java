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
public interface TrainingRepository
    extends TrainingRepositoryWithBagRelationships, JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    default Optional<Training> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Training> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Training> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
