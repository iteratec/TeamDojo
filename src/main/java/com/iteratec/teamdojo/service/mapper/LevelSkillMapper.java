package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LevelSkill} and its DTO {@link LevelSkillDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface LevelSkillMapper extends EntityMapper<LevelSkillDTO, LevelSkill> {
    @Mapping(target = "skill", source = "skill", qualifiedByName = "skillTitleEN")
    @Mapping(target = "level", source = "level", qualifiedByName = "levelTitleEN")
    LevelSkillDTO toDto(LevelSkill s);

    @Named("skillTitleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    SkillDTO toDtoSkillTitleEN(Skill skill);

    @Named("levelTitleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    LevelDTO toDtoLevelTitleEN(Level level);
}
