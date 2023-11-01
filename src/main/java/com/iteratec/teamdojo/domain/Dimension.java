package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iteratec.teamdojo.GeneratedByJHipster;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Dimension.
 */
@Entity
@Table(name = "dimension")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "title_en", length = 50, nullable = false)
    private String titleEN;

    @Size(min = 1, max = 50)
    @Column(name = "title_de", length = 50)
    private String titleDE;

    @Size(max = 4096)
    @Column(name = "description_en", length = 4096)
    private String descriptionEN;

    @Size(max = 4096)
    @Column(name = "description_de", length = 4096)
    private String descriptionDE;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dimension")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dependsOn", "skills", "image", "dimension", "level" }, allowSetters = true)
    private Set<Level> levels = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dimensions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills", "image", "dimensions" }, allowSetters = true)
    private Set<Badge> badges = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "participations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills", "image", "participations", "group" }, allowSetters = true)
    private Set<Team> participants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dimension id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleEN() {
        return this.titleEN;
    }

    public Dimension titleEN(String titleEN) {
        this.setTitleEN(titleEN);
        return this;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTitleDE() {
        return this.titleDE;
    }

    public Dimension titleDE(String titleDE) {
        this.setTitleDE(titleDE);
        return this;
    }

    public void setTitleDE(String titleDE) {
        this.titleDE = titleDE;
    }

    public String getDescriptionEN() {
        return this.descriptionEN;
    }

    public Dimension descriptionEN(String descriptionEN) {
        this.setDescriptionEN(descriptionEN);
        return this;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionDE() {
        return this.descriptionDE;
    }

    public Dimension descriptionDE(String descriptionDE) {
        this.setDescriptionDE(descriptionDE);
        return this;
    }

    public void setDescriptionDE(String descriptionDE) {
        this.descriptionDE = descriptionDE;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Dimension createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Dimension updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Level> getLevels() {
        return this.levels;
    }

    public void setLevels(Set<Level> levels) {
        if (this.levels != null) {
            this.levels.forEach(i -> i.setDimension(null));
        }
        if (levels != null) {
            levels.forEach(i -> i.setDimension(this));
        }
        this.levels = levels;
    }

    public Dimension levels(Set<Level> levels) {
        this.setLevels(levels);
        return this;
    }

    public Dimension addLevels(Level level) {
        this.levels.add(level);
        level.setDimension(this);
        return this;
    }

    public Dimension removeLevels(Level level) {
        this.levels.remove(level);
        level.setDimension(null);
        return this;
    }

    public Set<Badge> getBadges() {
        return this.badges;
    }

    public void setBadges(Set<Badge> badges) {
        if (this.badges != null) {
            this.badges.forEach(i -> i.removeDimensions(this));
        }
        if (badges != null) {
            badges.forEach(i -> i.addDimensions(this));
        }
        this.badges = badges;
    }

    public Dimension badges(Set<Badge> badges) {
        this.setBadges(badges);
        return this;
    }

    public Dimension addBadges(Badge badge) {
        this.badges.add(badge);
        badge.getDimensions().add(this);
        return this;
    }

    public Dimension removeBadges(Badge badge) {
        this.badges.remove(badge);
        badge.getDimensions().remove(this);
        return this;
    }

    public Set<Team> getParticipants() {
        return this.participants;
    }

    public void setParticipants(Set<Team> teams) {
        if (this.participants != null) {
            this.participants.forEach(i -> i.removeParticipations(this));
        }
        if (teams != null) {
            teams.forEach(i -> i.addParticipations(this));
        }
        this.participants = teams;
    }

    public Dimension participants(Set<Team> teams) {
        this.setParticipants(teams);
        return this;
    }

    public Dimension addParticipants(Team team) {
        this.participants.add(team);
        team.getParticipations().add(this);
        return this;
    }

    public Dimension removeParticipants(Team team) {
        this.participants.remove(team);
        team.getParticipations().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dimension)) {
            return false;
        }
        return getId() != null && getId().equals(((Dimension) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dimension{" +
            "id=" + getId() +
            ", titleEN='" + getTitleEN() + "'" +
            ", titleDE='" + getTitleDE() + "'" +
            ", descriptionEN='" + getDescriptionEN() + "'" +
            ", descriptionDE='" + getDescriptionDE() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
