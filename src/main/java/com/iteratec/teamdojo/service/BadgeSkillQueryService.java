package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.domain.*; // for static metamodels
import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.repository.BadgeSkillRepository;
import com.iteratec.teamdojo.service.criteria.BadgeSkillCriteria;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.mapper.BadgeSkillMapper;
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
 * Service for executing complex queries for {@link BadgeSkill} entities in the database.
 * The main input is a {@link BadgeSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BadgeSkillDTO} or a {@link Page} of {@link BadgeSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BadgeSkillQueryService extends QueryService<BadgeSkill> {

    private final Logger log = LoggerFactory.getLogger(BadgeSkillQueryService.class);

    private final BadgeSkillRepository badgeSkillRepository;

    private final BadgeSkillMapper badgeSkillMapper;

    public BadgeSkillQueryService(BadgeSkillRepository badgeSkillRepository, BadgeSkillMapper badgeSkillMapper) {
        this.badgeSkillRepository = badgeSkillRepository;
        this.badgeSkillMapper = badgeSkillMapper;
    }

    /**
     * Return a {@link List} of {@link BadgeSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BadgeSkillDTO> findByCriteria(BadgeSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BadgeSkill> specification = createSpecification(criteria);
        return badgeSkillMapper.toDto(badgeSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BadgeSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BadgeSkillDTO> findByCriteria(BadgeSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BadgeSkill> specification = createSpecification(criteria);
        return badgeSkillRepository.findAll(specification, page).map(badgeSkillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BadgeSkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BadgeSkill> specification = createSpecification(criteria);
        return badgeSkillRepository.count(specification);
    }

    /**
     * Function to convert {@link BadgeSkillCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BadgeSkill> createSpecification(BadgeSkillCriteria criteria) {
        Specification<BadgeSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BadgeSkill_.id));
            }
            if (criteria.getBadgeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getBadgeId(), root -> root.join(BadgeSkill_.badge, JoinType.LEFT).get(Badge_.id))
                    );
            }
            if (criteria.getSkillId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSkillId(), root -> root.join(BadgeSkill_.skill, JoinType.LEFT).get(Skill_.id))
                    );
            }
        }
        return specification;
    }
}
