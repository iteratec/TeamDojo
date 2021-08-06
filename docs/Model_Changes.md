# JDL Modell Changes from v1 to v2

This list describes the changes from the v1 data model in the database to v2 (column renames, new columns etc.).

**NOTE**: Here we use the camelCase notation from the JDL. In the DDL all identifiers are mapped to snake_case.

- Badge* 
    - name &rarr; title
    - new: createdAt
    - new: updatedAt 
- BadgeSkill*
- Comment
    - new: createdAt
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
- Organization* -> Organisation
    - name &rarr; title
    - new: description
    - new: applicationMode
    - new: createdAt
    - new: updatedAt
    - removed: userMode
    - removed: mattermostUrl
- Report
    - creationDate -> createdAt
    - new: updatedAt
- Skill*
    - new: createdAt
    - new: updatedAt
- Team*
    - name &rarr; title
    - shortName &rarr; shortTitle
    - contactPerson &arr; contact
    - new: createdAt
    - new: updatedAt
- TeamSkill*
    - new: createdAt
    - new: updatedAt
- Training
    - contactPerson &rarr; contact
    - new: createdAt
    - new: updatedAt

<sup>*</sup>Used in `src/main/resources/config/liquibase/initial_demo_data.xml`
