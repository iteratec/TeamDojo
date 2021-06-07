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

## Random Note Stash

### Classes which still need to be added to migrate all V1 Repository features

- `com/iteratec/teamdojo/repository/CustomAuditEventRepository.java` (dependencies Constants, `config.audit.AuditEventConverter`, `domain.PersistentAuditEvent`) omitted because `PersistentAuditEvent` is not present inside the JDL. This means there is no liquibase entry therefore no database table, which presumably leads to the integration tests failing.

### Classes which still need to be added to migrate all V1 Service features

- `com/iteratec/teamdojo/service/ImageService` adds a throw NoSuchAlgorithm exception to the save function. Omitted because im not sure how to properly Override the method to make it throw this exception (Amar)

### @Transaction Tag in ExtendedServiceImpl Classes

- check if it is necessary to add the @Transaction Annotation to each ExtendedServiceImpl class

### @Primary Tag in ExtendedService Impl Classes

- problem with failing Tests in `ImageResourceIT` `createImage()` Assertion at Line 146, `putNewImage()` Assertion at Line 669. These failing tests can be fixed by removing the `@Primary`-Annotation from the `ExtendedImageServiceImpl` class and instead adding it to the ImageServiceImpl class. See `contrib/stacktraces` for more detailed information.
  - This has nothing to do with `@Primary`-Annotation: The problem is a NPE thrown in `com.iteratec.teamdojo.service.impl.ext.ExtendedImageServiceImpl.resize` line 100 because the passed in `img` is `null`. For unknown reason the method `com.iteratec.teamdojo.service.impl.ext.ExtendedImageServiceImpl.createImageFromBytes` returns `null` in line 56. I added a FIXME marker at the position.

### Util function

- added the PaginationUtil class from V1 (see ExtendedBadgeResource -> getAllBadgesBySkills). JHipster also has a PaginationUtisl package, however using the method needed in getAllBadgeBySkills results in a compiler error due to different method signatures

### Notes regarding migrated controller classes

- couldn't map ExtendedBadgeResource to the same url, changed mapping from /api -> /api/v2
- ignored CommentResource, DimensionResource, LevelSkillResource because the only difference consists of the following: `return ResponseEntity.ok().headers(headers).body(page.getContent());` -> `return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);`. Should be dicussed if this can be ignored.
- ignored addition of NoSuchAlgorithmException in ImageResource same as the reason given for ImageService
