package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Training;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

@GeneratedByJHipster
public interface TrainingRepositoryWithBagRelationships {
    Optional<Training> fetchBagRelationships(Optional<Training> training);

    List<Training> fetchBagRelationships(List<Training> trainings);

    Page<Training> fetchBagRelationships(Page<Training> trainings);
}
