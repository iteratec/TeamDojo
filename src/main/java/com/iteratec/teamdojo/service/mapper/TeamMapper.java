package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring", uses = { ImageMapper.class, DimensionMapper.class, TeamGroupMapper.class })
@GeneratedByJHipster
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {
    @Mapping(target = "image", source = "image", qualifiedByName = "title")
    @Mapping(target = "participations", source = "participations", qualifiedByName = "titleENSet")
    // ### MODIFICATION-START ###
    @Mapping(target = "group", source = "group")
    // ### MODIFICATION-END ###
    TeamDTO toDto(Team s);

    @Mapping(target = "removeParticipations", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    @Named("shortTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "shortTitle", source = "shortTitle")
    TeamDTO toDtoShortTitle(Team team);

    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    TeamDTO toDtoTitle(Team team);
}
