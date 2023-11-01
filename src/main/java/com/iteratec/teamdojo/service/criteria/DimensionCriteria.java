package com.iteratec.teamdojo.service.criteria;

import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.iteratec.teamdojo.domain.Dimension} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.DimensionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dimensions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class DimensionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titleEN;

    private StringFilter titleDE;

    private StringFilter descriptionEN;

    private StringFilter descriptionDE;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter levelsId;

    private LongFilter badgesId;

    private LongFilter participantsId;

    private Boolean distinct;

    public DimensionCriteria() {}

    public DimensionCriteria(DimensionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.titleEN = other.titleEN == null ? null : other.titleEN.copy();
        this.titleDE = other.titleDE == null ? null : other.titleDE.copy();
        this.descriptionEN = other.descriptionEN == null ? null : other.descriptionEN.copy();
        this.descriptionDE = other.descriptionDE == null ? null : other.descriptionDE.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.levelsId = other.levelsId == null ? null : other.levelsId.copy();
        this.badgesId = other.badgesId == null ? null : other.badgesId.copy();
        this.participantsId = other.participantsId == null ? null : other.participantsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DimensionCriteria copy() {
        return new DimensionCriteria(this);
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

    public LongFilter getLevelsId() {
        return levelsId;
    }

    public LongFilter levelsId() {
        if (levelsId == null) {
            levelsId = new LongFilter();
        }
        return levelsId;
    }

    public void setLevelsId(LongFilter levelsId) {
        this.levelsId = levelsId;
    }

    public LongFilter getBadgesId() {
        return badgesId;
    }

    public LongFilter badgesId() {
        if (badgesId == null) {
            badgesId = new LongFilter();
        }
        return badgesId;
    }

    public void setBadgesId(LongFilter badgesId) {
        this.badgesId = badgesId;
    }

    public LongFilter getParticipantsId() {
        return participantsId;
    }

    public LongFilter participantsId() {
        if (participantsId == null) {
            participantsId = new LongFilter();
        }
        return participantsId;
    }

    public void setParticipantsId(LongFilter participantsId) {
        this.participantsId = participantsId;
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
        final DimensionCriteria that = (DimensionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(titleEN, that.titleEN) &&
            Objects.equals(titleDE, that.titleDE) &&
            Objects.equals(descriptionEN, that.descriptionEN) &&
            Objects.equals(descriptionDE, that.descriptionDE) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(levelsId, that.levelsId) &&
            Objects.equals(badgesId, that.badgesId) &&
            Objects.equals(participantsId, that.participantsId) &&
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
            createdAt,
            updatedAt,
            levelsId,
            badgesId,
            participantsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DimensionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (titleEN != null ? "titleEN=" + titleEN + ", " : "") +
            (titleDE != null ? "titleDE=" + titleDE + ", " : "") +
            (descriptionEN != null ? "descriptionEN=" + descriptionEN + ", " : "") +
            (descriptionDE != null ? "descriptionDE=" + descriptionDE + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (levelsId != null ? "levelsId=" + levelsId + ", " : "") +
            (badgesId != null ? "badgesId=" + badgesId + ", " : "") +
            (participantsId != null ? "participantsId=" + participantsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
