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
 * Criteria class for the {@link com.iteratec.teamdojo.domain.Training} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.TrainingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trainings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TrainingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private StringFilter contact;

    private StringFilter link;

    private InstantFilter validUntil;

    private BooleanFilter isOfficial;

    private StringFilter suggestedBy;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter skillId;

    private Boolean distinct;

    public TrainingCriteria() {}

    public TrainingCriteria(TrainingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.validUntil = other.validUntil == null ? null : other.validUntil.copy();
        this.isOfficial = other.isOfficial == null ? null : other.isOfficial.copy();
        this.suggestedBy = other.suggestedBy == null ? null : other.suggestedBy.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.skillId = other.skillId == null ? null : other.skillId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TrainingCriteria copy() {
        return new TrainingCriteria(this);
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

    public StringFilter getLink() {
        return link;
    }

    public StringFilter link() {
        if (link == null) {
            link = new StringFilter();
        }
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
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

    public BooleanFilter getIsOfficial() {
        return isOfficial;
    }

    public BooleanFilter isOfficial() {
        if (isOfficial == null) {
            isOfficial = new BooleanFilter();
        }
        return isOfficial;
    }

    public void setIsOfficial(BooleanFilter isOfficial) {
        this.isOfficial = isOfficial;
    }

    public StringFilter getSuggestedBy() {
        return suggestedBy;
    }

    public StringFilter suggestedBy() {
        if (suggestedBy == null) {
            suggestedBy = new StringFilter();
        }
        return suggestedBy;
    }

    public void setSuggestedBy(StringFilter suggestedBy) {
        this.suggestedBy = suggestedBy;
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
        final TrainingCriteria that = (TrainingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(link, that.link) &&
            Objects.equals(validUntil, that.validUntil) &&
            Objects.equals(isOfficial, that.isOfficial) &&
            Objects.equals(suggestedBy, that.suggestedBy) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(skillId, that.skillId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            description,
            contact,
            link,
            validUntil,
            isOfficial,
            suggestedBy,
            createdAt,
            updatedAt,
            skillId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (contact != null ? "contact=" + contact + ", " : "") +
            (link != null ? "link=" + link + ", " : "") +
            (validUntil != null ? "validUntil=" + validUntil + ", " : "") +
            (isOfficial != null ? "isOfficial=" + isOfficial + ", " : "") +
            (suggestedBy != null ? "suggestedBy=" + suggestedBy + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (skillId != null ? "skillId=" + skillId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
