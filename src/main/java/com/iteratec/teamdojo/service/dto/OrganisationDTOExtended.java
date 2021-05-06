package com.iteratec.teamdojo.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Organisation} entity.
 */
@ApiModel(description = "This is an Level\n@author Robert Seedorff")
public class OrganisationDTOExtended extends OrganisationDTO {

    @Size(max = 255)
    @Pattern(regexp = "^(?:http(s)?://)?[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:/?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=.]+$")
    private String mattermostUrl;

    public String getMattermostUrl() {
        return mattermostUrl;
    }

    public void setMattermostUrl(String mattermostUrl) {
        this.mattermostUrl = mattermostUrl;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganisationDTOExtended{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", levelUpScore=" + getLevelUpScore() +
            ", applicationMode='" + getApplicationMode() + "'" +
            ", countOfConfirmations=" + getCountOfConfirmations() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", parent=" + getParent() +
            ", mattermosturl=" + getMattermostUrl() +
            "}";
    }
}
