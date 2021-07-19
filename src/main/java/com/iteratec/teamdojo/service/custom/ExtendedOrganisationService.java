package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.OrganisationService;
import com.iteratec.teamdojo.service.dto.OrganisationDTO;

public interface ExtendedOrganisationService extends OrganisationService {
    /**
     * Get the only organization - assuming, there is only one.
     *
     * @return
     */
    OrganisationDTO getCurrentOrganisation();
}
