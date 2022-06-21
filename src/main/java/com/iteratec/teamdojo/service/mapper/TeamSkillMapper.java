package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.TeamSkill;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamSkill} and its DTO {@link TeamSkillDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface TeamSkillMapper extends EntityMapper<TeamSkillDTO, TeamSkill> {
    @Mapping(target = "skill", source = "skill", qualifiedByName = "skillTitleEN")
    @Mapping(target = "team", source = "team", qualifiedByName = "teamTitle")
    TeamSkillDTO toDto(TeamSkill s);

    @Named("skillTitleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    SkillDTO toDtoSkillTitleEN(Skill skill);

    @Named("teamTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    TeamDTO toDtoTeamTitle(Team team);
}
