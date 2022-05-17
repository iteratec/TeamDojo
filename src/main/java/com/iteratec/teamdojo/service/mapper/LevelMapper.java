package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Level} and its DTO {@link LevelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ImageMapper.class, DimensionMapper.class })
@GeneratedByJHipster
public interface LevelMapper extends EntityMapper<LevelDTO, Level> {
    @Mapping(target = "dependsOn", source = "dependsOn", qualifiedByName = "titleEN")
    @Mapping(target = "image", source = "image", qualifiedByName = "title")
    @Mapping(target = "dimension", source = "dimension", qualifiedByName = "titleEN")
    LevelDTO toDto(Level s);

    @Named("titleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    LevelDTO toDtoTitleEN(Level level);
}
