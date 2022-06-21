package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Comment;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.service.dto.CommentDTO;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "team", source = "team", qualifiedByName = "teamShortTitle")
    @Mapping(target = "skill", source = "skill", qualifiedByName = "skillTitleEN")
    CommentDTO toDto(Comment s);

    @Named("teamShortTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "shortTitle", source = "shortTitle")
    TeamDTO toDtoTeamShortTitle(Team team);

    @Named("skillTitleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    SkillDTO toDtoSkillTitleEN(Skill skill);
}
