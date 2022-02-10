#
# This Makefile contains various helper targets to do JHipster stuff
#
# This is more a collection of command documentation, so we remember the
# flags and options. It is not a build script.
#

PROJECT_DIR		= $(shell pwd)
TOOLS_DIR		= $(PROJECT_DIR)/tools
COMPOSE_FILES	= $(PROJECT_DIR)/src/main/docker

all: help

.PHONY: prerequisites
prerequisites: ## Install prerequisite npm tools.
	@echo "Installing prerequisites with JHipster ..."
	npm install -g generator-jhipster
	npm install -g yo
	npm install -g rimraf

.PHONY: generate-app
generate-app: ## Generate application based on the selected options.
	@echo "Generate app with JHipster..."
	jhipster app --with-entities

.PHONY: generate-client-app
generate-client-app: ## Generate client application based on the selected options.
	@echo "Generate client app with JHipster..."
	jhipster app --skip-server

.PHONY: generate-server-app
generate-server-app: ## Generate server application based on the selected options.
	@echo "Generate server app with JHipster..."
	jhipster app --skip-client

.PHONY: generate-jdl
generate-jdl: ## Generate entities from the JDL file.
	@echo "Generating with JHipster..."
	jhipster jdl teamDojo_v2.jdl

.PHONY: generate-ci-cd
generate-ci-cd: ## Generate pipeline scripts.
	jhipster ci-cd

.PHONY: deploy-kubernetes
deploy-kubernetes: ## Deploy the current application to Kubernetes.
	cd deployment/k8s/kustomize && jhipster kubernetes

.PHONY: deploy-kubernetes-helm
deploy-kubernetes-helm: ## Deploy the current application to Kubernetes using Helm.
	cd deployment/k8s/helm && jhipster kubernetes-helm

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
	docker-compose -f $(COMPOSE_FILES)/keycloak.yml down

.PHONY: start-postgres
start-postgres: ## Start the PostgreSQL container.
	docker-compose -f $(COMPOSE_FILES)/postgresql.yml up -d

.PHONY: stop-postgres
stop-postgres: ## Stop the PostgreSQL container.
	docker-compose -f $(COMPOSE_FILES)/postgresql.yml down

.PHONY: start-registry
start-registry: start-keycloak ## Start the JHipster Registry container
	docker-compose -f $(COMPOSE_FILES)/jhipster-registry.yml up -d
	$(TOOLS_DIR)/wait-for-container.sh \
		'JHipster Registry' \
		$(COMPOSE_FILES)/jhipster-registry.yml \
		'Connected to the JHipster Registry running in Docker'

.PHONY: stop-registry
stop-registry: ## Stop the JHipster Registry container
	docker-compose -f $(COMPOSE_FILES)/jhipster-registry.yml down

.PHONY: start-backend
start-backend: start-keycloak ## Start the application backend in dev mode.
	$(PROJECT_DIR)/gradlew -x webapp -Pdev,swagger

.PHONY: start-frontend
start-frontend: ## Start the application frontend in dev mode.
	npm install
	npm start

.PHONY: start ## Start the application with all dependent containers.
start: start-postgres start-registry ## Start the application (backend & frontend) in production mode.
	$(PROJECT_DIR)/gradlew -Pprod

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

.PHONEY: clean
clean: ## Wipes all local built artifacts.
	$(PROJECT_DIR)/gradlew clean
	rm -rf node_modules/

.PHONEY: help
help: ## Display this help screen.
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
