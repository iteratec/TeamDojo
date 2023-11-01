package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.TeamGroup;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.dto.TeamGroupDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {
    @Mapping(target = "image", source = "image", qualifiedByName = "imageTitle")
    @Mapping(target = "participations", source = "participations", qualifiedByName = "dimensionTitleENSet")
    @Mapping(target = "group", source = "group", qualifiedByName = "teamGroupTitle")
    TeamDTO toDto(Team s);

    @Mapping(target = "removeParticipations", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    @Named("imageTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    ImageDTO toDtoImageTitle(Image image);

    @Named("dimensionTitleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    DimensionDTO toDtoDimensionTitleEN(Dimension dimension);

    @Named("dimensionTitleENSet")
    default Set<DimensionDTO> toDtoDimensionTitleENSet(Set<Dimension> dimension) {
        return dimension.stream().map(this::toDtoDimensionTitleEN).collect(Collectors.toSet());
    }

// ### MODIFICATION-START ###
    @Named("teamGroupTitle")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    TeamGroupDTO toDtoTeamGroupTitle(TeamGroup teamGroup);
// ### MODIFICATION-END ###
}
