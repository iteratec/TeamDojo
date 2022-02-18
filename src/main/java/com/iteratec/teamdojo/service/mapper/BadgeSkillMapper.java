package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BadgeSkill} and its DTO {@link BadgeSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = { BadgeMapper.class, SkillMapper.class })
public interface BadgeSkillMapper extends EntityMapper<BadgeSkillDTO, BadgeSkill> {
    @Mapping(target = "badge", source = "badge", qualifiedByName = "title")
    @Mapping(target = "skill", source = "skill", qualifiedByName = "title")
    BadgeSkillDTO toDto(BadgeSkill s);
}
