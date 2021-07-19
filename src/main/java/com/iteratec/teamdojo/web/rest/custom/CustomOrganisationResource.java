package com.iteratec.teamdojo.web.rest.custom;

import com.iteratec.teamdojo.service.custom.ExtendedOrganisationService;
import com.iteratec.teamdojo.service.dto.OrganisationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class CustomOrganisationResource {

    private final ExtendedOrganisationService organisationService;

    public CustomOrganisationResource(ExtendedOrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    /**
     * GET  /organization : get the only organization - assuming, there is only one.
     *
     * @return the ResponseEntity with status 200 (OK) and the organization in body
     */
    @GetMapping("/organisations/current")
    public OrganisationDTO getCurrentOrganization() {
        log.debug("REST request to get current Organizations");
        return organisationService.getCurrentOrganisation();
    }
}
