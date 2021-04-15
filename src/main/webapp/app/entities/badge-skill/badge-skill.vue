<template>
  <div>
    <h2 id="page-heading" data-cy="BadgeSkillHeading">
      <span v-text="$t('teamDojoApp.badgeSkill.home.title')" id="badge-skill-heading">Badge Skills</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.badgeSkill.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'BadgeSkillCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-badge-skill"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.badgeSkill.home.createLabel')"> Create a new Badge Skill </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && badgeSkills && badgeSkills.length === 0">
      <span v-text="$t('teamDojoApp.badgeSkill.home.notFound')">No badgeSkills found</span>
    </div>
    <div class="table-responsive" v-if="badgeSkills && badgeSkills.length > 0">
      <table class="table table-striped" aria-describedby="badgeSkills">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('badge.title')">
              <span v-text="$t('teamDojoApp.badgeSkill.badge')">Badge</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'badge.title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('skill.title')">
              <span v-text="$t('teamDojoApp.badgeSkill.skill')">Skill</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'skill.title'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="badgeSkill in badgeSkills" :key="badgeSkill.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BadgeSkillView', params: { badgeSkillId: badgeSkill.id } }">{{ badgeSkill.id }}</router-link>
            </td>
            <td>
              <div v-if="badgeSkill.badge">
                <router-link :to="{ name: 'BadgeView', params: { badgeId: badgeSkill.badge.id } }">{{
                  badgeSkill.badge.title
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="badgeSkill.skill">
                <router-link :to="{ name: 'SkillView', params: { skillId: badgeSkill.skill.id } }">{{
                  badgeSkill.skill.title
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BadgeSkillView', params: { badgeSkillId: badgeSkill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BadgeSkillEdit', params: { badgeSkillId: badgeSkill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(badgeSkill)"
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
        ><span id="teamDojoApp.badgeSkill.delete.question" data-cy="badgeSkillDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-badgeSkill-heading" v-text="$t('teamDojoApp.badgeSkill.delete.question', { id: removeId })">
          Are you sure you want to delete this Badge Skill?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-badgeSkill"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeBadgeSkill()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./badge-skill.component.ts"></script>
