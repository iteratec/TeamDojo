# Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1.  [Node.js][]: We use Node to run a development web server and build the project.
    Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```shell
npm install
```

We use npm scripts and [Webpack][] as our build system.
And postgresql as our database. For the database we use a standard docker-compose file.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive:

```shell
docker-compose -f src/main/docker/postgresql.yml up
./gradlew
```

and 

```shell
npm start
```

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

## Service workers

Service workers are commented by default, to enable them please uncomment the following code.

-   The service worker registering script in `index.html`

```html
<script>
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.register('./service-worker.js').then(function() {
            console.log('Service Worker Registered');
        });
    }
</script>
```

Note: workbox creates the respective service worker and dynamically generate the `service-worker.js`

## Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

```shell
npm install --save --save-exact leaflet
```

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

```shell
npm install --save-dev --save-exact @types/leaflet
```

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/vendor.ts](src/main/webapp/app/vendor.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/css/vendor.css](src/main/webapp/content/css/vendor.css) file:

```
@import '~leaflet/dist/leaflet.css';
```

Note: there are still few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Using angular-cli

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

```shell
ng generate component my-component
```

will generate few files:

```shell
create src/main/webapp/app/my-component/my-component.component.html
create src/main/webapp/app/my-component/my-component.component.ts
update src/main/webapp/app/app.module.ts
```

## Doing API-First development using openapi-generator

[OpenAPI-Generator]() is configured for this application. You can generate API code from the `src/main/resources/swagger/api.yml` definition file by running:

```bash
./gradlew openApiGenerate
```

Then implements the generated delegate classes with `@Service` classes.

To edit the `api.yml` definition file, you can use a tool such as [Swagger-Editor](). Start a local instance of the swagger-editor using docker by running: `docker-compose -f src/main/docker/swagger-editor.yml up -d`. The editor will then be reachable at [http://localhost:7742](http://localhost:7742).

Refer to [Doing API-First development][] for more details.

## Building for production

To optimize the TeamDojo application for production, run:

```shell
./gradlew -Pprod clean bootWar
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

```shell
java -jar build/libs/*.war
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

```shell
./gradlew test
```

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

```shell
npm test
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```shell
make start-local-sonar
```

Then, run a Sonar analysis:

```shell
make sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```shell
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```shell
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on. To achieve this, first build a docker image of your app by running:

```shell
./gradlew bootWar -Pprod jibDockerBuild
```

Then run:

```shell
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

## Working with Liquibase

Liquibase is used to keep the database structure in sync with the application code.

Every change made to the entity classes have to come along with a migration script for Liquibase. This script contains all the instructions (in XML-syntax) that have to be made in the database to overcome the delta between its current and the desired structure. These migration scripts have to be placed in folder `src/main/resources/config/liquibase/changelog/`. They are sequenced and applied by Liquibase in the ascending order of the date declaration in their name.

The custom gradle task `liquibaseDataImportFromYaml` creates a liquibase script out of `\*.yaml` files, placed in folder `skills/`. This can be useful to load test data into a database instance.

## i18n handling for person- & team-mode

The application can be run in 2 different modes. One for teams & one for individuals/persons. These 2 mode differentiate in the usage of i18n-files in folder `src/main/webapp/i18n`. See the usage of the MergeJsonWebpackPlugin in `webpack.common.js` file. These files have to kept in sync. The following script can help with that.

```shell
#!/bin/bash

dir=$1
from=$2
to=$3
cd $dir

for file in $(echo $dir'/src/main/webapp/i18n/'$from'/*.json')
do
  git diff --no-prefix dev $file > patch.patch
  patch $( echo $file | sed -e 's/\/'$from'\//\/'$to'\//g') patch.patch
done

rm patch.patch

command for example: $./i18n.sh ~/projects/slab-dojo en en_person
```

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 5.8.1 archive]: https://www.jhipster.tech/documentation-archive/v5.8.1
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v5.8.1/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v5.8.1/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v5.8.1/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v5.8.1/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v5.8.1/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v5.8.1/setting-up-ci/
[node.js]: https://nodejs.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: http://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: http://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: http://leafletjs.com/
[definitelytyped]: http://definitelytyped.org/
[openapi-generator]: https://openapi-generator.tech
[swagger-editor]: http://editor.swagger.io
[doing api-first development]: https://www.jhipster.tech/documentation-archive/v5.8.1/doing-api-first-development/
