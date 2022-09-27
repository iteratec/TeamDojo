# TeamDojo

![Version: 2.0.2](https://img.shields.io/badge/Version-2.0.2-informational?style=flat-square) ![Type: application](https://img.shields.io/badge/Type-application-informational?style=flat-square) ![AppVersion: 2.0.2](https://img.shields.io/badge/AppVersion-2.0.2-informational?style=flat-square) [![Artifact Hub](https://img.shields.io/endpoint?url=https://artifacthub.io/badge/repository/iteratec)](https://artifacthub.io/packages/search?repo=iteratec)

TeamDojo is a application for improving (application and project) skills of your teams through gamification.

**Homepage:** <https://www.iteratec.com>

## Maintainers

| Name | Email | Url |
| ---- | ------ | --- |
| iteratec GmbH |  |  |

## Source Code

* <https://github.com/iteratec/TeamDojo.git>

## Requirements

Kubernetes: `>=v1.11.0-0`

| Repository | Name | Version |
|------------|------|---------|
| https://charts.bitnami.com/bitnami | postgresql | 11.0.4 |

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| affinity | object | `{}` |  |
| autoscaling.enabled | bool | `false` |  |
| autoscaling.maxReplicas | int | `10` |  |
| autoscaling.minReplicas | int | `1` |  |
| autoscaling.targetCPUUtilizationPercentage | int | `80` |  |
| autoscaling.targetMemoryUtilizationPercentage | int | `80` |  |
| fullnameOverride | string | `""` |  |
| image.pullPolicy | string | `"IfNotPresent"` |  |
| image.repository | string | `"docker.io/iteratec/teamdojo"` |  |
| image.tag | string | `"2.0.2"` |  |
| imagePullSecrets | list | `[]` |  |
| ingress.annotations | object | `{}` |  |
| ingress.enabled | bool | `false` |  |
| ingress.hosts[0].host | string | `"chart-example.local"` |  |
| ingress.hosts[0].paths[0].backend.serviceName | string | `"chart-example.local"` |  |
| ingress.hosts[0].paths[0].backend.servicePort | int | `80` |  |
| ingress.hosts[0].paths[0].path | string | `"/"` |  |
| ingress.tls | list | `[]` |  |
| nameOverride | string | `""` |  |
| nodeSelector | object | `{}` |  |
| podAnnotations | object | `{}` |  |
| podSecurityContext | object | `{}` |  |
| postgresql.auth.database | string | `"teamdojo"` |  |
| postgresql.auth.username | string | `"teamdojo"` |  |
| postgresql.enabled | bool | `true` |  |
| replicaCount | int | `1` |  |
| resources | object | `{}` |  |
| securityContext | object | `{}` |  |
| service.port | int | `8080` |  |
| service.type | string | `"ClusterIP"` |  |
| serviceAccount.annotations | object | `{}` |  |
| serviceAccount.create | bool | `false` |  |
| serviceAccount.name | string | `""` |  |
| teamdojo | object | `{"env":[{"name":"SPRING_LIQUIBASE_CONTEXTS","value":"prod,demo"}],"javaopts":" -Xmx512m -Xms256m","metrics":{"prometheus":{"enabled":"false"}},"oauth2":{"clientId":null,"clientSecret":null,"enabled":false,"issuerUri":null},"profile":"prod,api-docs"}` | TeamDojo specific application configurations |
| teamdojo.javaopts | string | `" -Xmx512m -Xms256m"` | Configures the JAVA_OPTS |
| teamdojo.oauth2.enabled | bool | `false` | Must be enabled and configured to integrate TeamDojo witch your OIDC Provider, e.g. keycloak |
| teamdojo.profile | string | `"prod,api-docs"` | Configures the Spring Boot Application Profile, e.g. "prod,api-docs", "dev" |
| tolerations | list | `[]` |  |