112,124d111
< 
<     /**
<      * GET  /organization : get the only organization - assuming, there is only one.
<      *
<      * @return the ResponseEntity with status 200 (OK) and the organization in body
<      */
<     @GetMapping("/organizations/current")
<     public OrganizationDTO getCurrentOrganization() {
<         log.debug("REST request to get current Organizations");
<         return organizationService.getCurrentOrganization();
<     }
< 
< 
