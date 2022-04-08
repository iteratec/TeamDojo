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
    @Mapping(target = "dependsOn", source = "dependsOn", qualifiedByName = "title")
    @Mapping(target = "image", source = "image", qualifiedByName = "title")
    @Mapping(target = "dimension", source = "dimension", qualifiedByName = "title")
    LevelDTO toDto(Level s);

    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    LevelDTO toDtoTitle(Level level);
}
