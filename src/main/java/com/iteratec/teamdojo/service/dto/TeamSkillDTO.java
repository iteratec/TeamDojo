package com.iteratec.teamdojo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.TeamSkill} entity.
 */
public class TeamSkillDTO implements Serializable {

    private Long id;

    private Instant completedAt;

    private Instant verifiedAt;

    private Boolean irrelevant;

    private String note;

    @NotNull
    private Integer vote;

    private String voters;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    private SkillDTO skill;

    private TeamDTO team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Boolean getIrrelevant() {
        return irrelevant;
    }

    public void setIrrelevant(Boolean irrelevant) {
        this.irrelevant = irrelevant;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public String getVoters() {
        return voters;
    }

    public void setVoters(String voters) {
        this.voters = voters;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SkillDTO getSkill() {
        return skill;
    }

    public void setSkill(SkillDTO skill) {
        this.skill = skill;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamSkillDTO)) {
            return false;
        }

        TeamSkillDTO teamSkillDTO = (TeamSkillDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamSkillDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamSkillDTO{" +
            "id=" + getId() +
            ", completedAt='" + getCompletedAt() + "'" +
            ", verifiedAt='" + getVerifiedAt() + "'" +
            ", irrelevant='" + getIrrelevant() + "'" +
            ", note='" + getNote() + "'" +
            ", vote=" + getVote() +
            ", voters='" + getVoters() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", skill=" + getSkill() +
            ", team=" + getTeam() +
            "}";
    }
}
