package com.iteratec.teamdojo.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Skill} entity.
 */
@ApiModel(description = "This is an Skill\n@author Robert Seedorff")
public class SkillDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    private String title;

    @Size(max = 4096)
    private String description;

    @Size(max = 4096)
    private String implementation;

    @Size(max = 4096)
    private String validation;

    @Min(value = 1)
    private Integer expiryPeriod;

    @Size(max = 255)
    private String contact;

    @NotNull
    @Min(value = 0)
    private Integer score;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    private Double rateScore;

    @NotNull
    @Min(value = 0)
    private Integer rateCount;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

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

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public Integer getExpiryPeriod() {
        return expiryPeriod;
    }

    public void setExpiryPeriod(Integer expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getRateScore() {
        return rateScore;
    }

    public void setRateScore(Double rateScore) {
        this.rateScore = rateScore;
    }

    public Integer getRateCount() {
        return rateCount;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillDTO)) {
            return false;
        }

        SkillDTO skillDTO = (SkillDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, skillDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", implementation='" + getImplementation() + "'" +
            ", validation='" + getValidation() + "'" +
            ", expiryPeriod=" + getExpiryPeriod() +
            ", contact='" + getContact() + "'" +
            ", score=" + getScore() +
            ", rateScore=" + getRateScore() +
            ", rateCount=" + getRateCount() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
