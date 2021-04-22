package com.iteratec.teamdojo.service.dto;

import com.iteratec.teamdojo.domain.enumeration.ApplicationMode;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Organisation} entity.
 */
@ApiModel(description = "This is an Level\n@author Robert Seedorff")
public class OrganisationDTOExtended implements Serializable {

    private Long id;

    @Size(max = 255)
    @Pattern(regexp = "^(?:http(s)?://)?[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:/?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=.]+$")
    private String mattermostUrl;

    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    private String description;

    private Integer levelUpScore;

    @NotNull
    private ApplicationMode applicationMode;

    @Min(value = 0)
    private Integer countOfConfirmations;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

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

    public ApplicationMode getApplicationMode() {
        return applicationMode;
    }

    public void setApplicationMode(ApplicationMode applicationMode) {
        this.applicationMode = applicationMode;
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

    public String getMattermostUrl() {
        return mattermostUrl;
    }

    public void setMattermostUrl(String mattermostUrl) {
        this.mattermostUrl = mattermostUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationDTO)) {
            return false;
        }

        OrganisationDTOExtended organisationDTO = (OrganisationDTOExtended) o;
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
            ", applicationMode='" + getApplicationMode() + "'" +
            ", countOfConfirmations=" + getCountOfConfirmations() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
