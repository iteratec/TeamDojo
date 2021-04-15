<template>
  <div>
    <h2 id="page-heading" data-cy="ActivityHeading">
      <span v-text="$t('teamDojoApp.activity.home.title')" id="activity-heading">Activities</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.activity.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ActivityCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-activity"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.activity.home.createLabel')"> Create a new Activity </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && activities && activities.length === 0">
      <span v-text="$t('teamDojoApp.activity.home.notFound')">No activities found</span>
    </div>
    <div class="table-responsive" v-if="activities && activities.length > 0">
      <table class="table table-striped" aria-describedby="activities">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span v-text="$t('teamDojoApp.activity.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('data')">
              <span v-text="$t('teamDojoApp.activity.data')">Data</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'data'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('teamDojoApp.activity.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span v-text="$t('teamDojoApp.activity.updatedAt')">Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="activity in activities" :key="activity.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ActivityView', params: { activityId: activity.id } }">{{ activity.id }}</router-link>
            </td>
            <td v-text="$t('teamDojoApp.ActivityType.' + activity.type)">{{ activity.type }}</td>
            <td>{{ activity.data }}</td>
            <td>{{ activity.createdAt ? $d(Date.parse(activity.createdAt), 'short') : '' }}</td>
            <td>{{ activity.updatedAt ? $d(Date.parse(activity.updatedAt), 'short') : '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ActivityView', params: { activityId: activity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ActivityEdit', params: { activityId: activity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(activity)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
        <infinite-loading
          ref="infiniteLoading"
          v-if="totalItems > itemsPerPage"
          :identifier="infiniteId"
          slot="append"
          @infinite="loadMore"
          force-use-infinite-wrapper=".el-table__body-wrapper"
          :distance="20"
        >
        </infinite-loading>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="teamDojoApp.activity.delete.question" data-cy="activityDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-activity-heading" v-text="$t('teamDojoApp.activity.delete.question', { id: removeId })">
          Are you sure you want to delete this Activity?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-activity"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeActivity()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./activity.component.ts"></script>
