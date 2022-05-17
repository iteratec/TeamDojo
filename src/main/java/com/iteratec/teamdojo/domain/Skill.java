package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@GeneratedByJHipster
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    @Column(name = "title_en", length = 80, nullable = false)
    private String titleEN;

    @Size(max = 4096)
    @Column(name = "description_en", length = 4096)
    private String descriptionEN;

    @Size(max = 4096)
    @Column(name = "implementation_en", length = 4096)
    private String implementationEN;

    @Size(max = 4096)
    @Column(name = "validation_en", length = 4096)
    private String validationEN;

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
        return this.id;
    }

    public Skill id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleEN() {
        return this.titleEN;
    }

    public Skill titleEN(String titleEN) {
        this.setTitleEN(titleEN);
        return this;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getDescriptionEN() {
        return this.descriptionEN;
    }

    public Skill descriptionEN(String descriptionEN) {
        this.setDescriptionEN(descriptionEN);
        return this;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getImplementationEN() {
        return this.implementationEN;
    }

    public Skill implementationEN(String implementationEN) {
        this.setImplementationEN(implementationEN);
        return this;
    }

    public void setImplementationEN(String implementationEN) {
        this.implementationEN = implementationEN;
    }

    public String getValidationEN() {
        return this.validationEN;
    }

    public Skill validationEN(String validationEN) {
        this.setValidationEN(validationEN);
        return this;
    }

    public void setValidationEN(String validationEN) {
        this.validationEN = validationEN;
    }

    public Integer getExpiryPeriod() {
        return this.expiryPeriod;
    }

    public Skill expiryPeriod(Integer expiryPeriod) {
        this.setExpiryPeriod(expiryPeriod);
        return this;
    }

    public void setExpiryPeriod(Integer expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    public String getContact() {
        return this.contact;
    }

    public Skill contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getScore() {
        return this.score;
    }

    public Skill score(Integer score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getRateScore() {
        return this.rateScore;
    }

    public Skill rateScore(Double rateScore) {
        this.setRateScore(rateScore);
        return this;
    }

    public void setRateScore(Double rateScore) {
        this.rateScore = rateScore;
    }

    public Integer getRateCount() {
        return this.rateCount;
    }

    public Skill rateCount(Integer rateCount) {
        this.setRateCount(rateCount);
        return this;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Skill createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Skill updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<BadgeSkill> getBadges() {
        return this.badges;
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

    public Set<LevelSkill> getLevels() {
        return this.levels;
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

    public Set<TeamSkill> getTeams() {
        return this.teams;
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

    public Set<Training> getTrainings() {
        return this.trainings;
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
            ", titleEN='" + getTitleEN() + "'" +
            ", descriptionEN='" + getDescriptionEN() + "'" +
            ", implementationEN='" + getImplementationEN() + "'" +
            ", validationEN='" + getValidationEN() + "'" +
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
