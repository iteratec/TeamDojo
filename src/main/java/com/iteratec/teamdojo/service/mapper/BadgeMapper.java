package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.domain.*;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Badge} and its DTO {@link BadgeDTO}.
 */
@Mapper(componentModel = "spring", uses = { ImageMapper.class, DimensionMapper.class })
public interface BadgeMapper extends EntityMapper<BadgeDTO, Badge> {
    @Mapping(target = "image", source = "image", qualifiedByName = "title")
    @Mapping(target = "dimensions", source = "dimensions", qualifiedByName = "titleSet")
    BadgeDTO toDto(Badge s);

    @Mapping(target = "removeDimensions", ignore = true)
    Badge toEntity(BadgeDTO badgeDTO);

    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    BadgeDTO toDtoTitle(Badge badge);
}
