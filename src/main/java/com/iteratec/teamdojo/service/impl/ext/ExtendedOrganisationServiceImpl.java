package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.domain.enumeration.ApplicationMode;
import com.iteratec.teamdojo.repository.OrganisationRepository;
import com.iteratec.teamdojo.service.dto.OrganisationDTO;
import com.iteratec.teamdojo.service.ext.ExtendedOrganisationService;
import com.iteratec.teamdojo.service.impl.OrganisationServiceImpl;
import com.iteratec.teamdojo.service.mapper.OrganisationMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ExtendedOrganisationServiceImpl extends OrganisationServiceImpl implements ExtendedOrganisationService {

    private final Logger log = LoggerFactory.getLogger(ExtendedOrganisationServiceImpl.class);

    static final String DEFAULT_ORGANISATION_NAME = "Organization";

    public ExtendedOrganisationServiceImpl(OrganisationRepository organisationRepository, OrganisationMapper organisationMapper) {
        super(organisationRepository, organisationMapper);
    }

    @Override
    public OrganisationDTO getCurrentOrganisation() {
        List<OrganisationDTO> organisations = findAll();
        if (organisations.isEmpty()) {
            return getDefaultOrganization();
        } else {
            if (organisations.size() > 1) {
                log.warn("There exists more than one organization");
            }

            OrganisationDTO organisationDTO = organisations.get(0);
            if (organisationDTO.getCountOfConfirmations() == null) {
                organisationDTO.setCountOfConfirmations(0);
            }

            return organisationDTO;
        }
    }

    private OrganisationDTO getDefaultOrganization() {
        OrganisationDTO organisation = new OrganisationDTO();
        organisation.setTitle(DEFAULT_ORGANISATION_NAME);
        organisation.setApplicationMode(ApplicationMode.TEAM);
        organisation.setCountOfConfirmations(0);
        return organisation;
    }
}
