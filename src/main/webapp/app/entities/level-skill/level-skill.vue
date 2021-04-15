<template>
  <div>
    <h2 id="page-heading" data-cy="LevelSkillHeading">
      <span v-text="$t('teamDojoApp.levelSkill.home.title')" id="level-skill-heading">Level Skills</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.levelSkill.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LevelSkillCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-level-skill"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.levelSkill.home.createLabel')"> Create a new Level Skill </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && levelSkills && levelSkills.length === 0">
      <span v-text="$t('teamDojoApp.levelSkill.home.notFound')">No levelSkills found</span>
    </div>
    <div class="table-responsive" v-if="levelSkills && levelSkills.length > 0">
      <table class="table table-striped" aria-describedby="levelSkills">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('skill.title')">
              <span v-text="$t('teamDojoApp.levelSkill.skill')">Skill</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'skill.title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('level.title')">
              <span v-text="$t('teamDojoApp.levelSkill.level')">Level</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'level.title'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="levelSkill in levelSkills" :key="levelSkill.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LevelSkillView', params: { levelSkillId: levelSkill.id } }">{{ levelSkill.id }}</router-link>
            </td>
            <td>
              <div v-if="levelSkill.skill">
                <router-link :to="{ name: 'SkillView', params: { skillId: levelSkill.skill.id } }">{{
                  levelSkill.skill.title
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="levelSkill.level">
                <router-link :to="{ name: 'LevelView', params: { levelId: levelSkill.level.id } }">{{
                  levelSkill.level.title
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'LevelSkillView', params: { levelSkillId: levelSkill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'LevelSkillEdit', params: { levelSkillId: levelSkill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(levelSkill)"
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
        ><span id="teamDojoApp.levelSkill.delete.question" data-cy="levelSkillDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-levelSkill-heading" v-text="$t('teamDojoApp.levelSkill.delete.question', { id: removeId })">
          Are you sure you want to delete this Level Skill?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-levelSkill"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLevelSkill()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./level-skill.component.ts"></script>
