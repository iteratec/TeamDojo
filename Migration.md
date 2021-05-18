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
- Own extended classes are storedin a subpackage `ext`.
- Other extensions (eg. from Spring) start with `Custom`, eg. `CustomAuditReposiotry`.

## Random Note Stash

### Classes which still need to be added to migrate all V1 Repository features

- ExtendedImageRepository (somehow causes integrations tests to fail)
- com/iteratec/teamdojo/repository/CustomAuditEventRepository.java (dependencies Constants,
  config.audit.AuditEventConverter, domain.PersistentAuditEvent)

---
