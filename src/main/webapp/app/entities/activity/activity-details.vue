<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="activity">
        <h2 class="jh-entity-heading" data-cy="activityDetailsHeading">
          <span v-text="$t('teamDojoApp.activity.detail.title')">Activity</span> {{ activity.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.activity.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('teamDojoApp.ActivityType.' + activity.type)">{{ activity.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.activity.data')">Data</span>
          </dt>
          <dd>
            <span>{{ activity.data }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.activity.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="activity.createdAt">{{ $d(Date.parse(activity.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.activity.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="activity.updatedAt">{{ $d(Date.parse(activity.updatedAt), 'long') }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="activity.id" :to="{ name: 'ActivityEdit', params: { activityId: activity.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./activity-details.component.ts"></script>
