package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Level} and its DTO {@link LevelDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface LevelMapper extends EntityMapper<LevelDTO, Level> {
    @Mapping(target = "dependsOn", source = "dependsOn", qualifiedByName = "levelTitleEN")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageTitle")
    @Mapping(target = "dimension", source = "dimension", qualifiedByName = "dimensionTitleEN")
    LevelDTO toDto(Level s);

    @Named("levelTitleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    LevelDTO toDtoLevelTitleEN(Level level);

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
}
