{{/* vim: set filetype=mustache: */}}
{{/*
prometheus/grafana customisation
*/}}
{{- define "prometheus.name" -}}
{{- default "jhipster-prometheus" -}}
{{- end -}}

{{- define "prometheus.fullname" -}}
{{- default "jhipster-prometheus" -}}
{{- end -}}

{{- define "prometheus.server.fullname" -}}
{{- default "jhipster-prometheus" -}}
{{- end -}}

{{- define "prometheus.pushgateway.fullname" -}}
{{- default "jhipster-prometheus-pushgateway" -}}
{{- end -}}

{{- define "prometheus.nodeExporter.fullname" -}}
{{- default "jhipster-prometheus-nodeexporter" -}}
{{- end -}}

{{- define "prometheus.alertmanager.fullname" -}}
{{- default "jhipster-prometheus-alertmanager" -}}
{{- end -}}

{{- define "grafana.name" -}}
{{- default "jhipster-grafana" -}}
{{- end -}}

{{- define "grafana.fullname" -}}
{{- default "jhipster-grafana" -}}
{{- end -}}

