package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.domain.*;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LevelSkill} and its DTO {@link LevelSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = { SkillMapper.class, LevelMapper.class })
public interface LevelSkillMapper extends EntityMapper<LevelSkillDTO, LevelSkill> {
    @Mapping(target = "skill", source = "skill", qualifiedByName = "title")
    @Mapping(target = "level", source = "level", qualifiedByName = "title")
    LevelSkillDTO toDto(LevelSkill s);
}
