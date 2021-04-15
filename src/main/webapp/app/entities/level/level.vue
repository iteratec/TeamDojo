<template>
  <div>
    <h2 id="page-heading" data-cy="LevelHeading">
      <span v-text="$t('teamDojoApp.level.home.title')" id="level-heading">Levels</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.level.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LevelCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-level"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.level.home.createLabel')"> Create a new Level </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && levels && levels.length === 0">
      <span v-text="$t('teamDojoApp.level.home.notFound')">No levels found</span>
    </div>
    <div class="table-responsive" v-if="levels && levels.length > 0">
      <table class="table table-striped" aria-describedby="levels">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('teamDojoApp.level.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('teamDojoApp.level.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('requiredScore')">
              <span v-text="$t('teamDojoApp.level.requiredScore')">Required Score</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'requiredScore'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('instantMultiplier')">
              <span v-text="$t('teamDojoApp.level.instantMultiplier')">Instant Multiplier</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'instantMultiplier'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('completionBonus')">
              <span v-text="$t('teamDojoApp.level.completionBonus')">Completion Bonus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'completionBonus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('teamDojoApp.level.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span v-text="$t('teamDojoApp.level.updatedAt')">Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dependsOn.title')">
              <span v-text="$t('teamDojoApp.level.dependsOn')">Depends On</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dependsOn.title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('image.title')">
              <span v-text="$t('teamDojoApp.level.image')">Image</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image.title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dimension.title')">
              <span v-text="$t('teamDojoApp.level.dimension')">Dimension</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dimension.title'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="level in levels" :key="level.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LevelView', params: { levelId: level.id } }">{{ level.id }}</router-link>
            </td>
            <td>{{ level.title }}</td>
            <td>{{ level.description }}</td>
            <td>{{ level.requiredScore }}</td>
            <td>{{ level.instantMultiplier }}</td>
            <td>{{ level.completionBonus }}</td>
            <td>{{ level.createdAt ? $d(Date.parse(level.createdAt), 'short') : '' }}</td>
            <td>{{ level.updatedAt ? $d(Date.parse(level.updatedAt), 'short') : '' }}</td>
            <td>
              <div v-if="level.dependsOn">
                <router-link :to="{ name: 'LevelView', params: { levelId: level.dependsOn.id } }">{{ level.dependsOn.title }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="level.image">
                <router-link :to="{ name: 'ImageView', params: { imageId: level.image.id } }">{{ level.image.title }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="level.dimension">
                <router-link :to="{ name: 'DimensionView', params: { dimensionId: level.dimension.id } }">{{
                  level.dimension.title
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'LevelView', params: { levelId: level.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'LevelEdit', params: { levelId: level.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(level)"
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
        ><span id="teamDojoApp.level.delete.question" data-cy="levelDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-level-heading" v-text="$t('teamDojoApp.level.delete.question', { id: removeId })">
          Are you sure you want to delete this Level?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-level"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLevel()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./level.component.ts"></script>
