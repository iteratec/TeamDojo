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
 * A Badge.
 */
@Entity
@Table(name = "badge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Badge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "title", length = 20, nullable = false)
    private String title;

    @Size(max = 4096)
    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "available_until")
    private Instant availableUntil;

    @Min(value = 1)
    @Column(name = "available_amount")
    private Integer availableAmount;

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

    @OneToMany(mappedBy = "badge")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "badge", "skill" }, allowSetters = true)
    private Set<BadgeSkill> skills = new HashSet<>();

    @ManyToOne
    private Image image;

    @ManyToMany
    @JoinTable(
        name = "rel_badge__dimensions",
        joinColumns = @JoinColumn(name = "badge_id"),
        inverseJoinColumns = @JoinColumn(name = "dimensions_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "levels", "badges", "participants" }, allowSetters = true)
    private Set<Dimension> dimensions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Badge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Badge title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Badge description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getAvailableUntil() {
        return this.availableUntil;
    }

    public Badge availableUntil(Instant availableUntil) {
        this.setAvailableUntil(availableUntil);
        return this;
    }

    public void setAvailableUntil(Instant availableUntil) {
        this.availableUntil = availableUntil;
    }

    public Integer getAvailableAmount() {
        return this.availableAmount;
    }

    public Badge availableAmount(Integer availableAmount) {
        this.setAvailableAmount(availableAmount);
        return this;
    }

    public void setAvailableAmount(Integer availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Double getRequiredScore() {
        return this.requiredScore;
    }

    public Badge requiredScore(Double requiredScore) {
        this.setRequiredScore(requiredScore);
        return this;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }

    public Double getInstantMultiplier() {
        return this.instantMultiplier;
    }

    public Badge instantMultiplier(Double instantMultiplier) {
        this.setInstantMultiplier(instantMultiplier);
        return this;
    }

    public void setInstantMultiplier(Double instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
    }

    public Integer getCompletionBonus() {
        return this.completionBonus;
    }

    public Badge completionBonus(Integer completionBonus) {
        this.setCompletionBonus(completionBonus);
        return this;
    }

    public void setCompletionBonus(Integer completionBonus) {
        this.completionBonus = completionBonus;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Badge createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Badge updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<BadgeSkill> getSkills() {
        return this.skills;
    }

    public void setSkills(Set<BadgeSkill> badgeSkills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.setBadge(null));
        }
        if (badgeSkills != null) {
            badgeSkills.forEach(i -> i.setBadge(this));
        }
        this.skills = badgeSkills;
    }

    public Badge skills(Set<BadgeSkill> badgeSkills) {
        this.setSkills(badgeSkills);
        return this;
    }

    public Badge addSkills(BadgeSkill badgeSkill) {
        this.skills.add(badgeSkill);
        badgeSkill.setBadge(this);
        return this;
    }

    public Badge removeSkills(BadgeSkill badgeSkill) {
        this.skills.remove(badgeSkill);
        badgeSkill.setBadge(null);
        return this;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Badge image(Image image) {
        this.setImage(image);
        return this;
    }

    public Set<Dimension> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public Badge dimensions(Set<Dimension> dimensions) {
        this.setDimensions(dimensions);
        return this;
    }

    public Badge addDimensions(Dimension dimension) {
        this.dimensions.add(dimension);
        dimension.getBadges().add(this);
        return this;
    }

    public Badge removeDimensions(Dimension dimension) {
        this.dimensions.remove(dimension);
        dimension.getBadges().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Badge)) {
            return false;
        }
        return id != null && id.equals(((Badge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Badge{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", availableUntil='" + getAvailableUntil() + "'" +
            ", availableAmount=" + getAvailableAmount() +
            ", requiredScore=" + getRequiredScore() +
            ", instantMultiplier=" + getInstantMultiplier() +
            ", completionBonus=" + getCompletionBonus() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
