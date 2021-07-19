package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.enumeration.ApplicationMode;
import com.iteratec.teamdojo.repository.OrganisationRepository;
import com.iteratec.teamdojo.service.custom.ExtendedOrganisationService;
import com.iteratec.teamdojo.service.dto.OrganisationDTO;
import com.iteratec.teamdojo.service.impl.OrganisationServiceImpl;
import com.iteratec.teamdojo.service.mapper.OrganisationMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class ExtendedOrganisationServiceImpl extends OrganisationServiceImpl implements ExtendedOrganisationService {

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
