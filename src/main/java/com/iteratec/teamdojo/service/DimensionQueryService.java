package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.*; // for static metamodels
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.repository.DimensionRepository;
import com.iteratec.teamdojo.service.criteria.DimensionCriteria;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import com.iteratec.teamdojo.service.mapper.DimensionMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Dimension} entities in the database.
 * The main input is a {@link DimensionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DimensionDTO} or a {@link Page} of {@link DimensionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
@GeneratedByJHipster
public class DimensionQueryService extends QueryService<Dimension> {

    private final Logger log = LoggerFactory.getLogger(DimensionQueryService.class);

    private final DimensionRepository dimensionRepository;

    private final DimensionMapper dimensionMapper;

    public DimensionQueryService(DimensionRepository dimensionRepository, DimensionMapper dimensionMapper) {
        this.dimensionRepository = dimensionRepository;
        this.dimensionMapper = dimensionMapper;
    }

    /**
     * Return a {@link List} of {@link DimensionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DimensionDTO> findByCriteria(DimensionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dimension> specification = createSpecification(criteria);
        return dimensionMapper.toDto(dimensionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DimensionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DimensionDTO> findByCriteria(DimensionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dimension> specification = createSpecification(criteria);
        return dimensionRepository.findAll(specification, page).map(dimensionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DimensionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dimension> specification = createSpecification(criteria);
        return dimensionRepository.count(specification);
    }

    /**
     * Function to convert {@link DimensionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Dimension> createSpecification(DimensionCriteria criteria) {
        Specification<Dimension> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Dimension_.id));
            }
            if (criteria.getTitleEN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitleEN(), Dimension_.titleEN));
            }
            if (criteria.getTitleDE() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitleDE(), Dimension_.titleDE));
            }
            if (criteria.getDescriptionEN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionEN(), Dimension_.descriptionEN));
            }
            if (criteria.getDescriptionDE() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionDE(), Dimension_.descriptionDE));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Dimension_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Dimension_.updatedAt));
            }
            if (criteria.getLevelsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLevelsId(), root -> root.join(Dimension_.levels, JoinType.LEFT).get(Level_.id))
                    );
            }
            if (criteria.getBadgesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getBadgesId(), root -> root.join(Dimension_.badges, JoinType.LEFT).get(Badge_.id))
                    );
            }
            if (criteria.getParticipantsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParticipantsId(),
                            root -> root.join(Dimension_.participants, JoinType.LEFT).get(Team_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
