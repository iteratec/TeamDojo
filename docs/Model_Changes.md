# JDL Modell Changes from v1 to v2

This list describes the changes from the v1 data mnodel in the database to v2 (column renames, new columns etc.).:

- badge* 
    - name &rarr; title
    - new: createdAt
    - new: updatedAt 
- badge_skill*
- comment
    - new: createdAt
    - new: updatedAt
- dimension*
    - name &rarr; title
    - new: createdAt
    - new: updatedAt
- image*
    - name &rarr; title
    - new: createdAt
    - new: updatedAt
- level*
    - name &rarr; title
    - new: createdAt
    - new: updatedAt
- level_skill*
- organization* -> organisation
    - name &rarr; title
    - new: description
    - new: applicationMode
    - new: createdAt
    - new: updatedAt
    - removed: userMode
    - removed: 
- report
    - creationDate -> createdAt
    - new: updatedAt
- skill*
    - new: createdAt
    - new: updatedAt
- team*
    - name &rarr; title
    - shortName &rarr; shortTitle
    - contactPerson &arr; contact
    - new: createdAt
    - new: updatedAt
- team_skill*
    - new: createdAt
    - new: updatedAt
- training
    - contactPerson &rarr; contact
    - new: createdAt
    - new: updatedAt

<sup>*</sup>Used in `src/main/resources/config/liquibase/initial_demo_data.xml`
