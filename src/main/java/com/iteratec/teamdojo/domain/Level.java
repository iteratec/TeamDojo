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
 * This is an Level\n@author Robert Seedorff
 */
@Entity
@Table(name = "level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    @Column(name = "required_score", nullable = false)
    private Double requiredScore;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "instant_multiplier", nullable = false)
    private Double instantMultiplier;

    @Min(value = 0)
    @Column(name = "completion_bonus")
    private Integer completionBonus;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @JsonIgnoreProperties(value = { "dependsOn", "skills", "image", "dimension" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Level dependsOn;

    @OneToMany(mappedBy = "level")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skill", "level" }, allowSetters = true)
    private Set<LevelSkill> skills = new HashSet<>();

    @ManyToOne
    private Image image;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "levels", "badges", "participants" }, allowSetters = true)
    private Dimension dimension;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Level id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Level title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Level description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRequiredScore() {
        return this.requiredScore;
    }

    public Level requiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
        return this;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }

    public Double getInstantMultiplier() {
        return this.instantMultiplier;
    }

    public Level instantMultiplier(Double instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
        return this;
    }

    public void setInstantMultiplier(Double instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
    }

    public Integer getCompletionBonus() {
        return this.completionBonus;
    }

    public Level completionBonus(Integer completionBonus) {
        this.completionBonus = completionBonus;
        return this;
    }

    public void setCompletionBonus(Integer completionBonus) {
        this.completionBonus = completionBonus;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Level createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Level updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Level getDependsOn() {
        return this.dependsOn;
    }

    public Level dependsOn(Level level) {
        this.setDependsOn(level);
        return this;
    }

    public void setDependsOn(Level level) {
        this.dependsOn = level;
    }

    public Set<LevelSkill> getSkills() {
        return this.skills;
    }

    public Level skills(Set<LevelSkill> levelSkills) {
        this.setSkills(levelSkills);
        return this;
    }

    public Level addSkills(LevelSkill levelSkill) {
        this.skills.add(levelSkill);
        levelSkill.setLevel(this);
        return this;
    }

    public Level removeSkills(LevelSkill levelSkill) {
        this.skills.remove(levelSkill);
        levelSkill.setLevel(null);
        return this;
    }

    public void setSkills(Set<LevelSkill> levelSkills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.setLevel(null));
        }
        if (levelSkills != null) {
            levelSkills.forEach(i -> i.setLevel(this));
        }
        this.skills = levelSkills;
    }

    public Image getImage() {
        return this.image;
    }

    public Level image(Image image) {
        this.setImage(image);
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public Level dimension(Dimension dimension) {
        this.setDimension(dimension);
        return this;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Level)) {
            return false;
        }
        return id != null && id.equals(((Level) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Level{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", requiredScore=" + getRequiredScore() +
            ", instantMultiplier=" + getInstantMultiplier() +
            ", completionBonus=" + getCompletionBonus() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
