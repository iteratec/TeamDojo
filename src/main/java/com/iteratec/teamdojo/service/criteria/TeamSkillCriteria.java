package com.iteratec.teamdojo.service.criteria;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.enumeration.SkillStatus;
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
 * Criteria class for the {@link com.iteratec.teamdojo.domain.TeamSkill} entity. This class is used
 * in {@link com.iteratec.teamdojo.web.rest.TeamSkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /team-skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@GeneratedByJHipster
public class TeamSkillCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SkillStatus
     */
    @GeneratedByJHipster
    public static class SkillStatusFilter extends Filter<SkillStatus> {

        public SkillStatusFilter() {}

        public SkillStatusFilter(SkillStatusFilter filter) {
            super(filter);
        }

        @Override
        public SkillStatusFilter copy() {
            return new SkillStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter completedAt;

    private InstantFilter verifiedAt;

    private BooleanFilter irrelevant;

    private SkillStatusFilter skillStatus;

    private StringFilter note;

    private IntegerFilter vote;

    private StringFilter voters;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter skillId;

    private LongFilter teamId;

    private Boolean distinct;

    public TeamSkillCriteria() {}

    public TeamSkillCriteria(TeamSkillCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.completedAt = other.completedAt == null ? null : other.completedAt.copy();
        this.verifiedAt = other.verifiedAt == null ? null : other.verifiedAt.copy();
        this.irrelevant = other.irrelevant == null ? null : other.irrelevant.copy();
        this.skillStatus = other.skillStatus == null ? null : other.skillStatus.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.vote = other.vote == null ? null : other.vote.copy();
        this.voters = other.voters == null ? null : other.voters.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.skillId = other.skillId == null ? null : other.skillId.copy();
        this.teamId = other.teamId == null ? null : other.teamId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TeamSkillCriteria copy() {
        return new TeamSkillCriteria(this);
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

    public InstantFilter getCompletedAt() {
        return completedAt;
    }

    public InstantFilter completedAt() {
        if (completedAt == null) {
            completedAt = new InstantFilter();
        }
        return completedAt;
    }

    public void setCompletedAt(InstantFilter completedAt) {
        this.completedAt = completedAt;
    }

    public InstantFilter getVerifiedAt() {
        return verifiedAt;
    }

    public InstantFilter verifiedAt() {
        if (verifiedAt == null) {
            verifiedAt = new InstantFilter();
        }
        return verifiedAt;
    }

    public void setVerifiedAt(InstantFilter verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public BooleanFilter getIrrelevant() {
        return irrelevant;
    }

    public BooleanFilter irrelevant() {
        if (irrelevant == null) {
            irrelevant = new BooleanFilter();
        }
        return irrelevant;
    }

    public void setIrrelevant(BooleanFilter irrelevant) {
        this.irrelevant = irrelevant;
    }

    public SkillStatusFilter getSkillStatus() {
        return skillStatus;
    }

    public SkillStatusFilter skillStatus() {
        if (skillStatus == null) {
            skillStatus = new SkillStatusFilter();
        }
        return skillStatus;
    }

    public void setSkillStatus(SkillStatusFilter skillStatus) {
        this.skillStatus = skillStatus;
    }

    public StringFilter getNote() {
        return note;
    }

    public StringFilter note() {
        if (note == null) {
            note = new StringFilter();
        }
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public IntegerFilter getVote() {
        return vote;
    }

    public IntegerFilter vote() {
        if (vote == null) {
            vote = new IntegerFilter();
        }
        return vote;
    }

    public void setVote(IntegerFilter vote) {
        this.vote = vote;
    }

    public StringFilter getVoters() {
        return voters;
    }

    public StringFilter voters() {
        if (voters == null) {
            voters = new StringFilter();
        }
        return voters;
    }

    public void setVoters(StringFilter voters) {
        this.voters = voters;
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

    public LongFilter getTeamId() {
        return teamId;
    }

    public LongFilter teamId() {
        if (teamId == null) {
            teamId = new LongFilter();
        }
        return teamId;
    }

    public void setTeamId(LongFilter teamId) {
        this.teamId = teamId;
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
        final TeamSkillCriteria that = (TeamSkillCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(completedAt, that.completedAt) &&
            Objects.equals(verifiedAt, that.verifiedAt) &&
            Objects.equals(irrelevant, that.irrelevant) &&
            Objects.equals(skillStatus, that.skillStatus) &&
            Objects.equals(note, that.note) &&
            Objects.equals(vote, that.vote) &&
            Objects.equals(voters, that.voters) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(skillId, that.skillId) &&
            Objects.equals(teamId, that.teamId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            completedAt,
            verifiedAt,
            irrelevant,
            skillStatus,
            note,
            vote,
            voters,
            createdAt,
            updatedAt,
            skillId,
            teamId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamSkillCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (completedAt != null ? "completedAt=" + completedAt + ", " : "") +
            (verifiedAt != null ? "verifiedAt=" + verifiedAt + ", " : "") +
            (irrelevant != null ? "irrelevant=" + irrelevant + ", " : "") +
            (skillStatus != null ? "skillStatus=" + skillStatus + ", " : "") +
            (note != null ? "note=" + note + ", " : "") +
            (vote != null ? "vote=" + vote + ", " : "") +
            (voters != null ? "voters=" + voters + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (skillId != null ? "skillId=" + skillId + ", " : "") +
            (teamId != null ? "teamId=" + teamId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
