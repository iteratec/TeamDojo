#
# This Makefile contains various helper targets to do JHipster stuff
#
# This is more a collection of command documentation, so we remember the
# flags and options. It is not a build script.
#

PROJECT_DIR		= $(shell pwd)
TOOLS_DIR		= $(PROJECT_DIR)/tools
COMPOSE_FILES	= $(PROJECT_DIR)/src/main/docker
DIAGRAMS		:= $(shell find $(PROJECT_DIR) -type f -name '*.puml')
IMAGES			:= $(addsuffix .png, $(basename $(DIAGRAMS)))
JAVA_VERSION	:= $(shell cut  -d'=' -f 2 .sdkmanrc)

all: help

.PHONY: prerequisites
prerequisites: ## Install prerequisite npm tools.
	@echo "Installing prerequisites with JHipster ..."
	npm install -g generator-jhipster@7.7.0
	npm install -g yo
	npm install -g rimraf

# This target is ment to be run after code generation: It will re-add custom dependencies again
# to the package.json, after code generation overwrites the file.
.PHONY: npm-dependencies
npm-dependencies: ## Install all additional npm packages.
	@echo "Installing additional npm packages ..."
	npm install simplebar
	npm install ngx-markdown
	npm install moment
	npm install @ibm/plex
	npm install @fortawesome/fontawesome-free

.PHONY: install-java
install-java: ## Install Java with SDKMAN
	source $(HOME)/.sdkman/bin/sdkman-init.sh && sdk install java $(JAVA_VERSION)

.PHONY: generate-app
generate-app: ## Generate application based on the selected options.
	@echo "Generate app with JHipster..."
	jhipster app --with-entities --with-generated-flag

.PHONY: generate-client-app
generate-client-app: ## Generate client application based on the selected options.
	@echo "Generate client app with JHipster..."
	jhipster app --skip-server --with-generated-flag

.PHONY: generate-server-app
generate-server-app: ## Generate server application based on the selected options.
	@echo "Generate server app with JHipster..."
	jhipster app --skip-client --with-generated-flag

.PHONY: generate-jdl
generate-jdl: ## Generate entities from the JDL file.
	@echo "Generating with JHipster..."
	jhipster jdl --with-generated-flag teamDojo_v2.jdl

.PHONY: generate-ci-cd
generate-ci-cd: ## Generate pipeline scripts.
	jhipster ci-cd

.PHONY: deploy-kubernetes
deploy-kubernetes: ## Deploy the current application to Kubernetes.
	cd deployment/K8S-generated/kustomize && jhipster kubernetes

.PHONY: deploy-kubernetes-helm
deploy-kubernetes-helm: ## Deploy the current application to Kubernetes using Helm.
	cd deployment/K8S-generated/helm && jhipster kubernetes-helm

.PHONY: generate-all
generate-all: generate-app generate-jdl generate-ci-cd ## Generate everything.

.PHONY: start-keycloak
start-keycloak: ## Start the Keycloak container for authentication.
	docker-compose -f $(COMPOSE_FILES)/keycloak.yml up -d
	$(TOOLS_DIR)/wait-for-container.sh \
		'Keycloak' \
		$(COMPOSE_FILES)/keycloak.yml \
		'Admin console listening on'

.PHONY: stop-keycloak
stop-keycloak: ## Stop the Keycloak container.
	docker-compose -f $(COMPOSE_FILES)/keycloak.yml down || true

.PHONY: start-postgres
start-postgres: ## Start the PostgreSQL container.
	docker-compose -f $(COMPOSE_FILES)/postgresql.yml up -d

.PHONY: stop-postgres
stop-postgres: ## Stop the PostgreSQL container.
	docker-compose -f $(COMPOSE_FILES)/postgresql.yml down || true

.PHONY: start-registry
start-registry: start-keycloak ## Start the JHipster Registry container
	docker-compose -f $(COMPOSE_FILES)/jhipster-registry.yml up -d
	$(TOOLS_DIR)/wait-for-container.sh \
		'JHipster Registry' \
		$(COMPOSE_FILES)/jhipster-registry.yml \
		"Application 'jhipster-registry' is running!"

