package com.iteratec.teamdojo.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Comment} entity.
 */
@ApiModel(description = "This is a Comment\n@author Robert Seedorff")
public class CommentDTO implements Serializable {

    private Long id;

    @NotNull
    private String text;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    private TeamDTO team;

    private SkillDTO skill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    public SkillDTO getSkill() {
        return skill;
    }

    public void setSkill(SkillDTO skill) {
        this.skill = skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentDTO)) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", team=" + getTeam() +
            ", skill=" + getSkill() +
            "}";
    }
}
