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
 * A team group is a hierarchical construct to organize teams within a large organization into departments to separate\ndifferent teams more easily based on their organizational structure.
 */
@Entity
@Table(name = "team_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TeamGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "title", length = 80, nullable = false)
    private String title;

    @Size(max = 4096)
    @Column(name = "description", length = 4096)
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "group")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills", "image", "participations", "group" }, allowSetters = true)
    private Set<Team> teams = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "teams", "parent" }, allowSetters = true)
    private TeamGroup parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeamGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public TeamGroup title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public TeamGroup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public TeamGroup createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public TeamGroup updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setGroup(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setGroup(this));
        }
        this.teams = teams;
    }

    public TeamGroup teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public TeamGroup addTeams(Team team) {
        this.teams.add(team);
        team.setGroup(this);
        return this;
    }

    public TeamGroup removeTeams(Team team) {
        this.teams.remove(team);
        team.setGroup(null);
        return this;
    }

    public TeamGroup getParent() {
        return this.parent;
    }

    public void setParent(TeamGroup teamGroup) {
        this.parent = teamGroup;
    }

    public TeamGroup parent(TeamGroup teamGroup) {
        this.setParent(teamGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamGroup)) {
            return false;
        }
        return id != null && id.equals(((TeamGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamGroup{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
