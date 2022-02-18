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
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
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

    @NotNull
    @Column(name = "days_until_expiration", nullable = false)
    private Double daysUntilExpiration;

    @NotNull
    @Column(name = "expired", nullable = false)
    private Boolean expired;

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skill", "team" }, allowSetters = true)
    private Set<TeamSkill> skills = new HashSet<>();

    @ManyToOne
    private Image image;

    @ManyToMany
    @JoinTable(
        name = "rel_team__participations",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "participations_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "levels", "badges", "participants" }, allowSetters = true)
    private Set<Dimension> participations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Team id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Team title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return this.shortTitle;
    }

    public Team shortTitle(String shortTitle) {
        this.setShortTitle(shortTitle);
        return this;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public Team slogan(String slogan) {
        this.setSlogan(slogan);
        return this;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getContact() {
        return this.contact;
    }

    public Team contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Instant getValidUntil() {
        return this.validUntil;
    }

    public Team validUntil(Instant validUntil) {
        this.setValidUntil(validUntil);
        return this;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Boolean getPureTrainingTeam() {
        return this.pureTrainingTeam;
    }

    public Team pureTrainingTeam(Boolean pureTrainingTeam) {
        this.setPureTrainingTeam(pureTrainingTeam);
        return this;
    }

    public void setPureTrainingTeam(Boolean pureTrainingTeam) {
        this.pureTrainingTeam = pureTrainingTeam;
    }

    public Boolean getOfficial() {
        return this.official;
    }

    public Team official(Boolean official) {
        this.setOfficial(official);
        return this;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Team createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Team updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getDaysUntilExpiration() {
        return this.daysUntilExpiration;
    }

    public Team daysUntilExpiration(Double daysUntilExpiration) {
        this.setDaysUntilExpiration(daysUntilExpiration);
        return this;
    }

    public void setDaysUntilExpiration(Double daysUntilExpiration) {
        this.daysUntilExpiration = daysUntilExpiration;
    }

    public Boolean getExpired() {
        return this.expired;
    }

    public Team expired(Boolean expired) {
        this.setExpired(expired);
        return this;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Set<TeamSkill> getSkills() {
        return this.skills;
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

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Team image(Image image) {
        this.setImage(image);
        return this;
    }

    public Set<Dimension> getParticipations() {
        return this.participations;
    }

    public void setParticipations(Set<Dimension> dimensions) {
        this.participations = dimensions;
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
            ", daysUntilExpiration=" + getDaysUntilExpiration() +
            ", expired='" + getExpired() + "'" +
            "}";
    }
}
