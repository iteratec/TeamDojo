package com.iteratec.teamdojo.service.dto;

import com.iteratec.teamdojo.GeneratedByJHipster;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.domain.enumeration.custom.SkillStatus;
// ### MODIFICATION-END ###
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.TeamSkill} entity.
 */
// ### MODIFICATION-START ###
@GeneratedByJHipster
public class TeamSkillDTO implements Serializable, com.iteratec.teamdojo.service.dto.custom.AuditableData {

    // ### MODIFICATION-END ###

    private Long id;

    private Instant completedAt;

    private Instant verifiedAt;

    private Boolean irrelevant;

    @NotNull
    private SkillStatus skillStatus;

    @Size(max = 4096)
    private String note;

    @NotNull
    private Integer vote;

    @Size(max = 255)
    private String voters;

    // ### MODIFICATION-START ###
    private Instant createdAt;
    // ### MODIFICATION-END ###

    // ### MODIFICATION-START ###
    private Instant updatedAt;
    // ### MODIFICATION-END ###

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

    public SkillStatus getSkillStatus() {
        return skillStatus;
    }

    public void setSkillStatus(SkillStatus skillStatus) {
        this.skillStatus = skillStatus;
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
            ", skillStatus='" + getSkillStatus() + "'" +
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
