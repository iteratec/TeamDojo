# TeamDojo

An application for improving (application and project) skills of your teams through gamification.

It allows teams to self assess their skills and checks if they are reaching a specified ability level.
If they reach a new ability level, they will be rewarded with a cool new Team Avatar, Level Rewards -
like a virtual belt - and topic specific Badges.
TeamDojo also calculates scores, based on specific skill, level and badge ranking/difficulty and ranks the teams by
the amount of their achieved scores.

![screencast](screencast.gif 'Screencast')

## Usage

### Cloning

    git clone https://github.com/otto-de/TeamDojo.git
    cd TeamDojo/

### Start the database

    docker-compose -f src/main/docker/postgresql.yml up

### Start the server

    ./gradlew

The application will be available at [http://localhost:8080](http://localhost:8080)

### Default secrets / credentials

The default admin credentials are: **admin/teamdojo**, configured in [src/main/resources/config/liquibase/users.csv](src/main/resources/config/liquibase/users.csv).

The secret for the "Remember me" Cookie is configured in [src/main/resources/config/application-prod.yml](src/main/resources/config/application-prod.yml).

Please change the password and secret in your production environment.

## Development

[Here](DEVELOPMENT.md) you can find the dev documentation.

## Further Development (Status determination feature)

Determining the status of a skill acquired by a Team was reworked.

A combination of data from the Skill and Teamskill entities are used in the determination of the status.

The fields used in calculating the status are "irrelevant" and "completedAt" (From the TeamSkill Entity) and "expiryPeriod" (from the Skill Entity).

In the context of a given Team, a skill is in the

- Status `OPEN`: If the TeamSkill.completedAt field is not set (that is null) and TeamSkill.irrelevant is not true

- Status `IRRELEVANT`: If the TeamSkill.irrelevant field is set to true, notwithstanding the values of the other fields.

- Status `ACHIEVED`: If the TeamSkill.completedAt field is set and the TeamSkill.irrelevant is not true and the TeamSkill.completedAt
  plus the SKill.expiryPeriod (in days) is later than the current date.
- Status `EXPIRING`: If the TeamSkill.completedAt field is set and the TeamSkill.irrelevant is not true, the current date
  is later than the TeamSkill.completedAt plus the SKill.expiryPeriod (in days) but not later than a
  7-days grace period.
- Status `EXPIRED`: If the TeamSkill.completedAt field is set and the TeamSkill.irrelevant is not true, the current date
  is later than the TeamSkill.completedAt plus the SKill.expiryPeriod (in days) and the 7-days grace period
  is past.

The corresponding skill status for a team is determined and set in the AchievableSkillDTO and passed as part of the
JSON string on to the frontend.

At the frontend, the skillstatus information in the JSON-string from the backend is deserialized into a typescript object (SkillStatus enum in the
IAchievableSkill Typescript type).

The Skillstatus information at the frontend is used to calculate the number of teams that have acquired a given skill, to display the status
of a skill for a given team, to toggle status from one state to another.
