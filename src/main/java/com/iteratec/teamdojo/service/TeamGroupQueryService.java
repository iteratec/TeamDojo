package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.*; // for static metamodels
import com.iteratec.teamdojo.domain.TeamGroup;
import com.iteratec.teamdojo.repository.TeamGroupRepository;
import com.iteratec.teamdojo.service.criteria.TeamGroupCriteria;
import com.iteratec.teamdojo.service.dto.TeamGroupDTO;
import com.iteratec.teamdojo.service.mapper.TeamGroupMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TeamGroup} entities in the database.
 * The main input is a {@link TeamGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeamGroupDTO} or a {@link Page} of {@link TeamGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
@GeneratedByJHipster
public class TeamGroupQueryService extends QueryService<TeamGroup> {

    private final Logger log = LoggerFactory.getLogger(TeamGroupQueryService.class);

    private final TeamGroupRepository teamGroupRepository;

    private final TeamGroupMapper teamGroupMapper;

    public TeamGroupQueryService(TeamGroupRepository teamGroupRepository, TeamGroupMapper teamGroupMapper) {
        this.teamGroupRepository = teamGroupRepository;
        this.teamGroupMapper = teamGroupMapper;
    }

    /**
     * Return a {@link List} of {@link TeamGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeamGroupDTO> findByCriteria(TeamGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TeamGroup> specification = createSpecification(criteria);
        return teamGroupMapper.toDto(teamGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TeamGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamGroupDTO> findByCriteria(TeamGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TeamGroup> specification = createSpecification(criteria);
        return teamGroupRepository.findAll(specification, page).map(teamGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TeamGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TeamGroup> specification = createSpecification(criteria);
        return teamGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link TeamGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TeamGroup> createSpecification(TeamGroupCriteria criteria) {
        Specification<TeamGroup> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TeamGroup_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), TeamGroup_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TeamGroup_.description));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), TeamGroup_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), TeamGroup_.updatedAt));
            }
            if (criteria.getTeamsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTeamsId(), root -> root.join(TeamGroup_.teams, JoinType.LEFT).get(Team_.id))
                    );
            }
            if (criteria.getParentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getParentId(), root -> root.join(TeamGroup_.parent, JoinType.LEFT).get(TeamGroup_.id))
                    );
            }
        }
        return specification;
    }
}
