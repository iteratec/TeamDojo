package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.OrganisationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Organisation}.
 */
public interface OrganisationExtendedService extends OrganisationService {
    // get the only organization - assuming, there is only one.
    OrganisationDTO getCurrentOrganization();
}
