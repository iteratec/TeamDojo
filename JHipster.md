# Prerequisites

- JHipster >= 7.0.1

## Local installation

More details can be found here: https://www.jhipster.tech/installation/

### with NPM

```bash
# npm install -g generator-jhipster@5.8.1
npm install -g generator-jhipster
npm install -g yo

# check if version is ok
jhipster --version
```

### with YARN (alternative to NPM)

```bash
yarn global add generator-jhipster
```

# JHipster Code Generation

This repo contains two important _jhipster_ configuration files:

- JHipster generator configuration: `.yo-rc.json`
- JHipster Entities Definitions: `teamDojo.jdl`

## Initial Setup

```bash
# Create JHipster Stubs (see .yo-rc.json for all configuration options)
jhipster

# Create all TeamDojo entities with JHipster
jhipster jdl teamDojo.jdl

# Create CI/CD Pipeline based on GitHubActions
jhipster ci-cd

    # Example
    🚀 Welcome to the JHipster CI/CD Sub-Generator 🚀
    > ? What CI/CD pipeline do you want to generate? `GitHub Actions`
    > ? What tasks/integrations do you want to include ? 
        Build and publish a *Docker* image, 
        *Snyk*: dependency scanning for security vulnerabilities (requires SNYK_TOKEN)
    > ? *Docker*: what is the name of the image ? `team-dojo`

# Create deployment dir
mkdir deployment
mkdir deployment/docker
mkdir deployment/K8S
mkdir deployment/K8S/helm
mkdir deployment/K8S/kustomize

# Create K8S deployment (in the folder K8S)
cd deployment/k8s/kustomize
jhipster kubernetes
cd ../helm
jhipster kubernetes-helm

```

## Update Setup

- Update JHipster Stubs: `jhipster` (see .yo-rc.json for all configuration options)
- Update all TeamDojo entities with JHipster: `jhipster jdl teamDojo.jdl`
