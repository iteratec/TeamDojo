1c1
< # TeamDojo
---
> # teamdojo
3c3
< An application for improving (application and project) skills of your teams through gamification.
---
> This application was generated using JHipster 5.8.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v5.8.1](https://www.jhipster.tech/documentation-archive/v5.8.1).
5,11c5
< It allows teams to self assess their skills and checks if they are reaching a specified ability level.
< If they reach a new ability level, they will be rewarded with a cool new Team Avatar, Level Rewards -
< like a virtual belt - and topic specific Badges.
< TeamDojo also calculates scores, based on specific skill, level and badge ranking/difficulty and ranks the teams by
< the amount of their achieved scores.
< 
< ![screencast](screencast.gif 'Screencast')
---
> ## Development
13c7
< ## Usage
---
> Before you can build this project, you must install and configure the following dependencies on your machine:
15c9,10
< ### Cloning
---
> 1.  [Node.js][]: We use Node to run a development web server and build the project.
>     Depending on your system, you can install Node either from source or as a pre-packaged bundle.
17,18c12,13
<     git clone https://github.com/otto-de/TeamDojo.git
<     cd TeamDojo/
---
> After installing Node, you should be able to run the following command to install development tools.
> You will only need to run this command when dependencies change in [package.json](package.json).
20c15
< ### Start the database
---
>     npm install
22c17
<     docker-compose -f src/main/docker/postgresql.yml up
---
> We use npm scripts and [Webpack][] as our build system.
24c19,20
< ### Start the server
---
> Run the following commands in two separate terminals to create a blissful development experience where your browser
> auto-refreshes when files change on your hard drive.
26a23
>     npm start
28c25,27
< The application will be available at [http://localhost:8080](http://localhost:8080)
---
> Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
> specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
> Add the `help` flag on any command to see how you can use it. For example, `npm help update`.
30c29
< ### Model
---
> The `npm run` command will list all of the scripts available to run for this project.
32c31
< #### Organization
---
> ### Service workers
34,36c33
< TeamDojo comes prefilled with some demo data.
< Log in with the default admin credentials: _admin/teamdojo_ at [http://localhost:8080/#/admin](http://localhost:8080/#/admin)
< and change your **organization** _Entities - Organization_. It will be your navigation root node.
---
> Service workers are commented by default, to enable them please uncomment the following code.
38c35
< #### Topic
---
> -   The service worker registering script in index.html
40,41c37,45
< Next you would like to create some **Topics** your teams want to achieve skills for: _Entities - Topic_.
< Examples could be _Quality Assurance_, _Security_, _Operations_, _Architecture_, ...
---
> ```html
> <script>
>     if ('serviceWorker' in navigator) {
>         navigator.serviceWorker
>         .register('./service-worker.js')
>         .then(function() { console.log('Service Worker Registered'); });
>     }
> </script>
> ```
43c47
< #### Team
---
> Note: workbox creates the respective service worker and dynamically generate the `service-worker.js`
45c49
< Teams are the users of TeamDojo. Here you can create them.
---
> ### Managing dependencies
47c51
< _Entities - Team_
---
> For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:
49,54c53
< -   **Name** - The full Team name.
< -   **Short Name** - An acronym of the Team's name. It will be part of the REST URL.
< -   **Picture** - The Team's logo
< -   Team **Slogan**
< -   Team **Contact Person**
< -   **Participations** - Every Team can participate on one or more Topics.
---
>     npm install --save --save-exact leaflet
56c55
< #### Level
---
> To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:
58,59c57
< Now you can specify maturity or ability **Levels** for these Topics.
< Each Level should contain _n_ Skills - see next step.
---
>     npm install --save-dev --save-exact @types/leaflet
61c59,60
< A Level consists of the following settings:
---
> Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
> Edit [src/main/webapp/app/vendor.ts](src/main/webapp/app/vendor.ts) file:
63c62,64
< _Entities - Level_
---
> ```
> import 'leaflet/dist/leaflet.js';
> ```
65,77c66
< -   Name - The level name. Judo belt ranks for example: yellow, orange, green, ...
< -   [OPTIONAL] **Description** of the level.
< -   A **Picture** to be shown. You can find some examples in [examples/images/level](examples/images/level).
<     You can create your own icons, but for the best user experience get some help from some skilled UI/UX people.
< -   **Required Score** - A decimal value from 0 to 1. You can define how much percent of achieved skills are necessary to
<     get this level. Default can be 1.0
< -   **Skill Score Multiplier** - A decimal value. Here you can specify how much bonus points the team can achieve with every
<     skill of this level. Default can be 0.0. See [Scoring System](#scoring-system-and-balancing) for more details.
< -   [OPTIONAL] **Level Completion Bonus** - A numeric value. How much bonus points the team can achieve with the
<     completion of this level. See [Scoring System](#scoring-system-and-balancing) for more details.
< -   **Topic** - Every Level must be assigned to one topic.
< -   [OPTIONAL] A level can **depends on** a previous level. E.g.: To reach Level 2, all skills of Level 2 and Level 1 must
<     be completed.
---
> Edit [src/main/webapp/content/css/vendor.css](src/main/webapp/content/css/vendor.css) file:
79c68,70
< #### Badge
---
> ```
> @import '~leaflet/dist/leaflet.css';
> ```
81,82c72
< While Level are the core of the maturity model, **Badges** can be used to push some specific skills or to reward well
< performing teams.
---
> Note: there are still few other things remaining to do for Leaflet that we won't detail here.
84c74
< _Entities - Badges_
---
> For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].
86,99c76
< -   Name - The badge name. Technology specific or cool names for skill subsets. E.g.: AWS Badge, Docker Master,
<     Always-up-to-date Badge, Password Ninja, ...
< -   [OPTIONAL] **Description** of the badge.
< -   A **Picture** to be shown. You can find some examples in [examples/images/badge](examples/images/badge).
<     You can create your own icons, but for the best user experience get some help from some skilled UI/UX people.
< -   [NOT IMPLEMENTED] Available Until - not implemented yet.
< -   [NOT IMPLEMENTED] Available Amount - not implemented yet.
< -   **Required Score** - A decimal value from 0 to 1. You can define how much percent of achieved skills are necessary to
<     get this badge. Default can be 1.0.
< -   **Skill Score Multiplier** - A decimal value. Here you can specify how much bonus points the team can achieve with
<     every skill of this badge. Default can be 0.0. See [Scoring System](#scoring-system-and-balancing) for more details.
< -   [OPTIONAL] **Badge Completion Bonus** - A numeric value. How much bonus points the team can gain with the
<     completion of this badge. See [Scoring System](#scoring-system-and-balancing) for more details.
< -   **Topics** - Every Badge can be assigned to one ore more topics.
---
> ### Using angular-cli
101c78
< #### Skill
---
> You can also use [Angular CLI][] to generate some custom client code.
103c80
< The core element of this framework. Teams can obtain **skills**.
---
> For example, the following command:
105c82
< _Entities - Skill_
---
>     ng generate component my-component
107,115c84
< -   **Skill Title** - Short and significant.
< -   **Description** - Is part of the skill details. Why is this skill useful? Why should a team aim to obtain this skill?
< -   **Implementation** - How can the team achieve this skill? Hard facts to configure/implement/learn something.
< -   **Validation** - How can the team tell they have achieved this skill? Hard criteria.
< -   [OPTIONAL] **Expiry Priod** - For future development. A skill expires after a period of time.
< -   [OPTIONAL] **Contact** - A person with know how relevant for this skill.
< -   **Score** - With every achieved skill, a team gains scores. A default value could be 1 for every skill.
< -   [DO NOT TOUCH] **Rate Score** - Users can vote for a skill (1-5 stars). This value should not be set in the admin view.
< -   [DO NOT TOUCH] **Rate Count** - Users can vote for a skill (1-5 stars). This value should not be set in the admin view.
---
> will generate few files:
117c86,88
< #### Assign Skills to Level and Badges
---
>     create src/main/webapp/app/my-component/my-component.component.html
>     create src/main/webapp/app/my-component/my-component.component.ts
>     update src/main/webapp/app/app.module.ts
119c90
< Every Skill should be assigned to at least one Level or Badge:
---
> ### Doing API-First development using openapi-generator
121,122c92
< -   _Entities - Level Skills_
< -   _Entities - Badge Skills_
---
> [OpenAPI-Generator]() is configured for this application. You can generate API code from the `src/main/resources/swagger/api.yml` definition file by running:
124c94,96
< Here you can specify which skills are necessary to obtain a specific Level or Badge.
---
> ```bash
> ./gradlew openApiGenerate
> ```
126c98
< ### Scoring System and Balancing
---
> Then implements the generated delegate classes with `@Service` classes.
128,129c100
< If a team completes a skill, its score will be added to the team's score.
< With the Skill **Score** property you can value its costs / complexity / importance.
---
> To edit the `api.yml` definition file, you can use a tool such as [Swagger-Editor](). Start a local instance of the swagger-editor using docker by running: `docker-compose -f src/main/docker/swagger-editor.yml up -d`. The editor will then be reachable at [http://localhost:7742](http://localhost:7742).
131,133c102
< To honor the skill completion of specific levels or badges, you can adjust the **Level/Badge Score Multiplier**.
< Every completed Skill will add its skill score multiplied with the Score Multiplier.
< E.g.:
---
> Refer to [Doing API-First development][] for more details.
135,136c104
<     Skill: TLS everywhere; Score: 10
<     Badge: Encryption Master; Score Multiplier: 2
---
> ## Building for production
138,141c106
<     #Completion of TLS everywhere will resulting in:
<        10 (Skill Score)
<     +  20 (Skill Score x Score Multiplier)
<     => 30 Points
---
> To optimize the teamdojo application for production, run:
143,145c108
< You can also reward the completion of levels and badges. Therefore you can gain bonus points with **Level/Badge Completion Bonus**.
< If the required percentage (**Required Score**) of skills for the Badge or Level is reached, the Completion Bonus will
< be added to the Team scores. E.g.:
---
>     ./gradlew -Pprod clean bootWar
147,149c110,111
<     Skill: TLS everywhere; Score: 10
<     Skill: Update your Systems; Score: 30
<     Level: Green; Required Score: 1.0; Level Completion Bonus: 100
---
> This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
> To ensure everything worked, run:
151,155c113
<     #Completion of all skills will resulting in:
<         10 (Skill Score)
<     +   30 (Skill Score)
<     +  100 (Completion Bonus)
<     => 140 Points
---
>     java -jar build/libs/*.war
157c115
< You can combine them of course:
---
> Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.
159,162c117
<     Skill: TLS everywhere; Score: 10
<     Skill: Update your Systems; Score: 30
<     Level: Green; Required Score: 1.0; Score Multiplier: 1; Level Completion Bonus: 100
<     Badge: Encryption Master; Score Multiplier: 2
---
> Refer to [Using JHipster in production][] for more details.
163a119
> ## Testing
165,172c121
<     #Completion of all skills will resulting in:
<         10 (Skill Score)
<     +   10 (Skill Score x Level Multiplier)
<     +   20 (Skill Score x Badge Multiplier)
<     +   30 (Skill Score)
<     +   30 (Skill Score x Level Multiplier)
<     +  100 (Level Completion Bonus)
<     => 200 Points
---
> To launch your application's tests, run:
174c123
< ## Security
---
>     ./gradlew test
176,178c125
< For maximum transparency and accessibility there is no user / role concept.
< Everyone can see and change everything. An exception is the technical Administrator.
< He configures rarely changing data like teams, levels, skills, etc. to hide the internal complexity.
---
> ### Client tests
180,183c127
< We believe that this tool helps software development teams to track where they are performing well and where they can
< become improve to build better software.
< Any additional complexity level - processes, login, role models - will result in a decreasing motivation to use
< yet another tool during your daily business.
---
> Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:
185,186c129
< The easiest way to hide your internal data are IP whitelists on network level.
< Host this tool in your internal network and let everyone use and see it.
---
>     npm test
188,189c131
< If you really want any kind of authentication/authorization process, build it, push it back into this repository but
< make it optional.
---
> For more information, refer to the [Running tests page][].
191c133
< ### Default secrets / credentials
---
> ### Code quality
193c135
< The default admin credentials are: **admin/teamdojo**, configured in [src/main/resources/config/liquibase/users.csv](src/main/resources/config/liquibase/users.csv).
---
> Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:
195c137,139
< The secret for the "Remember me" Cookie is configured in [src/main/resources/config/application-prod.yml](src/main/resources/config/application-prod.yml).
---
> ```
> docker-compose -f src/main/docker/sonar.yml up -d
> ```
197c141
< Please change the password and secret in your production environment.
---
> Then, run a Sonar analysis:
199c143,151
< ## Development
---
> ```
> ./gradlew -Pprod clean test sonarqube
> ```
> 
> For more information, refer to the [Code quality page][].
> 
> ## Using Docker to simplify development (optional)
> 
> You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.
201c153
< [Here](DEVELOPMENT.md) you can find the dev documentation.
---
> For example, to start a postgresql database in a docker container, run:
203c155
< ## Further Development (Status determination feature)
---
>     docker-compose -f src/main/docker/postgresql.yml up -d
205c157
< Determining the status of a skill acquired by a Team was reworked.
---
> To stop it and remove the container, run:
207c159
< A combination of data from the Skill and Teamskill entities are used in the determination of the status.
---
>     docker-compose -f src/main/docker/postgresql.yml down
209c161,162
< The fields used in calculating the status are "irrelevant" and "completedAt" (From the TeamSkill Entity) and "expiryPeriod" (from the Skill Entity).
---
> You can also fully dockerize your application and all the services that it depends on.
> To achieve this, first build a docker image of your app by running:
211c164
< In the context of a given Team, a skill is in the
---
>     ./gradlew bootWar -Pprod jibDockerBuild
213c166
< -   Status `OPEN`: If the TeamSkill.completedAt field is not set (that is null) and TeamSkill.irrelevant is not true
---
> Then run:
215c168
< -   Status `IRRELEVANT`: If the TeamSkill.irrelevant field is set to true, notwithstanding the values of the other fields.
---
>     docker-compose -f src/main/docker/app.yml up -d
217,224c170
< -   Status `ACHIEVED`: If the TeamSkill.completedAt field is set and the TeamSkill.irrelevant is not true and the TeamSkill.completedAt
<     plus the SKill.expiryPeriod (in days) is later than the current date.
< -   Status `EXPIRING`: If the TeamSkill.completedAt field is set and the TeamSkill.irrelevant is not true, the current date
<     is later than the TeamSkill.completedAt plus the SKill.expiryPeriod (in days) but not later than a
<     7-days grace period.
< -   Status `EXPIRED`: If the TeamSkill.completedAt field is set and the TeamSkill.irrelevant is not true, the current date
<     is later than the TeamSkill.completedAt plus the SKill.expiryPeriod (in days) and the 7-days grace period
<     is past.
---
> For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.
226,227c172
< The corresponding skill status for a team is determined and set in the AchievableSkillDTO and passed as part of the
< JSON string on to the frontend.
---
> ## Continuous Integration (optional)
229,230c174
< At the frontend, the skillstatus information in the JSON-string from the backend is deserialized into a typescript object (SkillStatus enum in the
< IAchievableSkill Typescript type).
---
> To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.
232,233c176,196
< The Skillstatus information at the frontend is used to calculate the number of teams that have acquired a given skill, to display the status
< of a skill for a given team, to toggle status from one state to another.
---
> [jhipster homepage and latest documentation]: https://www.jhipster.tech
> [jhipster 5.8.1 archive]: https://www.jhipster.tech/documentation-archive/v5.8.1
> [using jhipster in development]: https://www.jhipster.tech/documentation-archive/v5.8.1/development/
> [using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v5.8.1/docker-compose
> [using jhipster in production]: https://www.jhipster.tech/documentation-archive/v5.8.1/production/
> [running tests page]: https://www.jhipster.tech/documentation-archive/v5.8.1/running-tests/
> [code quality page]: https://www.jhipster.tech/documentation-archive/v5.8.1/code-quality/
> [setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v5.8.1/setting-up-ci/
> [node.js]: https://nodejs.org/
> [yarn]: https://yarnpkg.org/
> [webpack]: https://webpack.github.io/
> [angular cli]: https://cli.angular.io/
> [browsersync]: http://www.browsersync.io/
> [jest]: https://facebook.github.io/jest/
> [jasmine]: http://jasmine.github.io/2.0/introduction.html
> [protractor]: https://angular.github.io/protractor/
> [leaflet]: http://leafletjs.com/
> [definitelytyped]: http://definitelytyped.org/
> [openapi-generator]: https://openapi-generator.tech
> [swagger-editor]: http://editor.swagger.io
> [doing api-first development]: https://www.jhipster.tech/documentation-archive/v5.8.1/doing-api-first-development/
