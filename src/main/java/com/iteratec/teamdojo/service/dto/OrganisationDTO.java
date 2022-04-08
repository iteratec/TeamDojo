package com.iteratec.teamdojo.service.dto;

import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Organisation} entity.
 */
@GeneratedByJHipster
// ### MODIFICATION-START ###
public class OrganisationDTO implements Serializable, com.iteratec.teamdojo.service.dto.custom.AuditableData {

    // ### MODIFICATION-END ###

    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    @Size(max = 4096)
    private String description;

    private Integer levelUpScore;

    @Min(value = 0)
    private Integer countOfConfirmations;

    // ### MODIFICATION-START ###
    private Instant createdAt;
    // ### MODIFICATION-END ###

    // ### MODIFICATION-START ###
    private Instant updatedAt;
    // ### MODIFICATION-END ###

    private OrganisationDTO parent;

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

    public Integer getLevelUpScore() {
        return levelUpScore;
    }

    public void setLevelUpScore(Integer levelUpScore) {
        this.levelUpScore = levelUpScore;
    }

    public Integer getCountOfConfirmations() {
        return countOfConfirmations;
    }

    public void setCountOfConfirmations(Integer countOfConfirmations) {
        this.countOfConfirmations = countOfConfirmations;
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

    public OrganisationDTO getParent() {
        return parent;
    }

    public void setParent(OrganisationDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationDTO)) {
            return false;
        }

        OrganisationDTO organisationDTO = (OrganisationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organisationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganisationDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", levelUpScore=" + getLevelUpScore() +
            ", countOfConfirmations=" + getCountOfConfirmations() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
