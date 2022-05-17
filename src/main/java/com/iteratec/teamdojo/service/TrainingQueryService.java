package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.*; // for static metamodels
import com.iteratec.teamdojo.domain.Training;
import com.iteratec.teamdojo.repository.TrainingRepository;
import com.iteratec.teamdojo.service.criteria.TrainingCriteria;
import com.iteratec.teamdojo.service.dto.TrainingDTO;
import com.iteratec.teamdojo.service.mapper.TrainingMapper;
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
 * Service for executing complex queries for {@link Training} entities in the database.
 * The main input is a {@link TrainingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrainingDTO} or a {@link Page} of {@link TrainingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
@GeneratedByJHipster
public class TrainingQueryService extends QueryService<Training> {

    private final Logger log = LoggerFactory.getLogger(TrainingQueryService.class);

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    public TrainingQueryService(TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
    }

    /**
     * Return a {@link List} of {@link TrainingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrainingDTO> findByCriteria(TrainingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingMapper.toDto(trainingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrainingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingDTO> findByCriteria(TrainingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.findAll(specification, page).map(trainingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.count(specification);
    }

    /**
     * Function to convert {@link TrainingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Training> createSpecification(TrainingCriteria criteria) {
        Specification<Training> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Training_.id));
            }
            if (criteria.getTitleEN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitleEN(), Training_.titleEN));
            }
            if (criteria.getDescriptionEN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionEN(), Training_.descriptionEN));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), Training_.contact));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), Training_.link));
            }
            if (criteria.getValidUntil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidUntil(), Training_.validUntil));
            }
            if (criteria.getIsOfficial() != null) {
                specification = specification.and(buildSpecification(criteria.getIsOfficial(), Training_.isOfficial));
            }
            if (criteria.getSuggestedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuggestedBy(), Training_.suggestedBy));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Training_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Training_.updatedAt));
            }
            if (criteria.getSkillId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSkillId(), root -> root.join(Training_.skills, JoinType.LEFT).get(Skill_.id))
                    );
            }
        }
        return specification;
    }
}
