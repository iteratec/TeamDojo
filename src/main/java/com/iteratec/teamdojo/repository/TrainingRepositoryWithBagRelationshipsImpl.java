package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Training;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
@GeneratedByJHipster
public class TrainingRepositoryWithBagRelationshipsImpl implements TrainingRepositoryWithBagRelationships {

    @Autowired
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
        return Optional.of(trainings).map(this::fetchSkills).get();
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
        return entityManager
            .createQuery(
                "select distinct training from Training training left join fetch training.skills where training in :trainings",
                Training.class
            )
            .setParameter("trainings", trainings)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
