package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.domain.*; // for static metamodels
import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.repository.LevelSkillRepository;
import com.iteratec.teamdojo.service.criteria.LevelSkillCriteria;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.mapper.LevelSkillMapper;
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
 * Service for executing complex queries for {@link LevelSkill} entities in the database.
 * The main input is a {@link LevelSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LevelSkillDTO} or a {@link Page} of {@link LevelSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LevelSkillQueryService extends QueryService<LevelSkill> {

    private final Logger log = LoggerFactory.getLogger(LevelSkillQueryService.class);

    private final LevelSkillRepository levelSkillRepository;

    private final LevelSkillMapper levelSkillMapper;

    public LevelSkillQueryService(LevelSkillRepository levelSkillRepository, LevelSkillMapper levelSkillMapper) {
        this.levelSkillRepository = levelSkillRepository;
        this.levelSkillMapper = levelSkillMapper;
    }

    /**
     * Return a {@link List} of {@link LevelSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LevelSkillDTO> findByCriteria(LevelSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LevelSkill> specification = createSpecification(criteria);
        return levelSkillMapper.toDto(levelSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LevelSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LevelSkillDTO> findByCriteria(LevelSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LevelSkill> specification = createSpecification(criteria);
        return levelSkillRepository.findAll(specification, page).map(levelSkillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LevelSkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LevelSkill> specification = createSpecification(criteria);
        return levelSkillRepository.count(specification);
    }

    /**
     * Function to convert {@link LevelSkillCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LevelSkill> createSpecification(LevelSkillCriteria criteria) {
        Specification<LevelSkill> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LevelSkill_.id));
            }
            if (criteria.getSkillId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSkillId(), root -> root.join(LevelSkill_.skill, JoinType.LEFT).get(Skill_.id))
                    );
            }
            if (criteria.getLevelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLevelId(), root -> root.join(LevelSkill_.level, JoinType.LEFT).get(Level_.id))
                    );
            }
        }
        return specification;
    }
}
