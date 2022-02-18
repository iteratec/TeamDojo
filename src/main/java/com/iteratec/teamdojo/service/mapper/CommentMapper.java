package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.domain.Comment;
import com.iteratec.teamdojo.service.dto.CommentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring", uses = { TeamMapper.class, SkillMapper.class })
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "team", source = "team", qualifiedByName = "shortTitle")
    @Mapping(target = "skill", source = "skill", qualifiedByName = "title")
    CommentDTO toDto(Comment s);
}
