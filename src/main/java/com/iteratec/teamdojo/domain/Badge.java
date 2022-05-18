package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iteratec.teamdojo.GeneratedByJHipster;
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
@GeneratedByJHipster
public class Badge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "title_en", length = 20, nullable = false)
    private String titleEN;

    @Size(min = 2, max = 20)
    @Column(name = "title_de", length = 20)
    private String titleDE;

    @Size(max = 4096)
    @Column(name = "description_en", length = 4096)
    private String descriptionEN;

    @Size(max = 4096)
    @Column(name = "description_de", length = 4096)
    private String descriptionDE;

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

    public String getTitleEN() {
        return this.titleEN;
    }

    public Badge titleEN(String titleEN) {
        this.setTitleEN(titleEN);
        return this;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTitleDE() {
        return this.titleDE;
    }

    public Badge titleDE(String titleDE) {
        this.setTitleDE(titleDE);
        return this;
    }

    public void setTitleDE(String titleDE) {
        this.titleDE = titleDE;
    }

    public String getDescriptionEN() {
        return this.descriptionEN;
    }

    public Badge descriptionEN(String descriptionEN) {
        this.setDescriptionEN(descriptionEN);
        return this;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionDE() {
        return this.descriptionDE;
    }

    public Badge descriptionDE(String descriptionDE) {
        this.setDescriptionDE(descriptionDE);
        return this;
    }

    public void setDescriptionDE(String descriptionDE) {
        this.descriptionDE = descriptionDE;
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
            ", titleEN='" + getTitleEN() + "'" +
            ", titleDE='" + getTitleDE() + "'" +
            ", descriptionEN='" + getDescriptionEN() + "'" +
            ", descriptionDE='" + getDescriptionDE() + "'" +
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
