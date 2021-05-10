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
 * This is an Team\n@author Robert Seedorff
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @NotNull
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    @Column(name = "short_title", length = 20, nullable = false)
    private String shortTitle;

    @Size(max = 255)
    @Column(name = "slogan", length = 255)
    private String slogan;

    @Size(max = 255)
    @Column(name = "contact", length = 255)
    private String contact;

    @Column(name = "valid_until")
    private Instant validUntil;

    @NotNull
    @Column(name = "pure_training_team", nullable = false)
    private Boolean pureTrainingTeam;

    @NotNull
    @Column(name = "official", nullable = false)
    private Boolean official;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skill", "team" }, allowSetters = true)
    private Set<TeamSkill> skills = new HashSet<>();

    @ManyToOne
    private Image image;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_team__participations",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "participations_id")
    )
    @JsonIgnoreProperties(value = { "levels", "badges", "participants" }, allowSetters = true)
    private Set<Dimension> participations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Team title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return this.shortTitle;
    }

    public Team shortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
        return this;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public Team slogan(String slogan) {
        this.slogan = slogan;
        return this;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getContact() {
        return this.contact;
    }

    public Team contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Instant getValidUntil() {
        return this.validUntil;
    }

    public Team validUntil(Instant validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Boolean getPureTrainingTeam() {
        return this.pureTrainingTeam;
    }

    public Team pureTrainingTeam(Boolean pureTrainingTeam) {
        this.pureTrainingTeam = pureTrainingTeam;
        return this;
    }

    public void setPureTrainingTeam(Boolean pureTrainingTeam) {
        this.pureTrainingTeam = pureTrainingTeam;
    }

    public Boolean getOfficial() {
        return this.official;
    }

    public Team official(Boolean official) {
        this.official = official;
        return this;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Team createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Team updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<TeamSkill> getSkills() {
        return this.skills;
    }

    public Team skills(Set<TeamSkill> teamSkills) {
        this.setSkills(teamSkills);
        return this;
    }

    public Team addSkills(TeamSkill teamSkill) {
        this.skills.add(teamSkill);
        teamSkill.setTeam(this);
        return this;
    }

    public Team removeSkills(TeamSkill teamSkill) {
        this.skills.remove(teamSkill);
        teamSkill.setTeam(null);
        return this;
    }

    public void setSkills(Set<TeamSkill> teamSkills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.setTeam(null));
        }
        if (teamSkills != null) {
            teamSkills.forEach(i -> i.setTeam(this));
        }
        this.skills = teamSkills;
    }

    public Image getImage() {
        return this.image;
    }

    public Team image(Image image) {
        this.setImage(image);
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<Dimension> getParticipations() {
        return this.participations;
    }

    public Team participations(Set<Dimension> dimensions) {
        this.setParticipations(dimensions);
        return this;
    }

    public Team addParticipations(Dimension dimension) {
        this.participations.add(dimension);
        dimension.getParticipants().add(this);
        return this;
    }

    public Team removeParticipations(Dimension dimension) {
        this.participations.remove(dimension);
        dimension.getParticipants().remove(this);
        return this;
    }

    public void setParticipations(Set<Dimension> dimensions) {
        this.participations = dimensions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", shortTitle='" + getShortTitle() + "'" +
            ", slogan='" + getSlogan() + "'" +
            ", contact='" + getContact() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", pureTrainingTeam='" + getPureTrainingTeam() + "'" +
            ", official='" + getOfficial() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
