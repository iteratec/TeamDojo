package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.TeamSkill;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamSkill} and its DTO {@link TeamSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = { SkillMapper.class, TeamMapper.class })
@GeneratedByJHipster
public interface TeamSkillMapper extends EntityMapper<TeamSkillDTO, TeamSkill> {
    @Mapping(target = "skill", source = "skill", qualifiedByName = "titleEN")
    @Mapping(target = "team", source = "team", qualifiedByName = "title")
    TeamSkillDTO toDto(TeamSkill s);
}
