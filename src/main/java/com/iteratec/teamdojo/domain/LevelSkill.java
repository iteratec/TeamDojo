package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Lookup table entity for N-to-M relationships.
 */
@Entity
@Table(name = "level_skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LevelSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "badges", "levels", "teams", "trainings" }, allowSetters = true)
    private Skill skill;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "dependsOn", "skills", "image", "dimension" }, allowSetters = true)
    private Level level;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LevelSkill id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public LevelSkill skill(Skill skill) {
        this.setSkill(skill);
        return this;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public LevelSkill level(Level level) {
        this.setLevel(level);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelSkill)) {
            return false;
        }
        return id != null && id.equals(((LevelSkill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelSkill{" +
            "id=" + getId() +
            "}";
    }
}
