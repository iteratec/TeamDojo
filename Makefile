# Usage:
# make        # generate-all
# make clean  # remove ALL binaries and objects

.PHONY: all prerequisites init generate run clean test
.DEFAULT_GOAL:= generate

all: init

prerequisites:
	@echo "Installing prerequisites with JHipster ..."
	npm install -g generator-jhipster
	npm install -g yo
	npm install -g rimraf

init:
	@echo "Initilizing with JHipster ..."
	jhipster
	jhipster jdl teamDojo.jdl

generate:
	@echo "Generating with JHipster ..."
	jhipster
	jhipster jdl teamDojo.jdl

generate-all:
	@echo "Generating all ressources with JHipster ..."
	jhipster
	jhipster jdl teamDojo.jdl
	jhipster ci-cd
	cd ./deployment/k8s/kustomize/; \
		jhipster kubernetes
	cd ./deployment/k8s/helm/; \
		jhipster kubernetes-helm

run:
	@echo "Running with JHipster ..."
	docker-compose -f src/main/docker/keycloak.yml up -d
	./gradlew -x webapp
	npm start

clean:
	@echo "Cleaning up all generated files..."
	shopt -s extglob
	rm -Rdv * !("*.yo-rc.json"|"JHipster.md"|"README.md"|"teamDojo.jdl"|"Makefile")

test:
	@echo "please add a testsuite here"