.PHONY: stop-registry
stop-registry: ## Stop the JHipster Registry container
	docker-compose -f $(COMPOSE_FILES)/jhipster-registry.yml down || true

.PHONY: start-backend
start-backend: start-keycloak ## Start the application backend in dev mode.
	$(PROJECT_DIR)/gradlew -x webapp -Pdev,swagger

.PHONY: start-backend-debug
start-backend-debug: start-keycloak ## Start the application backend with java debug port enabled in dev mode.
	# It is mandatory to mention the gradle task explicit here, unless the option --debug-jvm from bootRun is not recognized
	# correctly by Gradle as an option of the default task (which is bootRun) and Gradle exits w/ error.
	$(PROJECT_DIR)/gradlew -x webapp bootRun -Pdev,swagger --debug-jvm

.PHONY: start-frontend
start-frontend: ## Start the application frontend in dev mode.
	npm install
	npm start

.PHONY: start ## Start the application with all dependent containers.
start: start-keycloak start-postgres ## Start the application (backend & frontend) in production mode.
	$(PROJECT_DIR)/gradlew -Pprod

.PHONY: start-debug
start-debug: start-postgres ## Start the application with java debug port enabled (backend & frontend) in production mode.
	# It is mandatory to mention the gradle task explicit here, unless the option --debug-jvm from bootRun is not recognized
	# correctly by Gradle as an option of the default task (which is bootRun) and Gradle exits w/ error.
	$(PROJECT_DIR)/gradlew bootRun --debug-jvm -Pprod

.PHONY: stop ## Stop all dependent containers.
stop: stop-registry stop-keycloak stop-postgres ## Stop everything.

.PHONY: build-prod-jar ## Build the productive fat jar.
build-prod-jar: ## Generates production bootable jar in build/libs/."
	$(PROJECT_DIR)/gradlew -Pprod clean bootJar

.PHONY: sonar
sonar: ## Run Sonarqube analysis.
# Copy _secrets to .secrets and add the password of your local SonarQube.
	$(PROJECT_DIR)/gradlew -Pprod clean check jacocoTestReport sonarqube \
		-Dsonar.host.url=http://localhost:9001 \
		-Dsonar.login=admin \
		-Dsonar.password=$(SONAR_PASSWORD) \

.PHONY: start-local-sonar
start-local-sonar: ## Start local dev Sonarqube server.
# https://www.jhipster.tech/code-quality/
	docker-compose -f $(COMPOSE_FILES)/sonar.yml up -d

.PHONY: stop-local-sonar
stop-local-sonar: ## Stop local dev Sonarqube server.
# https://www.jhipster.tech/code-quality/
	docker-compose -f $(COMPOSE_FILES)/sonar.yml down

.PHONEY: test-backend
test-backend: ## Run backend tests.
	npm run ci:backend:test

.PHONEY: integrationtest-backend
integrationtest-backend: ## Run backend integrationtest.
	./gradlew test integrationTest -x webapp -x webapp_test -Dlogging.level.ROOT=OFF -Dlogging.level.org.zalando=OFF -Dlogging.level.tech.jhipster=OFF -Dlogging.level.com.iteratec.teamdojo=OFF -Dlogging.level.org.springframework=OFF -Dlogging.level.org.springframework.web=OFF -Dlogging.level.org.springframework.security=OFF "-Pprod"

.PHONEY: test-frontend
test-frontend: ## Run frontend tests.
	npm run ci:frontend:test

.PHONEY: test
test: test-backend test-frontend ## Run all tests.

.PHONY: github-action
github-action: ## Execute the GitHub action on your local machine (requires act installed).
	# https://github.com/nektos/act
	act -e $(PROJECT_DIR)/.github/event_data.json

.PHONEY: clean
clean: ## Wipes all local built artifacts.
	$(PROJECT_DIR)/gradlew clean
	rm -rf node_modules/

.PHONY: puml
puml: $(IMAGES) ## Generate PlantUML images

%.png: %.puml
	plantuml -tpng $^

.PHONY: start-docs
start-docs: puml ## Start Mkdocs server locally
	mkdocs serve

.PHONEY: help
help: ## Display this help screen.
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
