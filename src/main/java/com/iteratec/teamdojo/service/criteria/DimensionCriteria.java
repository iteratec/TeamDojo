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
 * Criteria class for the {@link com.iteratec.teamdojo.domain.Dimension} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.DimensionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dimensions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@GeneratedByJHipster
public class DimensionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter levelsId;

    private LongFilter badgesId;

    private LongFilter participantsId;

    private Boolean distinct;

    public DimensionCriteria() {}

    public DimensionCriteria(DimensionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
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
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
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
        return Objects.hash(id, title, description, createdAt, updatedAt, levelsId, badgesId, participantsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DimensionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (levelsId != null ? "levelsId=" + levelsId + ", " : "") +
            (badgesId != null ? "badgesId=" + badgesId + ", " : "") +
            (participantsId != null ? "participantsId=" + participantsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
