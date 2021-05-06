.PHONY:
all: help

.PHONY:
prerequisites: ## Install prerequisite npm tools.
	@echo "Installing prerequisites with JHipster ..."
	npm install -g generator-jhipster
	npm install -g yo
	npm install -g rimraf

.PHONY:
generate-app: ## Generate application based on the selected options.
	@echo "Generate app with JHipster..."
	jhipster app

.PHONY:
generate-jdl: ## Generate entities from the JDL file.
	@echo "Generating with JHipster..."
	jhipster jdl teamDojo.jdl

.PHONY:
generate-ci-cd: ## Generate pipeline scripts.
	jhipster ci-cd

.PHONY:
deploy-kubernetes: ## Deploy the current application to Kubernetes.
	cd ./deployment/k8s/kustomize/; \
		jhipster kubernetes

.PHONY:
deploy-kubernetes-helm: ## Deploy the current application to Kubernetes using Helm.
	cd ./deployment/k8s/helm/; \
		jhipster kubernetes-helm

.PHONY:
generate-all: generate-app generate-jdl generate-ci-cd ## Generate everything.

.PHONY:
run: ## Run the app local.
	@echo "Running with JHipster ..."
	docker-compose -f src/main/docker/keycloak.yml up -d
	./gradlew -x webapp
	npm start

.PHONY:
clean: ## remove all binaries and objects.
	@echo "Cleaning up all generated files..."
	shopt -s extglob
	rm -Rdv * !("*.yo-rc.json"|"JHipster.md"|"README.md"|"teamDojo.jdl"|"Makefile")

.PHONEY:
help: ## Display this help screen.
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
