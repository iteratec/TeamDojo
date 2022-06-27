<!-- 
SPDX-FileCopyrightText: the TeamDojo authors

SPDX-License-Identifier: Apache-2.0
-->
# JDL Modell Changes from v1 to v2

This list describes the changes from the v1 data model in the database to v2 (column renames, new columns etc.).

**NOTE**: Here we use the camelCase notation from the JDL. In the DDL all identifiers are mapped to snake_case.

- Activity
    - not required: type
    - new: updatedAt 
- Badge* 
    - name &rarr; title
    - new: createdAt
    - new: updatedAt 
- BadgeSkill*
- Comment
    - creationDate &rarr; createdAt
    - new: updatedAt
- Dimension*
    - name &rarr; title
    - new: createdAt
    - new: updatedAt
- Image*
    - name &rarr; title
    - new: createdAt
    - new: updatedAt
- Level*
    - name &rarr; title
    - new: createdAt
    - new: updatedAt
- LevelSkill*
- Organization* &rarr; Organisation (removed in favor of TeamGroup)
    - name &rarr; title
    - new: description
    - new: createdAt
    - new: updatedAt
    - removed: userMode
    - removed: mattermostUrl
- Report
    - required: title
    - creationDate &rarr; createdAt
    - new: updatedAt
- Skill*
    - required: rateCount
    - new: createdAt
    - new: updatedAt
- Team*
    - name &rarr; title
    - shortName &rarr; shortTitle
    - validation `pattern(/^[a-zA-Z0-9_-]*$/)`: shortTitle
    - contactPerson &rarr; contact
    - new: createdAt
    - new: updatedAt
- new TeamGroup
  - title
  - description
  - createdAt
  - updatedAt
- TeamSkill*
    - new: skillStatus
    - new: createdAt
    - new: updatedAt
- Training
    - contactPerson &rarr; contact
    - new: createdAt
    - new: updatedAt
- new PersistentAuditEvent (only for persisting audit log in the Backend)
- new PersistentAuditEventData (only for persisting audit log in the Backend)
- new SkillStatus

<sup>*</sup>Used in `src/main/resources/config/liquibase/initial_demo_data.xml`
