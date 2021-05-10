<template>
  <div>
    <h2 id="page-heading" data-cy="SkillHeading">
      <span v-text="$t('teamDojoApp.skill.home.title')" id="skill-heading">Skills</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.skill.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SkillCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-skill"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.skill.home.createLabel')"> Create a new Skill </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && skills && skills.length === 0">
      <span v-text="$t('teamDojoApp.skill.home.notFound')">No skills found</span>
    </div>
    <div class="table-responsive" v-if="skills && skills.length > 0">
      <table class="table table-striped" aria-describedby="skills">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('teamDojoApp.skill.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('teamDojoApp.skill.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('implementation')">
              <span v-text="$t('teamDojoApp.skill.implementation')">Implementation</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'implementation'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('validation')">
              <span v-text="$t('teamDojoApp.skill.validation')">Validation</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'validation'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('expiryPeriod')">
              <span v-text="$t('teamDojoApp.skill.expiryPeriod')">Expiry Period</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expiryPeriod'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contact')">
              <span v-text="$t('teamDojoApp.skill.contact')">Contact</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contact'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('score')">
              <span v-text="$t('teamDojoApp.skill.score')">Score</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'score'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rateScore')">
              <span v-text="$t('teamDojoApp.skill.rateScore')">Rate Score</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rateScore'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rateCount')">
              <span v-text="$t('teamDojoApp.skill.rateCount')">Rate Count</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rateCount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('teamDojoApp.skill.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span v-text="$t('teamDojoApp.skill.updatedAt')">Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="skill in skills" :key="skill.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SkillView', params: { skillId: skill.id } }">{{ skill.id }}</router-link>
            </td>
            <td>{{ skill.title }}</td>
            <td>{{ skill.description }}</td>
            <td>{{ skill.implementation }}</td>
            <td>{{ skill.validation }}</td>
            <td>{{ skill.expiryPeriod }}</td>
            <td>{{ skill.contact }}</td>
            <td>{{ skill.score }}</td>
            <td>{{ skill.rateScore }}</td>
            <td>{{ skill.rateCount }}</td>
            <td>{{ skill.createdAt ? $d(Date.parse(skill.createdAt), 'short') : '' }}</td>
            <td>{{ skill.updatedAt ? $d(Date.parse(skill.updatedAt), 'short') : '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SkillView', params: { skillId: skill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SkillEdit', params: { skillId: skill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(skill)"
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
        ><span id="teamDojoApp.skill.delete.question" data-cy="skillDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-skill-heading" v-text="$t('teamDojoApp.skill.delete.question', { id: removeId })">
          Are you sure you want to delete this Skill?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-skill"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSkill()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./skill.component.ts"></script>
