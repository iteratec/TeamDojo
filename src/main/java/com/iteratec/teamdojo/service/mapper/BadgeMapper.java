package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Badge} and its DTO {@link BadgeDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface BadgeMapper extends EntityMapper<BadgeDTO, Badge> {
    @Mapping(target = "image", source = "image", qualifiedByName = "imageTitle")
    @Mapping(target = "dimensions", source = "dimensions", qualifiedByName = "dimensionTitleENSet")
    BadgeDTO toDto(Badge s);

    @Mapping(target = "removeDimensions", ignore = true)
    Badge toEntity(BadgeDTO badgeDTO);

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
}
