package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.*; // for static metamodels
import com.iteratec.teamdojo.domain.TeamSkill;
import com.iteratec.teamdojo.repository.TeamSkillRepository;
import com.iteratec.teamdojo.service.criteria.TeamSkillCriteria;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.mapper.TeamSkillMapper;
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
 * Service for executing complex queries for {@link TeamSkill} entities in the database.
 * The main input is a {@link TeamSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeamSkillDTO} or a {@link Page} of {@link TeamSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
@GeneratedByJHipster
public class TeamSkillQueryService extends QueryService<TeamSkill> {

    private final Logger log = LoggerFactory.getLogger(TeamSkillQueryService.class);

    private final TeamSkillRepository teamSkillRepository;

    private final TeamSkillMapper teamSkillMapper;

    public TeamSkillQueryService(TeamSkillRepository teamSkillRepository, TeamSkillMapper teamSkillMapper) {
        this.teamSkillRepository = teamSkillRepository;
        this.teamSkillMapper = teamSkillMapper;
    }

    /**
     * Return a {@link List} of {@link TeamSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeamSkillDTO> findByCriteria(TeamSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TeamSkill> specification = createSpecification(criteria);
        return teamSkillMapper.toDto(teamSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TeamSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamSkillDTO> findByCriteria(TeamSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TeamSkill> specification = createSpecification(criteria);
        return teamSkillRepository.findAll(specification, page).map(teamSkillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TeamSkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TeamSkill> specification = createSpecification(criteria);
        return teamSkillRepository.count(specification);
    }

    /**
     * Function to convert {@link TeamSkillCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TeamSkill> createSpecification(TeamSkillCriteria criteria) {
        Specification<TeamSkill> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TeamSkill_.id));
            }
            if (criteria.getCompletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompletedAt(), TeamSkill_.completedAt));
            }
            if (criteria.getVerifiedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVerifiedAt(), TeamSkill_.verifiedAt));
            }
            if (criteria.getIrrelevant() != null) {
                specification = specification.and(buildSpecification(criteria.getIrrelevant(), TeamSkill_.irrelevant));
            }
            if (criteria.getSkillStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillStatus(), TeamSkill_.skillStatus));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), TeamSkill_.note));
            }
            if (criteria.getVote() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVote(), TeamSkill_.vote));
            }
            if (criteria.getVoters() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoters(), TeamSkill_.voters));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), TeamSkill_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), TeamSkill_.updatedAt));
            }
            if (criteria.getSkillId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSkillId(), root -> root.join(TeamSkill_.skill, JoinType.LEFT).get(Skill_.id))
                    );
            }
            if (criteria.getTeamId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTeamId(), root -> root.join(TeamSkill_.team, JoinType.LEFT).get(Team_.id))
                    );
            }
        }
        return specification;
    }
}
