<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="report">
        <h2 class="jh-entity-heading" data-cy="reportDetailsHeading">
          <span v-text="$t('teamDojoApp.report.detail.title')">Report</span> {{ report.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.report.title')">Title</span>
          </dt>
          <dd>
            <span>{{ report.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.report.description')">Description</span>
          </dt>
          <dd>
            <span>{{ report.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.report.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('teamDojoApp.ReportType.' + report.type)">{{ report.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.report.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="report.createdAt">{{ $d(Date.parse(report.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.report.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="report.updatedAt">{{ $d(Date.parse(report.updatedAt), 'long') }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="report.id" :to="{ name: 'ReportEdit', params: { reportId: report.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./report-details.component.ts"></script>
