# Migration from v1 to v2

This document stores some hints andnotes about our migration path.

## JHipster

Install the old JHipster version for v1: ` npm install -g generator-jhipster@5.8.1`

## Some Conventions

- Name of own extensions of generated classes start with `Extended`, eg. `ExtendedFooRepository`.
- Own extended classes are storedin a subpackage `ext`.
- Other extensions (eg. from Spring) styart with `Custom`, eg. `CustomAuditReposiotry`.

## Random Note Stash

### Classes which still need to be added to migrate all V1 Repository features

- ExtendedImageRepository (somehow causes integrations tests to fail)
- SkillRepository still needs to be extended
- com/iteratec/teamdojo/repository/CustomAuditEventRepository.java
- com/iteratec/teamdojo/repository/ExtendedSkillRepository.java
- com/iteratec/teamdojo/repository/TeamSkillRepository.java

#### Dependencies

- src/main/java/com/iteratec/teamdojo/domain/enumeration/SkillStatus.java
- src/main/java/com/iteratec/teamdojo/service/dto/AchievableSkillDTO.java

---
