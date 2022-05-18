package com.iteratec.teamdojo.service.dto;

import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Badge} entity.
 */
@GeneratedByJHipster
// ### MODIFICATION-START ###
public class BadgeDTO implements Serializable, com.iteratec.teamdojo.service.dto.custom.AuditableData {

    // ### MODIFICATION-END ###

    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    private String titleEN;

    @Size(min = 2, max = 20)
    private String titleDE;

    @Size(max = 4096)
    private String descriptionEN;

    @Size(max = 4096)
    private String descriptionDE;

    private Instant availableUntil;

    @Min(value = 1)
    private Integer availableAmount;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    private Double requiredScore;

    @NotNull
    @DecimalMin(value = "0")
    private Double instantMultiplier;

    @Min(value = 0)
    private Integer completionBonus;

    // ### MODIFICATION-START ###
    private Instant createdAt;
    // ### MODIFICATION-END ###

    // ### MODIFICATION-START ###
    private Instant updatedAt;
    // ### MODIFICATION-END ###

    private ImageDTO image;

    private Set<DimensionDTO> dimensions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTitleDE() {
        return titleDE;
    }

    public void setTitleDE(String titleDE) {
        this.titleDE = titleDE;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionDE() {
        return descriptionDE;
    }

    public void setDescriptionDE(String descriptionDE) {
        this.descriptionDE = descriptionDE;
    }

    public Instant getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(Instant availableUntil) {
        this.availableUntil = availableUntil;
    }

    public Integer getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Integer availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Double getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }

    public Double getInstantMultiplier() {
        return instantMultiplier;
    }

    public void setInstantMultiplier(Double instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
    }

    public Integer getCompletionBonus() {
        return completionBonus;
    }

    public void setCompletionBonus(Integer completionBonus) {
        this.completionBonus = completionBonus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

    public Set<DimensionDTO> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Set<DimensionDTO> dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BadgeDTO)) {
            return false;
        }

        BadgeDTO badgeDTO = (BadgeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, badgeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgeDTO{" +
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
            ", image=" + getImage() +
            ", dimensions=" + getDimensions() +
            "}";
    }
}
