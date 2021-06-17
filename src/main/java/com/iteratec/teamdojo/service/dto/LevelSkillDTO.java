package com.iteratec.teamdojo.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.LevelSkill} entity.
 */
@ApiModel(description = "Lookup table entity for N-to-M relationships.")
public class LevelSkillDTO implements Serializable {

    private Long id;

    private SkillDTO skill;

    private LevelDTO level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SkillDTO getSkill() {
        return skill;
    }

    public void setSkill(SkillDTO skill) {
        this.skill = skill;
    }

    public LevelDTO getLevel() {
        return level;
    }

    public void setLevel(LevelDTO level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelSkillDTO)) {
            return false;
        }

        LevelSkillDTO levelSkillDTO = (LevelSkillDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, levelSkillDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelSkillDTO{" +
            "id=" + getId() +
            ", skill=" + getSkill() +
            ", level=" + getLevel() +
            "}";
    }
}
