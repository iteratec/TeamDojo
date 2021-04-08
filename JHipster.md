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

- Create JHipster Stubs: `jhipster` (see .yo-rc.json for all configuration options)
- Create all Entities: `jhipster jdl teamDojo.jdl`
