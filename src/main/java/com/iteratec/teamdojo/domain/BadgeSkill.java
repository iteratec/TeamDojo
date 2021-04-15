package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * This is a BadgeSkill\n@author Robert Seedorff
 */
@Entity
@Table(name = "badge_skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BadgeSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "skills", "image", "dimensions" }, allowSetters = true)
    private Badge badge;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "badges", "levels", "teams", "trainings" }, allowSetters = true)
    private Skill skill;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BadgeSkill id(Long id) {
        this.id = id;
        return this;
    }

    public Badge getBadge() {
        return this.badge;
    }

    public BadgeSkill badge(Badge badge) {
        this.setBadge(badge);
        return this;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public BadgeSkill skill(Skill skill) {
        this.setSkill(skill);
        return this;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BadgeSkill)) {
            return false;
        }
        return id != null && id.equals(((BadgeSkill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgeSkill{" +
            "id=" + getId() +
            "}";
    }
}
