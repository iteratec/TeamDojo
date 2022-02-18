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
 * A Level.
 */
@Entity
@Table(name = "level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Size(max = 4096)
    @Column(name = "description", length = 4096)
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
        return this.id;
    }

    public Level id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Level title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Level description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRequiredScore() {
        return this.requiredScore;
    }

    public Level requiredScore(Double requiredScore) {
        this.setRequiredScore(requiredScore);
        return this;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }

    public Double getInstantMultiplier() {
        return this.instantMultiplier;
    }

    public Level instantMultiplier(Double instantMultiplier) {
        this.setInstantMultiplier(instantMultiplier);
        return this;
    }

    public void setInstantMultiplier(Double instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
    }

    public Integer getCompletionBonus() {
        return this.completionBonus;
    }

    public Level completionBonus(Integer completionBonus) {
        this.setCompletionBonus(completionBonus);
        return this;
    }

    public void setCompletionBonus(Integer completionBonus) {
        this.completionBonus = completionBonus;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Level createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Level updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Level getDependsOn() {
        return this.dependsOn;
    }

    public void setDependsOn(Level level) {
        this.dependsOn = level;
    }

    public Level dependsOn(Level level) {
        this.setDependsOn(level);
        return this;
    }

    public Set<LevelSkill> getSkills() {
        return this.skills;
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

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Level image(Image image) {
        this.setImage(image);
        return this;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Level dimension(Dimension dimension) {
        this.setDimension(dimension);
        return this;
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
