<!-- 
SPDX-FileCopyrightText: the TeamDojo authors

SPDX-License-Identifier: Apache-2.0
-->
### Pre-Defined Roles:

* `ROLE_ADMIN`
   * Allow: Create, Read, Update, Delete - on all Entities:
   * Allow: View all Administration UIs referenced in navigation bar:
     * Entities
     * Administration 
* `ROLE_USER`
   * Allow: Create, Read, Update, Delete - on Entities:
     * BadgeSkill
     * Image
     * Report
     * TeamSkill
     * Training
   * Allow: Create, Read / Disallow: Update, Delete - on Entities:
     * Activity
     * Badge
     * Comment
     * Level
     * Skill
     * TeamGroup
     * Team
   * Allow: View TeamDojo UI in general
   * Disallow: View all Administration UIs referenced in navigation bar:
     * Entities
     * Administration 
* `ROLE_ANONYMOUS`
  * Disallow: Create, Read, Update, Delete - on all Entities
  * Allow: View UI in general

### Role Definition Implementation:
* `src/main/webapp/app/config/authority.constants.ts` (frontend)
* `src/main/java/com/iteratec/teamdojo/security/AuthoritiesConstants.java` (backend)

### Role Configurations:

#### Level 0: Authentication
* `src/main/java/com/iteratec/teamdojo/security/SecurityUtils.java`

#### Level 1: Path Restrictions based, on Autorization Roles
General Backend Path Restrictions to specific roles:
* see `src/main/java/com/iteratec/teamdojo/config/SecurityConfiguration.java`
```java
    // line 92...
        .and()
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/auth-info").permitAll()
            .antMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/health/**").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
```

#### Level 2: RestAPI Method Restrictions, based on Autorization Roles
Implemented by annotation. e.g. in `src/main/java/com/iteratec/teamdojo/web/rest/ActivityResource.java` (line 43):
```java
/**
     * {@code DELETE  /activities/:id} : delete the "id" activity.
     *
     * @param id the id of the activityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // ### MODIFICATION-START ###
    @Secured(AuthoritiesConstants.ADMIN)
    // ### MODIFICATION-END ###
    @DeleteMapping("/activities/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        log.debug("REST request to delete Activity : {}", id);
        activityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
```

#### Level 3: UI visual Restrictions, based on Autorization Roles
* by directive: `*jhiHasAnyAuthority="'ROLE_ADMIN'"`
* by method: `this.hasAuthority = this.accountService.hasAnyAuthority(ROLES_ALLOWED_TO_UPDATE);`
