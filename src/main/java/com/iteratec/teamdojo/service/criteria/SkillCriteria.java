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
 * Criteria class for the {@link com.iteratec.teamdojo.domain.Skill} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.SkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SkillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private StringFilter implementation;

    private StringFilter validation;

    private IntegerFilter expiryPeriod;

    private StringFilter contact;

    private IntegerFilter score;

    private DoubleFilter rateScore;

    private IntegerFilter rateCount;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter badgesId;

    private LongFilter levelsId;

    private LongFilter teamsId;

    private LongFilter trainingsId;

    public SkillCriteria() {}

    public SkillCriteria(SkillCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.implementation = other.implementation == null ? null : other.implementation.copy();
        this.validation = other.validation == null ? null : other.validation.copy();
        this.expiryPeriod = other.expiryPeriod == null ? null : other.expiryPeriod.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.rateScore = other.rateScore == null ? null : other.rateScore.copy();
        this.rateCount = other.rateCount == null ? null : other.rateCount.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.badgesId = other.badgesId == null ? null : other.badgesId.copy();
        this.levelsId = other.levelsId == null ? null : other.levelsId.copy();
        this.teamsId = other.teamsId == null ? null : other.teamsId.copy();
        this.trainingsId = other.trainingsId == null ? null : other.trainingsId.copy();
    }

    @Override
    public SkillCriteria copy() {
        return new SkillCriteria(this);
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

    public StringFilter getImplementation() {
        return implementation;
    }

    public StringFilter implementation() {
        if (implementation == null) {
            implementation = new StringFilter();
        }
        return implementation;
    }

    public void setImplementation(StringFilter implementation) {
        this.implementation = implementation;
    }

    public StringFilter getValidation() {
        return validation;
    }

    public StringFilter validation() {
        if (validation == null) {
            validation = new StringFilter();
        }
        return validation;
    }

    public void setValidation(StringFilter validation) {
        this.validation = validation;
    }

    public IntegerFilter getExpiryPeriod() {
        return expiryPeriod;
    }

    public IntegerFilter expiryPeriod() {
        if (expiryPeriod == null) {
            expiryPeriod = new IntegerFilter();
        }
        return expiryPeriod;
    }

    public void setExpiryPeriod(IntegerFilter expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    public StringFilter getContact() {
        return contact;
    }

    public StringFilter contact() {
        if (contact == null) {
            contact = new StringFilter();
        }
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public IntegerFilter getScore() {
        return score;
    }

    public IntegerFilter score() {
        if (score == null) {
            score = new IntegerFilter();
        }
        return score;
    }

    public void setScore(IntegerFilter score) {
        this.score = score;
    }

    public DoubleFilter getRateScore() {
        return rateScore;
    }

    public DoubleFilter rateScore() {
        if (rateScore == null) {
            rateScore = new DoubleFilter();
        }
        return rateScore;
    }

    public void setRateScore(DoubleFilter rateScore) {
        this.rateScore = rateScore;
    }

    public IntegerFilter getRateCount() {
        return rateCount;
    }

    public IntegerFilter rateCount() {
        if (rateCount == null) {
            rateCount = new IntegerFilter();
        }
        return rateCount;
    }

    public void setRateCount(IntegerFilter rateCount) {
        this.rateCount = rateCount;
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

    public LongFilter getTeamsId() {
        return teamsId;
    }

    public LongFilter teamsId() {
        if (teamsId == null) {
            teamsId = new LongFilter();
        }
        return teamsId;
    }

    public void setTeamsId(LongFilter teamsId) {
        this.teamsId = teamsId;
    }

    public LongFilter getTrainingsId() {
        return trainingsId;
    }

    public LongFilter trainingsId() {
        if (trainingsId == null) {
            trainingsId = new LongFilter();
        }
        return trainingsId;
    }

    public void setTrainingsId(LongFilter trainingsId) {
        this.trainingsId = trainingsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SkillCriteria that = (SkillCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(implementation, that.implementation) &&
            Objects.equals(validation, that.validation) &&
            Objects.equals(expiryPeriod, that.expiryPeriod) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(score, that.score) &&
            Objects.equals(rateScore, that.rateScore) &&
            Objects.equals(rateCount, that.rateCount) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(badgesId, that.badgesId) &&
            Objects.equals(levelsId, that.levelsId) &&
            Objects.equals(teamsId, that.teamsId) &&
            Objects.equals(trainingsId, that.trainingsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            description,
            implementation,
            validation,
            expiryPeriod,
            contact,
            score,
            rateScore,
            rateCount,
            createdAt,
            updatedAt,
            badgesId,
            levelsId,
            teamsId,
            trainingsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (implementation != null ? "implementation=" + implementation + ", " : "") +
            (validation != null ? "validation=" + validation + ", " : "") +
            (expiryPeriod != null ? "expiryPeriod=" + expiryPeriod + ", " : "") +
            (contact != null ? "contact=" + contact + ", " : "") +
            (score != null ? "score=" + score + ", " : "") +
            (rateScore != null ? "rateScore=" + rateScore + ", " : "") +
            (rateCount != null ? "rateCount=" + rateCount + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (badgesId != null ? "badgesId=" + badgesId + ", " : "") +
            (levelsId != null ? "levelsId=" + levelsId + ", " : "") +
            (teamsId != null ? "teamsId=" + teamsId + ", " : "") +
            (trainingsId != null ? "trainingsId=" + trainingsId + ", " : "") +
            "}";
    }
}
