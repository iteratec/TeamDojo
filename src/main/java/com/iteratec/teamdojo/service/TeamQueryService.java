package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.domain.*; // for static metamodels
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.repository.TeamRepository;
import com.iteratec.teamdojo.service.criteria.TeamCriteria;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.mapper.TeamMapper;
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
 * Service for executing complex queries for {@link Team} entities in the database.
 * The main input is a {@link TeamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeamDTO} or a {@link Page} of {@link TeamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeamQueryService extends QueryService<Team> {

    private final Logger log = LoggerFactory.getLogger(TeamQueryService.class);

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    public TeamQueryService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    /**
     * Return a {@link List} of {@link TeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeamDTO> findByCriteria(TeamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Team> specification = createSpecification(criteria);
        return teamMapper.toDto(teamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamDTO> findByCriteria(TeamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Team> specification = createSpecification(criteria);
        return teamRepository.findAll(specification, page).map(teamMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TeamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Team> specification = createSpecification(criteria);
        return teamRepository.count(specification);
    }

    /**
     * Function to convert {@link TeamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Team> createSpecification(TeamCriteria criteria) {
        Specification<Team> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Team_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Team_.title));
            }
            if (criteria.getShortTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortTitle(), Team_.shortTitle));
            }
            if (criteria.getSlogan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlogan(), Team_.slogan));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), Team_.contact));
            }
            if (criteria.getValidUntil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidUntil(), Team_.validUntil));
            }
            if (criteria.getPureTrainingTeam() != null) {
                specification = specification.and(buildSpecification(criteria.getPureTrainingTeam(), Team_.pureTrainingTeam));
            }
            if (criteria.getOfficial() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficial(), Team_.official));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Team_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Team_.updatedAt));
            }
            if (criteria.getDaysUntilExpiration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDaysUntilExpiration(), Team_.daysUntilExpiration));
            }
            if (criteria.getExpired() != null) {
                specification = specification.and(buildSpecification(criteria.getExpired(), Team_.expired));
            }
            if (criteria.getSkillsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSkillsId(), root -> root.join(Team_.skills, JoinType.LEFT).get(TeamSkill_.id))
                    );
            }
            if (criteria.getImageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getImageId(), root -> root.join(Team_.image, JoinType.LEFT).get(Image_.id))
                    );
            }
            if (criteria.getParticipationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParticipationsId(),
                            root -> root.join(Team_.participations, JoinType.LEFT).get(Dimension_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
