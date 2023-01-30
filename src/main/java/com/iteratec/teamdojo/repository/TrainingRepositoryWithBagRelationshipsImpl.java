package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Training;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
@GeneratedByJHipster
public class TrainingRepositoryWithBagRelationshipsImpl implements TrainingRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Training> fetchBagRelationships(Optional<Training> training) {
        return training.map(this::fetchSkills);
    }

    @Override
    public Page<Training> fetchBagRelationships(Page<Training> trainings) {
        return new PageImpl<>(fetchBagRelationships(trainings.getContent()), trainings.getPageable(), trainings.getTotalElements());
    }

    @Override
    public List<Training> fetchBagRelationships(List<Training> trainings) {
        return Optional.of(trainings).map(this::fetchSkills).orElse(Collections.emptyList());
    }

    Training fetchSkills(Training result) {
        return entityManager
            .createQuery(
                "select training from Training training left join fetch training.skills where training is :training",
                Training.class
            )
            .setParameter("training", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Training> fetchSkills(List<Training> trainings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, trainings.size()).forEach(index -> order.put(trainings.get(index).getId(), index));
        List<Training> result = entityManager
            .createQuery(
                "select distinct training from Training training left join fetch training.skills where training in :trainings",
                Training.class
            )
            .setParameter("trainings", trainings)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
