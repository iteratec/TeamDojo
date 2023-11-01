package com.iteratec.teamdojo.service.dto;

import com.iteratec.teamdojo.GeneratedByJHipster;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.TeamGroup} entity.
 */
@Schema(
    description = "A team group is a hierarchical construct to organize teams within a large organization into departments to separate\ndifferent teams more easily based on their organizational structure."
)
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
// ### MODIFICATION-START ###
public class TeamGroupDTO implements Serializable, com.iteratec.teamdojo.service.dto.custom.AuditableData {

// ### MODIFICATION-END ###
    private Long id;

    @NotNull
    @Size(max = 80)
    private String title;

    @Size(max = 4096)
    private String description;

    // ### MODIFICATION-START ###
    private Instant createdAt;
// ### MODIFICATION-END ###

    // ### MODIFICATION-START ###
    private Instant updatedAt;

    // ### MODIFICATION-END ###

    private TeamGroupDTO parent;

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

    public TeamGroupDTO getParent() {
        return parent;
    }

    public void setParent(TeamGroupDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamGroupDTO)) {
            return false;
        }

        TeamGroupDTO teamGroupDTO = (TeamGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamGroupDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
