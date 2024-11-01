/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.*;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.SkillQueryService;
import com.iteratec.teamdojo.service.criteria.SkillCriteria;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import javax.persistence.criteria.JoinType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Primary
@Service
@Transactional(readOnly = true)
public class ExtendedSkillQueryServiceImpl extends SkillQueryService {

    public ExtendedSkillQueryServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        super(skillRepository, skillMapper);
    }

    /**
     * Function to convert {@link SkillCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    @Override
    protected Specification<Skill> createSpecification(SkillCriteria criteria) {
        Specification<Skill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Skill_.id));
            }
            if (criteria.getTitleEN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitleEN(), Skill_.titleEN));
            }
            if (criteria.getDescriptionEN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionEN(), Skill_.descriptionEN));
            }
            if (criteria.getImplementationEN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImplementationEN(), Skill_.implementationEN));
            }
            if (criteria.getValidationEN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidationEN(), Skill_.validationEN));
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
