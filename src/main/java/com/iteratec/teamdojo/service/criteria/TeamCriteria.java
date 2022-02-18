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
 * Criteria class for the {@link com.iteratec.teamdojo.domain.Team} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.TeamResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /teams?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TeamCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter shortTitle;

    private StringFilter slogan;

    private StringFilter contact;

    private InstantFilter validUntil;

    private BooleanFilter pureTrainingTeam;

    private BooleanFilter official;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private DoubleFilter daysUntilExpiration;

    private BooleanFilter expired;

    private LongFilter skillsId;

    private LongFilter imageId;

    private LongFilter participationsId;

    private Boolean distinct;

    public TeamCriteria() {}

    public TeamCriteria(TeamCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.shortTitle = other.shortTitle == null ? null : other.shortTitle.copy();
        this.slogan = other.slogan == null ? null : other.slogan.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.validUntil = other.validUntil == null ? null : other.validUntil.copy();
        this.pureTrainingTeam = other.pureTrainingTeam == null ? null : other.pureTrainingTeam.copy();
        this.official = other.official == null ? null : other.official.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.daysUntilExpiration = other.daysUntilExpiration == null ? null : other.daysUntilExpiration.copy();
        this.expired = other.expired == null ? null : other.expired.copy();
        this.skillsId = other.skillsId == null ? null : other.skillsId.copy();
        this.imageId = other.imageId == null ? null : other.imageId.copy();
        this.participationsId = other.participationsId == null ? null : other.participationsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TeamCriteria copy() {
        return new TeamCriteria(this);
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

    public StringFilter getShortTitle() {
        return shortTitle;
    }

    public StringFilter shortTitle() {
        if (shortTitle == null) {
            shortTitle = new StringFilter();
        }
        return shortTitle;
    }

    public void setShortTitle(StringFilter shortTitle) {
        this.shortTitle = shortTitle;
    }

    public StringFilter getSlogan() {
        return slogan;
    }

    public StringFilter slogan() {
        if (slogan == null) {
            slogan = new StringFilter();
        }
        return slogan;
    }

    public void setSlogan(StringFilter slogan) {
        this.slogan = slogan;
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

    public InstantFilter getValidUntil() {
        return validUntil;
    }

    public InstantFilter validUntil() {
        if (validUntil == null) {
            validUntil = new InstantFilter();
        }
        return validUntil;
    }

    public void setValidUntil(InstantFilter validUntil) {
        this.validUntil = validUntil;
    }

    public BooleanFilter getPureTrainingTeam() {
        return pureTrainingTeam;
    }

    public BooleanFilter pureTrainingTeam() {
        if (pureTrainingTeam == null) {
            pureTrainingTeam = new BooleanFilter();
        }
        return pureTrainingTeam;
    }

    public void setPureTrainingTeam(BooleanFilter pureTrainingTeam) {
        this.pureTrainingTeam = pureTrainingTeam;
    }

    public BooleanFilter getOfficial() {
        return official;
    }

    public BooleanFilter official() {
        if (official == null) {
            official = new BooleanFilter();
        }
        return official;
    }

    public void setOfficial(BooleanFilter official) {
        this.official = official;
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

    public DoubleFilter getDaysUntilExpiration() {
        return daysUntilExpiration;
    }

    public DoubleFilter daysUntilExpiration() {
        if (daysUntilExpiration == null) {
            daysUntilExpiration = new DoubleFilter();
        }
        return daysUntilExpiration;
    }

    public void setDaysUntilExpiration(DoubleFilter daysUntilExpiration) {
        this.daysUntilExpiration = daysUntilExpiration;
    }

    public BooleanFilter getExpired() {
        return expired;
    }

    public BooleanFilter expired() {
        if (expired == null) {
            expired = new BooleanFilter();
        }
        return expired;
    }

    public void setExpired(BooleanFilter expired) {
        this.expired = expired;
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

    public LongFilter getParticipationsId() {
        return participationsId;
    }

    public LongFilter participationsId() {
        if (participationsId == null) {
            participationsId = new LongFilter();
        }
        return participationsId;
    }

    public void setParticipationsId(LongFilter participationsId) {
        this.participationsId = participationsId;
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
        final TeamCriteria that = (TeamCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(shortTitle, that.shortTitle) &&
            Objects.equals(slogan, that.slogan) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(validUntil, that.validUntil) &&
            Objects.equals(pureTrainingTeam, that.pureTrainingTeam) &&
            Objects.equals(official, that.official) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(daysUntilExpiration, that.daysUntilExpiration) &&
            Objects.equals(expired, that.expired) &&
            Objects.equals(skillsId, that.skillsId) &&
            Objects.equals(imageId, that.imageId) &&
            Objects.equals(participationsId, that.participationsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            shortTitle,
            slogan,
            contact,
            validUntil,
            pureTrainingTeam,
            official,
            createdAt,
            updatedAt,
            daysUntilExpiration,
            expired,
            skillsId,
            imageId,
            participationsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (shortTitle != null ? "shortTitle=" + shortTitle + ", " : "") +
            (slogan != null ? "slogan=" + slogan + ", " : "") +
            (contact != null ? "contact=" + contact + ", " : "") +
            (validUntil != null ? "validUntil=" + validUntil + ", " : "") +
            (pureTrainingTeam != null ? "pureTrainingTeam=" + pureTrainingTeam + ", " : "") +
            (official != null ? "official=" + official + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (daysUntilExpiration != null ? "daysUntilExpiration=" + daysUntilExpiration + ", " : "") +
            (expired != null ? "expired=" + expired + ", " : "") +
            (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            (imageId != null ? "imageId=" + imageId + ", " : "") +
            (participationsId != null ? "participationsId=" + participationsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
