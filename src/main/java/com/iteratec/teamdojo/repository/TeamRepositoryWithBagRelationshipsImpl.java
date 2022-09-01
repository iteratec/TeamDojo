package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Team;
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
public class TeamRepositoryWithBagRelationshipsImpl implements TeamRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Team> fetchBagRelationships(Optional<Team> team) {
        return team.map(this::fetchParticipations);
    }

    @Override
    public Page<Team> fetchBagRelationships(Page<Team> teams) {
        return new PageImpl<>(fetchBagRelationships(teams.getContent()), teams.getPageable(), teams.getTotalElements());
    }

    @Override
    public List<Team> fetchBagRelationships(List<Team> teams) {
        return Optional.of(teams).map(this::fetchParticipations).orElse(Collections.emptyList());
    }

    Team fetchParticipations(Team result) {
        return entityManager
            .createQuery("select team from Team team left join fetch team.participations where team is :team", Team.class)
            .setParameter("team", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Team> fetchParticipations(List<Team> teams) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, teams.size()).forEach(index -> order.put(teams.get(index).getId(), index));
        List<Team> result = entityManager
            .createQuery("select distinct team from Team team left join fetch team.participations where team in :teams", Team.class)
            .setParameter("teams", teams)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
