package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * This is an Skill\n@author Robert Seedorff
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    @Column(name = "title", length = 80, nullable = false)
    private String title;

    @Size(max = 4096)
    @Column(name = "description", length = 4096)
    private String description;

    @Size(max = 4096)
    @Column(name = "implementation", length = 4096)
    private String implementation;

    @Size(max = 4096)
    @Column(name = "validation", length = 4096)
    private String validation;

    @Min(value = 1)
    @Column(name = "expiry_period")
    private Integer expiryPeriod;

    @Size(max = 255)
    @Column(name = "contact", length = 255)
    private String contact;

    @NotNull
    @Min(value = 0)
    @Column(name = "score", nullable = false)
    private Integer score;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "rate_score")
    private Double rateScore;

    @NotNull
    @Min(value = 0)
    @Column(name = "rate_count", nullable = false)
    private Integer rateCount;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "skill")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "badge", "skill" }, allowSetters = true)
    private Set<BadgeSkill> badges = new HashSet<>();

    @OneToMany(mappedBy = "skill")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skill", "level" }, allowSetters = true)
    private Set<LevelSkill> levels = new HashSet<>();

    @OneToMany(mappedBy = "skill")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skill", "team" }, allowSetters = true)
    private Set<TeamSkill> teams = new HashSet<>();

    @ManyToMany(mappedBy = "skills")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills" }, allowSetters = true)
    private Set<Training> trainings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Skill id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Skill title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Skill description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImplementation() {
        return this.implementation;
    }

    public Skill implementation(String implementation) {
        this.implementation = implementation;
        return this;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public String getValidation() {
        return this.validation;
    }

    public Skill validation(String validation) {
        this.validation = validation;
        return this;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public Integer getExpiryPeriod() {
        return this.expiryPeriod;
    }

    public Skill expiryPeriod(Integer expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
        return this;
    }

    public void setExpiryPeriod(Integer expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    public String getContact() {
        return this.contact;
    }

    public Skill contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getScore() {
        return this.score;
    }

    public Skill score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getRateScore() {
        return this.rateScore;
    }

    public Skill rateScore(Double rateScore) {
        this.rateScore = rateScore;
        return this;
    }

    public void setRateScore(Double rateScore) {
        this.rateScore = rateScore;
    }

    public Integer getRateCount() {
        return this.rateCount;
    }

    public Skill rateCount(Integer rateCount) {
        this.rateCount = rateCount;
        return this;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Skill createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Skill updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<BadgeSkill> getBadges() {
        return this.badges;
    }

    public Skill badges(Set<BadgeSkill> badgeSkills) {
        this.setBadges(badgeSkills);
        return this;
    }

    public Skill addBadges(BadgeSkill badgeSkill) {
        this.badges.add(badgeSkill);
        badgeSkill.setSkill(this);
        return this;
    }

    public Skill removeBadges(BadgeSkill badgeSkill) {
        this.badges.remove(badgeSkill);
        badgeSkill.setSkill(null);
        return this;
    }

    public void setBadges(Set<BadgeSkill> badgeSkills) {
        if (this.badges != null) {
            this.badges.forEach(i -> i.setSkill(null));
        }
        if (badgeSkills != null) {
            badgeSkills.forEach(i -> i.setSkill(this));
        }
        this.badges = badgeSkills;
    }

    public Set<LevelSkill> getLevels() {
        return this.levels;
    }

    public Skill levels(Set<LevelSkill> levelSkills) {
        this.setLevels(levelSkills);
        return this;
    }

    public Skill addLevels(LevelSkill levelSkill) {
        this.levels.add(levelSkill);
        levelSkill.setSkill(this);
        return this;
    }

    public Skill removeLevels(LevelSkill levelSkill) {
        this.levels.remove(levelSkill);
        levelSkill.setSkill(null);
        return this;
    }

    public void setLevels(Set<LevelSkill> levelSkills) {
        if (this.levels != null) {
            this.levels.forEach(i -> i.setSkill(null));
        }
        if (levelSkills != null) {
            levelSkills.forEach(i -> i.setSkill(this));
        }
        this.levels = levelSkills;
    }

    public Set<TeamSkill> getTeams() {
        return this.teams;
    }

    public Skill teams(Set<TeamSkill> teamSkills) {
        this.setTeams(teamSkills);
        return this;
    }

    public Skill addTeams(TeamSkill teamSkill) {
        this.teams.add(teamSkill);
        teamSkill.setSkill(this);
        return this;
    }

    public Skill removeTeams(TeamSkill teamSkill) {
        this.teams.remove(teamSkill);
        teamSkill.setSkill(null);
        return this;
    }

    public void setTeams(Set<TeamSkill> teamSkills) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setSkill(null));
        }
        if (teamSkills != null) {
            teamSkills.forEach(i -> i.setSkill(this));
        }
        this.teams = teamSkills;
    }

    public Set<Training> getTrainings() {
        return this.trainings;
    }

    public Skill trainings(Set<Training> trainings) {
        this.setTrainings(trainings);
        return this;
    }

    public Skill addTrainings(Training training) {
        this.trainings.add(training);
        training.getSkills().add(this);
        return this;
    }

    public Skill removeTrainings(Training training) {
        this.trainings.remove(training);
        training.getSkills().remove(this);
        return this;
    }

    public void setTrainings(Set<Training> trainings) {
        if (this.trainings != null) {
            this.trainings.forEach(i -> i.removeSkill(this));
        }
        if (trainings != null) {
            trainings.forEach(i -> i.addSkill(this));
        }
        this.trainings = trainings;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
            return false;
        }
        return id != null && id.equals(((Skill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", implementation='" + getImplementation() + "'" +
            ", validation='" + getValidation() + "'" +
            ", expiryPeriod=" + getExpiryPeriod() +
            ", contact='" + getContact() + "'" +
            ", score=" + getScore() +
            ", rateScore=" + getRateScore() +
            ", rateCount=" + getRateCount() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
