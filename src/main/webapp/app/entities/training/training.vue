<template>
  <div>
    <h2 id="page-heading" data-cy="TrainingHeading">
      <span v-text="$t('teamDojoApp.training.home.title')" id="training-heading">Trainings</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.training.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TrainingCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-training"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.training.home.createLabel')"> Create a new Training </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && trainings && trainings.length === 0">
      <span v-text="$t('teamDojoApp.training.home.notFound')">No trainings found</span>
    </div>
    <div class="table-responsive" v-if="trainings && trainings.length > 0">
      <table class="table table-striped" aria-describedby="trainings">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('teamDojoApp.training.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('teamDojoApp.training.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contact')">
              <span v-text="$t('teamDojoApp.training.contact')">Contact</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contact'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('link')">
              <span v-text="$t('teamDojoApp.training.link')">Link</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'link'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('validUntil')">
              <span v-text="$t('teamDojoApp.training.validUntil')">Valid Until</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'validUntil'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isOfficial')">
              <span v-text="$t('teamDojoApp.training.isOfficial')">Is Official</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isOfficial'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('suggestedBy')">
              <span v-text="$t('teamDojoApp.training.suggestedBy')">Suggested By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'suggestedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('teamDojoApp.training.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span v-text="$t('teamDojoApp.training.updatedAt')">Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="training in trainings" :key="training.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TrainingView', params: { trainingId: training.id } }">{{ training.id }}</router-link>
            </td>
            <td>{{ training.title }}</td>
            <td>{{ training.description }}</td>
            <td>{{ training.contact }}</td>
            <td>{{ training.link }}</td>
            <td>{{ training.validUntil ? $d(Date.parse(training.validUntil), 'short') : '' }}</td>
            <td>{{ training.isOfficial }}</td>
            <td>{{ training.suggestedBy }}</td>
            <td>{{ training.createdAt ? $d(Date.parse(training.createdAt), 'short') : '' }}</td>
            <td>{{ training.updatedAt ? $d(Date.parse(training.updatedAt), 'short') : '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TrainingView', params: { trainingId: training.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TrainingEdit', params: { trainingId: training.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(training)"
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
        ><span id="teamDojoApp.training.delete.question" data-cy="trainingDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-training-heading" v-text="$t('teamDojoApp.training.delete.question', { id: removeId })">
          Are you sure you want to delete this Training?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-training"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTraining()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./training.component.ts"></script>
