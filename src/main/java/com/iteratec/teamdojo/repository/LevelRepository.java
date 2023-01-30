package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Level;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Level entity.
 */
@Repository
@GeneratedByJHipster
public interface LevelRepository extends JpaRepository<Level, Long>, JpaSpecificationExecutor<Level> {
    default Optional<Level> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Level> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Level> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct level from Level level left join fetch level.dependsOn left join fetch level.image left join fetch level.dimension",
        countQuery = "select count(distinct level) from Level level"
    )
    Page<Level> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct level from Level level left join fetch level.dependsOn left join fetch level.image left join fetch level.dimension"
    )
    List<Level> findAllWithToOneRelationships();

    @Query(
        "select level from Level level left join fetch level.dependsOn left join fetch level.image left join fetch level.dimension where level.id =:id"
    )
    Optional<Level> findOneWithToOneRelationships(@Param("id") Long id);
}
