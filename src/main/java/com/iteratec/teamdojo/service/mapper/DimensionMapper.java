package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.domain.*;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dimension} and its DTO {@link DimensionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DimensionMapper extends EntityMapper<DimensionDTO, Dimension> {
    @Named("titleSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    Set<DimensionDTO> toDtoTitleSet(Set<Dimension> dimension);

    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    DimensionDTO toDtoTitle(Dimension dimension);
}
