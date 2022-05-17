package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Badge} and its DTO {@link BadgeDTO}.
 */
@Mapper(componentModel = "spring", uses = { ImageMapper.class, DimensionMapper.class })
@GeneratedByJHipster
public interface BadgeMapper extends EntityMapper<BadgeDTO, Badge> {
    @Mapping(target = "image", source = "image", qualifiedByName = "title")
    @Mapping(target = "dimensions", source = "dimensions", qualifiedByName = "titleENSet")
    BadgeDTO toDto(Badge s);

    @Mapping(target = "removeDimensions", ignore = true)
    Badge toEntity(BadgeDTO badgeDTO);

    @Named("titleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    BadgeDTO toDtoTitleEN(Badge badge);
}
