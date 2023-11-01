package com.iteratec.teamdojo.service.criteria;

import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.iteratec.teamdojo.domain.Skill} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.SkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class SkillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titleEN;

    private StringFilter titleDE;

    private StringFilter descriptionEN;

    private StringFilter descriptionDE;

    private StringFilter implementationEN;

    private StringFilter implementationDE;

    private StringFilter validationEN;

    private StringFilter validationDE;

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

    private Boolean distinct;

    public SkillCriteria() {}

    public SkillCriteria(SkillCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.titleEN = other.titleEN == null ? null : other.titleEN.copy();
        this.titleDE = other.titleDE == null ? null : other.titleDE.copy();
        this.descriptionEN = other.descriptionEN == null ? null : other.descriptionEN.copy();
        this.descriptionDE = other.descriptionDE == null ? null : other.descriptionDE.copy();
        this.implementationEN = other.implementationEN == null ? null : other.implementationEN.copy();
        this.implementationDE = other.implementationDE == null ? null : other.implementationDE.copy();
        this.validationEN = other.validationEN == null ? null : other.validationEN.copy();
        this.validationDE = other.validationDE == null ? null : other.validationDE.copy();
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
        this.distinct = other.distinct;
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

    public StringFilter getImplementationEN() {
        return implementationEN;
    }

    public StringFilter implementationEN() {
        if (implementationEN == null) {
            implementationEN = new StringFilter();
        }
        return implementationEN;
    }

    public void setImplementationEN(StringFilter implementationEN) {
        this.implementationEN = implementationEN;
    }

    public StringFilter getImplementationDE() {
        return implementationDE;
    }

    public StringFilter implementationDE() {
        if (implementationDE == null) {
            implementationDE = new StringFilter();
        }
        return implementationDE;
    }

    public void setImplementationDE(StringFilter implementationDE) {
        this.implementationDE = implementationDE;
    }

    public StringFilter getValidationEN() {
        return validationEN;
    }

    public StringFilter validationEN() {
        if (validationEN == null) {
            validationEN = new StringFilter();
        }
        return validationEN;
    }

    public void setValidationEN(StringFilter validationEN) {
        this.validationEN = validationEN;
    }

    public StringFilter getValidationDE() {
        return validationDE;
    }

    public StringFilter validationDE() {
        if (validationDE == null) {
            validationDE = new StringFilter();
        }
        return validationDE;
    }

    public void setValidationDE(StringFilter validationDE) {
        this.validationDE = validationDE;
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
        final SkillCriteria that = (SkillCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(titleEN, that.titleEN) &&
            Objects.equals(titleDE, that.titleDE) &&
            Objects.equals(descriptionEN, that.descriptionEN) &&
            Objects.equals(descriptionDE, that.descriptionDE) &&
            Objects.equals(implementationEN, that.implementationEN) &&
            Objects.equals(implementationDE, that.implementationDE) &&
            Objects.equals(validationEN, that.validationEN) &&
            Objects.equals(validationDE, that.validationDE) &&
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
            Objects.equals(trainingsId, that.trainingsId) &&
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
            implementationEN,
            implementationDE,
            validationEN,
            validationDE,
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
            trainingsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (titleEN != null ? "titleEN=" + titleEN + ", " : "") +
            (titleDE != null ? "titleDE=" + titleDE + ", " : "") +
            (descriptionEN != null ? "descriptionEN=" + descriptionEN + ", " : "") +
            (descriptionDE != null ? "descriptionDE=" + descriptionDE + ", " : "") +
            (implementationEN != null ? "implementationEN=" + implementationEN + ", " : "") +
            (implementationDE != null ? "implementationDE=" + implementationDE + ", " : "") +
            (validationEN != null ? "validationEN=" + validationEN + ", " : "") +
            (validationDE != null ? "validationDE=" + validationDE + ", " : "") +
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
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
