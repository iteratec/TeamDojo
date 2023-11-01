package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.PersistentAuditEventData;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersistentAuditEventData entity.
 */
@Repository
@GeneratedByJHipster
public interface PersistentAuditEventDataRepository extends JpaRepository<PersistentAuditEventData, Long> {
    default Optional<PersistentAuditEventData> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PersistentAuditEventData> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PersistentAuditEventData> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select persistentAuditEventData from PersistentAuditEventData persistentAuditEventData left join fetch persistentAuditEventData.event",
        countQuery = "select count(persistentAuditEventData) from PersistentAuditEventData persistentAuditEventData"
    )
    Page<PersistentAuditEventData> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select persistentAuditEventData from PersistentAuditEventData persistentAuditEventData left join fetch persistentAuditEventData.event"
    )
    List<PersistentAuditEventData> findAllWithToOneRelationships();

    @Query(
        "select persistentAuditEventData from PersistentAuditEventData persistentAuditEventData left join fetch persistentAuditEventData.event where persistentAuditEventData.id =:id"
    )
    Optional<PersistentAuditEventData> findOneWithToOneRelationships(@Param("id") Long id);
}
