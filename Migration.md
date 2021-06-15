# Migration from v1 to v2

This document stores some hints andnotes about our migration path.

## JHipster Baseline

To see what we changed in the generated code in v1 we need a baseline: Generated JHipster (5.8.1) app with v1 JDL:

1. Install the old JHipster version for v1: `npm install -g generator-jhipster@5.8.1`.
2. Create a dir for the baseline.
3. Copy `.yo-rc.json` and `teamDojo_v1.jdl` into this directory.
4. Generate the app: `jhipster app`
5. Generate the JDL: `jhipster import-jdl teamDojo_v1.jdl`

## Some Conventions

- Name of own extensions of generated classes start with `Extended`, eg. `ExtendedFooRepository`.
- Own extended classes are stored in a subpackage `ext`.
- Other extensions (e.g. from Spring) start with `Custom`, e.g. `CustomAuditReposiotry`.

## Gradle Stuff

- To download JavaDoc and Sources run `gradle cleanIdea idea`. Module added as described at [Stack Overflow](https://stackoverflow.com/questions/28404149/how-to-download-javadocs-and-sources-for-jar-using-gradle-2-0/33653146#33653146).

## Refactorings from v1 to v2

We changed some things in the JDL:

- "Organization" -> "Organisation"
- removed Mattermost stuff
- in some entities the property `name` was changed to `title`

## TODO

1. `com/iteratec/teamdojo/repository/CustomAuditEventRepository.java` (dependencies Constants, `config.audit.AuditEventConverter`, `domain.PersistentAuditEvent`) omitted because `PersistentAuditEvent` is not present inside the JDL. This means there is no liquibase entry therefore no database table, which presumably leads to the integration tests failing.
2. `com.iteratec.teamdojo.web.rest.ImageResourceIT`: We disabled two tests because the image scaling does not produce the expected fixture from v1
3. added the PaginationUtil class from V1 (see ExtendedBadgeResource -> getAllBadgesBySkills). JHipster also has a PaginationUtil package, however using the method needed in getAllBadgeBySkills results in a compiler error due to different method signatures -> we marked customPaginationUtil deprecated -> remove it.
4. remove deprecated `CustomHeaderUtil`
5. `ServerInfoResource` can't be migrated due to missing `ServerInfoDTO` class
6. check if `CustomAchievableSkillService` + `CustomTeamAchievableSkillResource` should be generated
7. add todos from diff script
8. Migrate TeamAchievableSkillResourceIntTest from V1

## Random Note Stash

### @Transaction Tag in ExtendedServiceImpl Classes

- check if it is necessary to add the @Transaction Annotation to each ExtendedServiceImpl class
  - If I understand [this SO answer](https://stackoverflow.com/questions/9918594/spring-transactional-inheritance-rules) right it is sufficient to annotate the super class.

### Notes regarding migrated controller classes

- ignored CommentResource, DimensionResource, LevelSkillResource because the only difference consists of the following: `return ResponseEntity.ok().headers(headers).body(page.getContent());` -> `return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);`.
  - ignored because it is the same (fluent API vs constructor).

### Test Code

- in v1 the generated Integration tests have the class name suffix `IntTes`, in v2 they have the suffix `IT`
- some test classes are missing in v2:
  - `de.otto.teamdojo.repository.CustomAuditEventRepositoryIntTest`
  - `de.otto.teamdojo.repository.SkillRepositoryIntTest`
  - `de.otto.teamdojo.security.DomainUserDetailsServiceIntTest`
  - `de.otto.teamdojo.test.util.*`
    - I added them to v2
    - everywhere they were used the generated test code was customized
  - `de.otto.teamdojo.web.rest.util.SkillStatusDeterminationUnitTest`
  - `de.otto.teamdojo.web.rest.AuditResourceIntTest`
  - `de.otto.teamdojo.web.rest.LogsResourceIntTest`
  - `de.otto.teamdojo.web.rest.TeamAchievableSkillResourceIntTest`
