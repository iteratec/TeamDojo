package com.iteratec.teamdojo.service.criteria;

import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.iteratec.teamdojo.domain.Training} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.TrainingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trainings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class TrainingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titleEN;

    private StringFilter titleDE;

    private StringFilter descriptionEN;

    private StringFilter descriptionDE;

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
        this.titleEN = other.titleEN == null ? null : other.titleEN.copy();
        this.titleDE = other.titleDE == null ? null : other.titleDE.copy();
        this.descriptionEN = other.descriptionEN == null ? null : other.descriptionEN.copy();
        this.descriptionDE = other.descriptionDE == null ? null : other.descriptionDE.copy();
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
            Objects.equals(titleEN, that.titleEN) &&
            Objects.equals(titleDE, that.titleDE) &&
            Objects.equals(descriptionEN, that.descriptionEN) &&
            Objects.equals(descriptionDE, that.descriptionDE) &&
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
            titleEN,
            titleDE,
            descriptionEN,
            descriptionDE,
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
            (titleEN != null ? "titleEN=" + titleEN + ", " : "") +
            (titleDE != null ? "titleDE=" + titleDE + ", " : "") +
            (descriptionEN != null ? "descriptionEN=" + descriptionEN + ", " : "") +
            (descriptionDE != null ? "descriptionDE=" + descriptionDE + ", " : "") +
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
