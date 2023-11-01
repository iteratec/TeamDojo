package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.TeamGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TeamGroup entity.
 */
@Repository
@GeneratedByJHipster
public interface TeamGroupRepository extends JpaRepository<TeamGroup, Long>, JpaSpecificationExecutor<TeamGroup> {
    default Optional<TeamGroup> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TeamGroup> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TeamGroup> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select teamGroup from TeamGroup teamGroup left join fetch teamGroup.parent",
        countQuery = "select count(teamGroup) from TeamGroup teamGroup"
    )
    Page<TeamGroup> findAllWithToOneRelationships(Pageable pageable);

    @Query("select teamGroup from TeamGroup teamGroup left join fetch teamGroup.parent")
    List<TeamGroup> findAllWithToOneRelationships();

    @Query("select teamGroup from TeamGroup teamGroup left join fetch teamGroup.parent where teamGroup.id =:id")
    Optional<TeamGroup> findOneWithToOneRelationships(@Param("id") Long id);
}
