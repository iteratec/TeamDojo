<template>
  <div>
    <h2 id="page-heading" data-cy="BadgeHeading">
      <span v-text="$t('teamDojoApp.badge.home.title')" id="badge-heading">Badges</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.badge.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'BadgeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-badge"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.badge.home.createLabel')"> Create a new Badge </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && badges && badges.length === 0">
      <span v-text="$t('teamDojoApp.badge.home.notFound')">No badges found</span>
    </div>
    <div class="table-responsive" v-if="badges && badges.length > 0">
      <table class="table table-striped" aria-describedby="badges">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('teamDojoApp.badge.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('teamDojoApp.badge.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('availableUntil')">
              <span v-text="$t('teamDojoApp.badge.availableUntil')">Available Until</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'availableUntil'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('availableAmount')">
              <span v-text="$t('teamDojoApp.badge.availableAmount')">Available Amount</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'availableAmount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('requiredScore')">
              <span v-text="$t('teamDojoApp.badge.requiredScore')">Required Score</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'requiredScore'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('instantMultiplier')">
              <span v-text="$t('teamDojoApp.badge.instantMultiplier')">Instant Multiplier</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'instantMultiplier'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('completionBonus')">
              <span v-text="$t('teamDojoApp.badge.completionBonus')">Completion Bonus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'completionBonus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('teamDojoApp.badge.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span v-text="$t('teamDojoApp.badge.updatedAt')">Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('image.title')">
              <span v-text="$t('teamDojoApp.badge.image')">Image</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image.title'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="badge in badges" :key="badge.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BadgeView', params: { badgeId: badge.id } }">{{ badge.id }}</router-link>
            </td>
            <td>{{ badge.title }}</td>
            <td>{{ badge.description }}</td>
            <td>{{ badge.availableUntil ? $d(Date.parse(badge.availableUntil), 'short') : '' }}</td>
            <td>{{ badge.availableAmount }}</td>
            <td>{{ badge.requiredScore }}</td>
            <td>{{ badge.instantMultiplier }}</td>
            <td>{{ badge.completionBonus }}</td>
            <td>{{ badge.createdAt ? $d(Date.parse(badge.createdAt), 'short') : '' }}</td>
            <td>{{ badge.updatedAt ? $d(Date.parse(badge.updatedAt), 'short') : '' }}</td>
            <td>
              <div v-if="badge.image">
                <router-link :to="{ name: 'ImageView', params: { imageId: badge.image.id } }">{{ badge.image.title }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BadgeView', params: { badgeId: badge.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BadgeEdit', params: { badgeId: badge.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(badge)"
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
        ><span id="teamDojoApp.badge.delete.question" data-cy="badgeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-badge-heading" v-text="$t('teamDojoApp.badge.delete.question', { id: removeId })">
          Are you sure you want to delete this Badge?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-badge"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeBadge()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./badge.component.ts"></script>
