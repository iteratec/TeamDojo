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
 * A Dimension.
 */
@Entity
@Table(name = "dimension")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "dimension")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dependsOn", "skills", "image", "dimension" }, allowSetters = true)
    private Set<Level> levels = new HashSet<>();

    @ManyToMany(mappedBy = "dimensions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills", "image", "dimensions" }, allowSetters = true)
    private Set<Badge> badges = new HashSet<>();

    @ManyToMany(mappedBy = "participations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills", "image", "participations" }, allowSetters = true)
    private Set<Team> participants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dimension id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Dimension title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Dimension description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Dimension createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Dimension updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Level> getLevels() {
        return this.levels;
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

    public void setLevels(Set<Level> levels) {
        if (this.levels != null) {
            this.levels.forEach(i -> i.setDimension(null));
        }
        if (levels != null) {
            levels.forEach(i -> i.setDimension(this));
        }
        this.levels = levels;
    }

    public Set<Badge> getBadges() {
        return this.badges;
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

    public void setBadges(Set<Badge> badges) {
        if (this.badges != null) {
            this.badges.forEach(i -> i.removeDimensions(this));
        }
        if (badges != null) {
            badges.forEach(i -> i.addDimensions(this));
        }
        this.badges = badges;
    }

    public Set<Team> getParticipants() {
        return this.participants;
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

    public void setParticipants(Set<Team> teams) {
        if (this.participants != null) {
            this.participants.forEach(i -> i.removeParticipations(this));
        }
        if (teams != null) {
            teams.forEach(i -> i.addParticipations(this));
        }
        this.participants = teams;
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
        return id != null && id.equals(((Dimension) o).id);
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
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
