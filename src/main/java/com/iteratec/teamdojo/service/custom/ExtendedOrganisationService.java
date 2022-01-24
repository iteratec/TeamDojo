package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.OrganisationService;
import com.iteratec.teamdojo.service.dto.OrganisationDTO;
import com.iteratec.teamdojo.service.impl.custom.InstantProviderInjectable;

public interface ExtendedOrganisationService extends OrganisationService, InstantProviderInjectable {
    /**
     * Get the only organization - assuming, there is only one.
     *
     * @return
     */
    OrganisationDTO getCurrentOrganisation();
}
