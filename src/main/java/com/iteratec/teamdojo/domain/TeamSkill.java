package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.enumeration.SkillStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TeamSkill.
 */
@Entity
@Table(name = "team_skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@GeneratedByJHipster
public class TeamSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    @Column(name = "irrelevant")
    private Boolean irrelevant;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "skill_status", nullable = false)
    private SkillStatus skillStatus;

    @Size(max = 4096)
    @Column(name = "note", length = 4096)
    private String note;

    @NotNull
    @Column(name = "vote", nullable = false)
    private Integer vote;

    @Size(max = 255)
    @Column(name = "voters", length = 255)
    private String voters;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "badges", "levels", "teams", "trainings" }, allowSetters = true)
    private Skill skill;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "skills", "image", "participations", "group" }, allowSetters = true)
    private Team team;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeamSkill id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCompletedAt() {
        return this.completedAt;
    }

    public TeamSkill completedAt(Instant completedAt) {
        this.setCompletedAt(completedAt);
        return this;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public Instant getVerifiedAt() {
        return this.verifiedAt;
    }

    public TeamSkill verifiedAt(Instant verifiedAt) {
        this.setVerifiedAt(verifiedAt);
        return this;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Boolean getIrrelevant() {
        return this.irrelevant;
    }

    public TeamSkill irrelevant(Boolean irrelevant) {
        this.setIrrelevant(irrelevant);
        return this;
    }

    public void setIrrelevant(Boolean irrelevant) {
        this.irrelevant = irrelevant;
    }

    public SkillStatus getSkillStatus() {
        return this.skillStatus;
    }

    public TeamSkill skillStatus(SkillStatus skillStatus) {
        this.setSkillStatus(skillStatus);
        return this;
    }

    public void setSkillStatus(SkillStatus skillStatus) {
        this.skillStatus = skillStatus;
    }

    public String getNote() {
        return this.note;
    }

    public TeamSkill note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getVote() {
        return this.vote;
    }

    public TeamSkill vote(Integer vote) {
        this.setVote(vote);
        return this;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public String getVoters() {
        return this.voters;
    }

    public TeamSkill voters(String voters) {
        this.setVoters(voters);
        return this;
    }

    public void setVoters(String voters) {
        this.voters = voters;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public TeamSkill createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public TeamSkill updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public TeamSkill skill(Skill skill) {
        this.setSkill(skill);
        return this;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public TeamSkill team(Team team) {
        this.setTeam(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamSkill)) {
            return false;
        }
        return id != null && id.equals(((TeamSkill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamSkill{" +
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
            "}";
    }
}
