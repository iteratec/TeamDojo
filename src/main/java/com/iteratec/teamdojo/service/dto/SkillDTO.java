package com.iteratec.teamdojo.service.dto;

import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Skill} entity.
 */
// ### MODIFICATION-START ###
@GeneratedByJHipster
public class SkillDTO implements Serializable, com.iteratec.teamdojo.service.dto.custom.AuditableData {

    // ### MODIFICATION-END ###

    private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    private String titleEN;

    @Size(min = 5, max = 80)
    private String titleDE;

    @Size(max = 4096)
    private String descriptionEN;

    @Size(max = 4096)
    private String descriptionDE;

    @Size(max = 4096)
    private String implementationEN;

    @Size(max = 4096)
    private String implementationDE;

    @Size(max = 4096)
    private String validationEN;

    @Size(max = 4096)
    private String validationDE;

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

    // ### MODIFICATION-START ###
    private Instant createdAt;
    // ### MODIFICATION-END ###

    // ### MODIFICATION-START ###
    private Instant updatedAt;

    // ### MODIFICATION-END ###

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

    public String getImplementationEN() {
        return implementationEN;
    }

    public void setImplementationEN(String implementationEN) {
        this.implementationEN = implementationEN;
    }

    public String getImplementationDE() {
        return implementationDE;
    }

    public void setImplementationDE(String implementationDE) {
        this.implementationDE = implementationDE;
    }

    public String getValidationEN() {
        return validationEN;
    }

    public void setValidationEN(String validationEN) {
        this.validationEN = validationEN;
    }

    public String getValidationDE() {
        return validationDE;
    }

    public void setValidationDE(String validationDE) {
        this.validationDE = validationDE;
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
            ", titleEN='" + getTitleEN() + "'" +
            ", titleDE='" + getTitleDE() + "'" +
            ", descriptionEN='" + getDescriptionEN() + "'" +
            ", descriptionDE='" + getDescriptionDE() + "'" +
            ", implementationEN='" + getImplementationEN() + "'" +
            ", implementationDE='" + getImplementationDE() + "'" +
            ", validationEN='" + getValidationEN() + "'" +
            ", validationDE='" + getValidationDE() + "'" +
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
