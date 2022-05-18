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
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.iteratec.teamdojo.domain.Badge} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.BadgeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /badges?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@GeneratedByJHipster
public class BadgeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titleEN;

    private StringFilter titleDE;

    private StringFilter descriptionEN;

    private StringFilter descriptionDE;

    private InstantFilter availableUntil;

    private IntegerFilter availableAmount;

    private DoubleFilter requiredScore;

    private DoubleFilter instantMultiplier;

    private IntegerFilter completionBonus;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter skillsId;

    private LongFilter imageId;

    private LongFilter dimensionsId;

    private Boolean distinct;

    public BadgeCriteria() {}

    public BadgeCriteria(BadgeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.titleEN = other.titleEN == null ? null : other.titleEN.copy();
        this.titleDE = other.titleDE == null ? null : other.titleDE.copy();
        this.descriptionEN = other.descriptionEN == null ? null : other.descriptionEN.copy();
        this.descriptionDE = other.descriptionDE == null ? null : other.descriptionDE.copy();
        this.availableUntil = other.availableUntil == null ? null : other.availableUntil.copy();
        this.availableAmount = other.availableAmount == null ? null : other.availableAmount.copy();
        this.requiredScore = other.requiredScore == null ? null : other.requiredScore.copy();
        this.instantMultiplier = other.instantMultiplier == null ? null : other.instantMultiplier.copy();
        this.completionBonus = other.completionBonus == null ? null : other.completionBonus.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.skillsId = other.skillsId == null ? null : other.skillsId.copy();
        this.imageId = other.imageId == null ? null : other.imageId.copy();
        this.dimensionsId = other.dimensionsId == null ? null : other.dimensionsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BadgeCriteria copy() {
        return new BadgeCriteria(this);
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

    public StringFilter getTitleEN() {
        return titleEN;
    }

    public StringFilter titleEN() {
        if (titleEN == null) {
            titleEN = new StringFilter();
        }
        return titleEN;
    }

    public void setTitleEN(StringFilter titleEN) {
        this.titleEN = titleEN;
    }

    public StringFilter getTitleDE() {
        return titleDE;
    }

    public StringFilter titleDE() {
        if (titleDE == null) {
            titleDE = new StringFilter();
        }
        return titleDE;
    }

    public void setTitleDE(StringFilter titleDE) {
        this.titleDE = titleDE;
    }

    public StringFilter getDescriptionEN() {
        return descriptionEN;
    }

    public StringFilter descriptionEN() {
        if (descriptionEN == null) {
            descriptionEN = new StringFilter();
        }
        return descriptionEN;
    }

    public void setDescriptionEN(StringFilter descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public StringFilter getDescriptionDE() {
        return descriptionDE;
    }

    public StringFilter descriptionDE() {
        if (descriptionDE == null) {
            descriptionDE = new StringFilter();
        }
        return descriptionDE;
    }

    public void setDescriptionDE(StringFilter descriptionDE) {
        this.descriptionDE = descriptionDE;
    }

    public InstantFilter getAvailableUntil() {
        return availableUntil;
    }

    public InstantFilter availableUntil() {
        if (availableUntil == null) {
            availableUntil = new InstantFilter();
        }
        return availableUntil;
    }

    public void setAvailableUntil(InstantFilter availableUntil) {
        this.availableUntil = availableUntil;
    }

    public IntegerFilter getAvailableAmount() {
        return availableAmount;
    }

    public IntegerFilter availableAmount() {
        if (availableAmount == null) {
            availableAmount = new IntegerFilter();
        }
        return availableAmount;
    }

    public void setAvailableAmount(IntegerFilter availableAmount) {
        this.availableAmount = availableAmount;
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

    public LongFilter getDimensionsId() {
        return dimensionsId;
    }

    public LongFilter dimensionsId() {
        if (dimensionsId == null) {
            dimensionsId = new LongFilter();
        }
        return dimensionsId;
    }

    public void setDimensionsId(LongFilter dimensionsId) {
        this.dimensionsId = dimensionsId;
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
        final BadgeCriteria that = (BadgeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(titleEN, that.titleEN) &&
            Objects.equals(titleDE, that.titleDE) &&
            Objects.equals(descriptionEN, that.descriptionEN) &&
            Objects.equals(descriptionDE, that.descriptionDE) &&
            Objects.equals(availableUntil, that.availableUntil) &&
            Objects.equals(availableAmount, that.availableAmount) &&
            Objects.equals(requiredScore, that.requiredScore) &&
            Objects.equals(instantMultiplier, that.instantMultiplier) &&
            Objects.equals(completionBonus, that.completionBonus) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(skillsId, that.skillsId) &&
            Objects.equals(imageId, that.imageId) &&
            Objects.equals(dimensionsId, that.dimensionsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            titleEN,
            titleDE,
            descriptionEN,
            descriptionDE,
            availableUntil,
            availableAmount,
            requiredScore,
            instantMultiplier,
            completionBonus,
            createdAt,
            updatedAt,
            skillsId,
            imageId,
            dimensionsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (titleEN != null ? "titleEN=" + titleEN + ", " : "") +
            (titleDE != null ? "titleDE=" + titleDE + ", " : "") +
            (descriptionEN != null ? "descriptionEN=" + descriptionEN + ", " : "") +
            (descriptionDE != null ? "descriptionDE=" + descriptionDE + ", " : "") +
            (availableUntil != null ? "availableUntil=" + availableUntil + ", " : "") +
            (availableAmount != null ? "availableAmount=" + availableAmount + ", " : "") +
            (requiredScore != null ? "requiredScore=" + requiredScore + ", " : "") +
            (instantMultiplier != null ? "instantMultiplier=" + instantMultiplier + ", " : "") +
            (completionBonus != null ? "completionBonus=" + completionBonus + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            (imageId != null ? "imageId=" + imageId + ", " : "") +
            (dimensionsId != null ? "dimensionsId=" + dimensionsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
