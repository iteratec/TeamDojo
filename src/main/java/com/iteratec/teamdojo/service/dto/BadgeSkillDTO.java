package com.iteratec.teamdojo.service.dto;

import com.iteratec.teamdojo.GeneratedByJHipster;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.BadgeSkill} entity.
 */
@Schema(description = "Lookup table entity for N-to-M relationships")
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class BadgeSkillDTO implements Serializable {

    private Long id;

    private BadgeDTO badge;

    private SkillDTO skill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BadgeDTO getBadge() {
        return badge;
    }

    public void setBadge(BadgeDTO badge) {
        this.badge = badge;
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
        if (!(o instanceof BadgeSkillDTO)) {
            return false;
        }

        BadgeSkillDTO badgeSkillDTO = (BadgeSkillDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, badgeSkillDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgeSkillDTO{" +
            "id=" + getId() +
            ", badge=" + getBadge() +
            ", skill=" + getSkill() +
            "}";
    }
}
