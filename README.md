# TeamDojo

An application for improving (application and project) skills of your teams by gamification.

It allows teams to self assess their skills and checks if they are reaching a specified ability level. If they reach a new ability level, they will be rewarded with a cool new Team Avatar, Level Rewards (like a virtual belt) and topic specific Badges. _TeamDojo_ also calculates scores, based on specific skill, level and badge ranking/difficulty and ranks the teams by the amount of their achieved scores.

![screencast](screencast.gif 'Screencast')

## Build and Run

There are two slightly different ways to build and run _TeamDojo_:

1. Development with hot module reloading of the frontend, and
2. Production to play around with _TeamDojo_.

There are also two ways of persistence:

1. Embedded database (recommended for development), and
2. PostgreSQL (recommended for production).

Either way you choose you always need an OAuth identity provider to run _TeamDojo_. For local setupt there is a compose file to run a Keycloak service.

### Prerequisites

First you need the repository:

```shell
git clone https://github.com/otto-de/TeamDojo.git
cd TeamDojo/
```

You need some tools:

- Java 11 (We strongly recommend to use [SDKMAN](https://sdkman.io/install).)
- NodeJS 14 (We strongly recommend to use [NVM](https://github.com/nvm-sh/nvm).)
- [GNU Make](https://www.gnu.org/software/make/manual/make.html)
- [Docker](https://docs.docker.com/get-docker/) & [Docker Compose](https://docs.docker.com/compose/install/)
- [direnv](https://direnv.net/) (Optional to source env vars from `.envrc`.)

For installing some NodeJS prerequisites simply run:

`shell make prerequisites `

### Gradle

Basically everything is done by Gradle build scripts. So simply build and run is done by invoking `./gradlew` (gradle wrapper). You can pass Spring Boot profiles to gradle (e.g. `./gradlew -Pprod`).

As mentioned above the application requires at least the Keycloak service and depending on the Spring Boot profile a PostgreSQL. The Docker Compose files for these services are located in `src/main/docker/`.

### GNU Make

To type all the commands to spin up the required services and build and run is quite tedious. so we added a `Makefile` to provide shorthands. Just type `make` to get a list of all available targets.

#### 1. Development Mode

For development mode you need two terminal windows (we recommend using [tmux](https://github.com/tmux/tmux/wiki)):

1. In the first terminal run `make start-backend`, and
   - This will also start the Keycloak, which will take a while.
2. in the second terminal run `make start-frontend`.
   - This will open a browser windows locating to <http://localhost:9000>.

#### 2. Production Mode

To run TeamDojo in production mode with PostgreSQL simply run:

```shell
make start
```

The application will be available at <http://localhost:8080>

You can find more information in our [developer documentation](docs/Development.md).

### Demo Data

We provide a Liquibase changeset with some demo data (`src/main/resources/config/liquibase/initial_demo_data.xml`). If you start the backend with "dev" profile it will be inserted automatically.

If you want to insert the data when running other profiles you must pass the Java option `-Dspring.liquibase.contexts=demo` to Spring Boot.

### Default secrets / credentials

The default admin credentials are: **admin/teamdojo**, configured in [src/main/resources/config/liquibase/users.csv](src/main/resources/config/liquibase/users.csv).

The secret for the "Remember me" Cookie is configured in [src/main/resources/config/application-prod.yml](src/main/resources/config/application-prod.yml).

Please change the password and secret in your production environment.
