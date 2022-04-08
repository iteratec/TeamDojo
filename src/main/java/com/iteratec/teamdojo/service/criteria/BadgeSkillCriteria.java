package com.iteratec.teamdojo.service.criteria;

import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.iteratec.teamdojo.domain.BadgeSkill} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.BadgeSkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /badge-skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@GeneratedByJHipster
public class BadgeSkillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter badgeId;

    private LongFilter skillId;

    private Boolean distinct;

    public BadgeSkillCriteria() {}

    public BadgeSkillCriteria(BadgeSkillCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.badgeId = other.badgeId == null ? null : other.badgeId.copy();
        this.skillId = other.skillId == null ? null : other.skillId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BadgeSkillCriteria copy() {
        return new BadgeSkillCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getBadgeId() {
        return badgeId;
    }

    public LongFilter badgeId() {
        if (badgeId == null) {
            badgeId = new LongFilter();
        }
        return badgeId;
    }

    public void setBadgeId(LongFilter badgeId) {
        this.badgeId = badgeId;
    }

    public LongFilter getSkillId() {
        return skillId;
    }

    public LongFilter skillId() {
        if (skillId == null) {
            skillId = new LongFilter();
        }
        return skillId;
    }

    public void setSkillId(LongFilter skillId) {
        this.skillId = skillId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BadgeSkillCriteria that = (BadgeSkillCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(badgeId, that.badgeId) &&
            Objects.equals(skillId, that.skillId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, badgeId, skillId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgeSkillCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (badgeId != null ? "badgeId=" + badgeId + ", " : "") +
            (skillId != null ? "skillId=" + skillId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
