package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LevelSkill} and its DTO {@link LevelSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = { SkillMapper.class, LevelMapper.class })
@GeneratedByJHipster
public interface LevelSkillMapper extends EntityMapper<LevelSkillDTO, LevelSkill> {
    @Mapping(target = "skill", source = "skill", qualifiedByName = "titleEN")
    @Mapping(target = "level", source = "level", qualifiedByName = "titleEN")
    LevelSkillDTO toDto(LevelSkill s);
}
