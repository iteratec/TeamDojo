<template>
  <div>
    <h2 id="page-heading" data-cy="TeamHeading">
      <span v-text="$t('teamDojoApp.team.home.title')" id="team-heading">Teams</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.team.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TeamCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-team">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.team.home.createLabel')"> Create a new Team </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && teams && teams.length === 0">
      <span v-text="$t('teamDojoApp.team.home.notFound')">No teams found</span>
    </div>
    <div class="table-responsive" v-if="teams && teams.length > 0">
      <table class="table table-striped" aria-describedby="teams">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('teamDojoApp.team.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('shortTitle')">
              <span v-text="$t('teamDojoApp.team.shortTitle')">Short Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'shortTitle'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('slogan')">
              <span v-text="$t('teamDojoApp.team.slogan')">Slogan</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'slogan'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contact')">
              <span v-text="$t('teamDojoApp.team.contact')">Contact</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contact'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('validUntil')">
              <span v-text="$t('teamDojoApp.team.validUntil')">Valid Until</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'validUntil'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pureTrainingTeam')">
              <span v-text="$t('teamDojoApp.team.pureTrainingTeam')">Pure Training Team</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pureTrainingTeam'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('official')">
              <span v-text="$t('teamDojoApp.team.official')">Official</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'official'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('teamDojoApp.team.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span v-text="$t('teamDojoApp.team.updatedAt')">Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('image.title')">
              <span v-text="$t('teamDojoApp.team.image')">Image</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image.title'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="team in teams" :key="team.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TeamView', params: { teamId: team.id } }">{{ team.id }}</router-link>
            </td>
            <td>{{ team.title }}</td>
            <td>{{ team.shortTitle }}</td>
            <td>{{ team.slogan }}</td>
            <td>{{ team.contact }}</td>
            <td>{{ team.validUntil ? $d(Date.parse(team.validUntil), 'short') : '' }}</td>
            <td>{{ team.pureTrainingTeam }}</td>
            <td>{{ team.official }}</td>
            <td>{{ team.createdAt ? $d(Date.parse(team.createdAt), 'short') : '' }}</td>
            <td>{{ team.updatedAt ? $d(Date.parse(team.updatedAt), 'short') : '' }}</td>
            <td>
              <div v-if="team.image">
                <router-link :to="{ name: 'ImageView', params: { imageId: team.image.id } }">{{ team.image.title }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TeamView', params: { teamId: team.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TeamEdit', params: { teamId: team.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(team)"
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
        ><span id="teamDojoApp.team.delete.question" data-cy="teamDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-team-heading" v-text="$t('teamDojoApp.team.delete.question', { id: removeId })">
          Are you sure you want to delete this Team?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-team"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTeam()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./team.component.ts"></script>
