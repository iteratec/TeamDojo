package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.OrganisationDTOExtended;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Organisation}.
 */
public interface OrganisationExtendedService extends OrganisationService {
    // get the only organization - assuming, there is only one.
    OrganisationDTOExtended getCurrentOrganization();
}
