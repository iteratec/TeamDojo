package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Organisation;
import com.iteratec.teamdojo.service.dto.OrganisationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organisation} and its DTO {@link OrganisationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
@GeneratedByJHipster
public interface OrganisationMapper extends EntityMapper<OrganisationDTO, Organisation> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "id")
    OrganisationDTO toDto(Organisation s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganisationDTO toDtoId(Organisation organisation);
}
