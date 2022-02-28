package com.iteratec.teamdojo.service.criteria;

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
 * Criteria class for the {@link com.iteratec.teamdojo.domain.LevelSkill} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.LevelSkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /level-skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class LevelSkillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter skillId;

    private LongFilter levelId;

    private Boolean distinct;

    public LevelSkillCriteria() {}

    public LevelSkillCriteria(LevelSkillCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.skillId = other.skillId == null ? null : other.skillId.copy();
        this.levelId = other.levelId == null ? null : other.levelId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LevelSkillCriteria copy() {
        return new LevelSkillCriteria(this);
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

    public LongFilter getLevelId() {
        return levelId;
    }

    public LongFilter levelId() {
        if (levelId == null) {
            levelId = new LongFilter();
        }
        return levelId;
    }

    public void setLevelId(LongFilter levelId) {
        this.levelId = levelId;
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
        final LevelSkillCriteria that = (LevelSkillCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(skillId, that.skillId) &&
            Objects.equals(levelId, that.levelId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, skillId, levelId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelSkillCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (skillId != null ? "skillId=" + skillId + ", " : "") +
            (levelId != null ? "levelId=" + levelId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
