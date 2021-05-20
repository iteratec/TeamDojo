2a3
> import de.otto.teamdojo.service.OrganizationService;
4d4
< import de.otto.teamdojo.domain.enumeration.UserMode;
6d5
< import de.otto.teamdojo.service.OrganizationService;
10a10
> 
32,33d31
<     static final String DEFAULT_ORGANIZATION_NAME = "Organization";
< 
89,117c87
<         log.debug("Request to delete Organization : {}", id);
<         organizationRepository.deleteById(id);
<     }
< 
<     @Override
<     public OrganizationDTO getCurrentOrganization() {
<         List<OrganizationDTO> organizations = findAll();
<         if (organizations.isEmpty()) {
<             return getDefaultOrganization();
<         } else {
<             if (organizations.size() > 1) {
<                 log.warn("There exists more than one organization");
<             }
< 
<             OrganizationDTO organizationDTO = organizations.get(0);
<             if (organizationDTO.getCountOfConfirmations() == null) {
<                 organizationDTO.setCountOfConfirmations(0);
<             }
< 
<             return organizationDTO;
<         }
<     }
< 
<     private OrganizationDTO getDefaultOrganization() {
<         OrganizationDTO organization = new OrganizationDTO();
<         organization.setName(DEFAULT_ORGANIZATION_NAME);
<         organization.setUserMode(UserMode.TEAM);
<         organization.setCountOfConfirmations(0);
<         return organization;
---
>         log.debug("Request to delete Organization : {}", id);        organizationRepository.deleteById(id);
