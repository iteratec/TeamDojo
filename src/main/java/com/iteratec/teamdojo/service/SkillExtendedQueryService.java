package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.domain.*; // for static metamodels
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.criteria.SkillCriteria;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
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
 * Service for executing complex queries for {@link Skill} entities in the database.
 * The main input is a {@link SkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillDTO} or a {@link Page} of {@link SkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillExtendedQueryService extends QueryService<Skill> {

    private final Logger log = LoggerFactory.getLogger(SkillQueryService.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillQueryService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Return a {@link List} of {@link SkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillDTO> findByCriteria(SkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillMapper.toDto(skillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillDTO> findByCriteria(SkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillRepository.findAll(specification, page).map(skillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillRepository.count(specification);
    }

    /**
     * Function to convert {@link SkillCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Skill> createSpecification(SkillCriteria criteria) {
        Specification<Skill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Skill_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Skill_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Skill_.description));
            }
            if (criteria.getImplementation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImplementation(), Skill_.implementation));
            }
            if (criteria.getValidation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidation(), Skill_.validation));
            }
            if (criteria.getExpiryPeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryPeriod(), Skill_.expiryPeriod));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), Skill_.contact));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), Skill_.score));
            }
            if (criteria.getRateScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRateScore(), Skill_.rateScore));
            }
            if (criteria.getRateCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRateCount(), Skill_.rateCount));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Skill_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Skill_.updatedAt));
            }
            if (criteria.getBadgesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getBadgesId(), root -> root.join(Skill_.badges, JoinType.LEFT).get(BadgeSkill_.id))
                    );
            }
            if (criteria.getLevelsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLevelsId(), root -> root.join(Skill_.levels, JoinType.LEFT).get(LevelSkill_.id))
                    );
            }
            if (criteria.getTeamsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTeamsId(), root -> root.join(Skill_.teams, JoinType.LEFT).get(TeamSkill_.id))
                    );
            }
            if (criteria.getTrainingsId() != null) {
                specification =
                    specification.and(buildReferringEntitySpecification(criteria.getTrainingsId(), Skill_.trainings, Training_.id));
            }
        }
        return specification;
    }
}
