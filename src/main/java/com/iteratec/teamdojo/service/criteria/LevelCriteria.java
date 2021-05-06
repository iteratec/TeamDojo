package com.iteratec.teamdojo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.iteratec.teamdojo.domain.Level} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.LevelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /levels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LevelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private DoubleFilter requiredScore;

    private DoubleFilter instantMultiplier;

    private IntegerFilter completionBonus;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter dependsOnId;

    private LongFilter skillsId;

    private LongFilter imageId;

    private LongFilter dimensionId;

    public LevelCriteria() {}

    public LevelCriteria(LevelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.requiredScore = other.requiredScore == null ? null : other.requiredScore.copy();
        this.instantMultiplier = other.instantMultiplier == null ? null : other.instantMultiplier.copy();
        this.completionBonus = other.completionBonus == null ? null : other.completionBonus.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.dependsOnId = other.dependsOnId == null ? null : other.dependsOnId.copy();
        this.skillsId = other.skillsId == null ? null : other.skillsId.copy();
        this.imageId = other.imageId == null ? null : other.imageId.copy();
        this.dimensionId = other.dimensionId == null ? null : other.dimensionId.copy();
    }

    @Override
    public LevelCriteria copy() {
        return new LevelCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public DoubleFilter getRequiredScore() {
        return requiredScore;
    }

    public DoubleFilter requiredScore() {
        if (requiredScore == null) {
            requiredScore = new DoubleFilter();
        }
        return requiredScore;
    }

    public void setRequiredScore(DoubleFilter requiredScore) {
        this.requiredScore = requiredScore;
    }

    public DoubleFilter getInstantMultiplier() {
        return instantMultiplier;
    }

    public DoubleFilter instantMultiplier() {
        if (instantMultiplier == null) {
            instantMultiplier = new DoubleFilter();
        }
        return instantMultiplier;
    }

    public void setInstantMultiplier(DoubleFilter instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
    }

    public IntegerFilter getCompletionBonus() {
        return completionBonus;
    }

    public IntegerFilter completionBonus() {
        if (completionBonus == null) {
            completionBonus = new IntegerFilter();
        }
        return completionBonus;
    }

    public void setCompletionBonus(IntegerFilter completionBonus) {
        this.completionBonus = completionBonus;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            createdAt = new InstantFilter();
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public InstantFilter updatedAt() {
        if (updatedAt == null) {
            updatedAt = new InstantFilter();
        }
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LongFilter getDependsOnId() {
        return dependsOnId;
    }

    public LongFilter dependsOnId() {
        if (dependsOnId == null) {
            dependsOnId = new LongFilter();
        }
        return dependsOnId;
    }

    public void setDependsOnId(LongFilter dependsOnId) {
        this.dependsOnId = dependsOnId;
    }

    public LongFilter getSkillsId() {
        return skillsId;
    }

    public LongFilter skillsId() {
        if (skillsId == null) {
            skillsId = new LongFilter();
        }
        return skillsId;
    }

    public void setSkillsId(LongFilter skillsId) {
        this.skillsId = skillsId;
    }

    public LongFilter getImageId() {
        return imageId;
    }

    public LongFilter imageId() {
        if (imageId == null) {
            imageId = new LongFilter();
        }
        return imageId;
    }

    public void setImageId(LongFilter imageId) {
        this.imageId = imageId;
    }

    public LongFilter getDimensionId() {
        return dimensionId;
    }

    public LongFilter dimensionId() {
        if (dimensionId == null) {
            dimensionId = new LongFilter();
        }
        return dimensionId;
    }

    public void setDimensionId(LongFilter dimensionId) {
        this.dimensionId = dimensionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LevelCriteria that = (LevelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(requiredScore, that.requiredScore) &&
            Objects.equals(instantMultiplier, that.instantMultiplier) &&
            Objects.equals(completionBonus, that.completionBonus) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(dependsOnId, that.dependsOnId) &&
            Objects.equals(skillsId, that.skillsId) &&
            Objects.equals(imageId, that.imageId) &&
            Objects.equals(dimensionId, that.dimensionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            description,
            requiredScore,
            instantMultiplier,
            completionBonus,
            createdAt,
            updatedAt,
            dependsOnId,
            skillsId,
            imageId,
            dimensionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (requiredScore != null ? "requiredScore=" + requiredScore + ", " : "") +
            (instantMultiplier != null ? "instantMultiplier=" + instantMultiplier + ", " : "") +
            (completionBonus != null ? "completionBonus=" + completionBonus + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (dependsOnId != null ? "dependsOnId=" + dependsOnId + ", " : "") +
            (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            (imageId != null ? "imageId=" + imageId + ", " : "") +
            (dimensionId != null ? "dimensionId=" + dimensionId + ", " : "") +
            "}";
    }
}