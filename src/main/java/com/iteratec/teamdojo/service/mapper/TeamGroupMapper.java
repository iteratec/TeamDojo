package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.domain.TeamGroup;
import com.iteratec.teamdojo.service.dto.TeamGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamGroup} and its DTO {@link TeamGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeamGroupMapper extends EntityMapper<TeamGroupDTO, TeamGroup> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "id")
    TeamGroupDTO toDto(TeamGroup s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamGroupDTO toDtoId(TeamGroup teamGroup);

    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    TeamGroupDTO toDtoTitle(TeamGroup teamGroup);
}
