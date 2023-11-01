package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Badge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
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
            .createQuery("select badge from Badge badge left join fetch badge.dimensions where badge.id = :id", Badge.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Badge> fetchDimensions(List<Badge> badges) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, badges.size()).forEach(index -> order.put(badges.get(index).getId(), index));
        List<Badge> result = entityManager
            .createQuery("select badge from Badge badge left join fetch badge.dimensions where badge in :badges", Badge.class)
            .setParameter("badges", badges)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
