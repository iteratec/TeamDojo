package com.iteratec.teamdojo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Level} entity.
 */
// ### MODIFICATION-START ###
public class LevelDTO implements Serializable, com.iteratec.teamdojo.service.dto.custom.AuditableData {

    // ### MODIFICATION-END ###

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String title;

    @Size(max = 4096)
    private String description;

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

    private LevelDTO dependsOn;

    private ImageDTO image;

    private DimensionDTO dimension;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LevelDTO getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(LevelDTO dependsOn) {
        this.dependsOn = dependsOn;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

    public DimensionDTO getDimension() {
        return dimension;
    }

    public void setDimension(DimensionDTO dimension) {
        this.dimension = dimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelDTO)) {
            return false;
        }

        LevelDTO levelDTO = (LevelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, levelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", requiredScore=" + getRequiredScore() +
            ", instantMultiplier=" + getInstantMultiplier() +
            ", completionBonus=" + getCompletionBonus() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", dependsOn=" + getDependsOn() +
            ", image=" + getImage() +
            ", dimension=" + getDimension() +
            "}";
    }
}
