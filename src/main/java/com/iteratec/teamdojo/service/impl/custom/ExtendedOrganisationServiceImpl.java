package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Organisation;
import com.iteratec.teamdojo.repository.OrganisationRepository;
import com.iteratec.teamdojo.service.custom.ExtendedOrganisationService;
import com.iteratec.teamdojo.service.dto.OrganisationDTO;
import com.iteratec.teamdojo.service.impl.OrganisationServiceImpl;
import com.iteratec.teamdojo.service.mapper.OrganisationMapper;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class ExtendedOrganisationServiceImpl extends OrganisationServiceImpl implements ExtendedOrganisationService {

    static final String DEFAULT_ORGANISATION_NAME = "Organization";
    private final AuditableDataTracker<OrganisationDTO, Organisation> tracker;

    public ExtendedOrganisationServiceImpl(final OrganisationRepository repo, final OrganisationMapper mapper) {
        super(repo, mapper);
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    /**
     * Injection point for instant provider
     * <p>
     * This is necessary because time is a side effect and we need to mock out the default implementation for tests.
     * </p>
     *
     * @param time not {@code null}
     */
    void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
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
        organisation.setCountOfConfirmations(0);
        return organisation;
    }

    @Override
    public OrganisationDTO save(final OrganisationDTO organisation) {
        tracker.modifyCreatedAndUpdatedAt(organisation);
        return super.save(organisation);
    }
}
