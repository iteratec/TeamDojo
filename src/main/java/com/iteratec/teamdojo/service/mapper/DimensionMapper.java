package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dimension} and its DTO {@link DimensionDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface DimensionMapper extends EntityMapper<DimensionDTO, Dimension> {}
