all: help

.PHONY:
prerequisites: ## Install prerequisite npm tools.
	@echo "Installing prerequisites with JHipster ..."
	npm install -g generator-jhipster
	npm install -g yo
	npm install -g rimraf

.PHONY:
init: ## TODO
	@echo "Initilizing with JHipster ..."
	jhipster app
	jhipster jdl teamDojo.jdl

.PHONY:
generate: ## TODO
	@echo "Generating with JHipster ..."
	jhipster app
	jhipster jdl teamDojo.jdl

.PHONY:
generate-all: ## TODO
	@echo "Generating all ressources with JHipster ..."
	jhipster app
	jhipster jdl teamDojo.jdl
	jhipster ci-cd
	cd ./deployment/k8s/kustomize/; \
		jhipster kubernetes
	cd ./deployment/k8s/helm/; \
		jhipster kubernetes-helm

.PHONY:
run: ## TODO
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
