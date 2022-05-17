package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dimension} and its DTO {@link DimensionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
@GeneratedByJHipster
public interface DimensionMapper extends EntityMapper<DimensionDTO, Dimension> {
    @Named("titleENSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    Set<DimensionDTO> toDtoTitleENSet(Set<Dimension> dimension);

    @Named("titleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    DimensionDTO toDtoTitleEN(Dimension dimension);
}
