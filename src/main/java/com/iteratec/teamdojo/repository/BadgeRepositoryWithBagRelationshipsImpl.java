package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Badge;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
@GeneratedByJHipster
public class BadgeRepositoryWithBagRelationshipsImpl implements BadgeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Badge> fetchBagRelationships(Optional<Badge> badge) {
        return badge.map(this::fetchDimensions);
    }

    @Override
    public Page<Badge> fetchBagRelationships(Page<Badge> badges) {
        return new PageImpl<>(fetchBagRelationships(badges.getContent()), badges.getPageable(), badges.getTotalElements());
    }

    @Override
    public List<Badge> fetchBagRelationships(List<Badge> badges) {
        return Optional.of(badges).map(this::fetchDimensions).orElse(Collections.emptyList());
    }

    Badge fetchDimensions(Badge result) {
        return entityManager
            .createQuery("select badge from Badge badge left join fetch badge.dimensions where badge is :badge", Badge.class)
            .setParameter("badge", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Badge> fetchDimensions(List<Badge> badges) {
        return entityManager
            .createQuery("select distinct badge from Badge badge left join fetch badge.dimensions where badge in :badges", Badge.class)
            .setParameter("badges", badges)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
