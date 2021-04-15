<template>
  <div>
    <h2 id="page-heading" data-cy="TeamSkillHeading">
      <span v-text="$t('teamDojoApp.teamSkill.home.title')" id="team-skill-heading">Team Skills</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.teamSkill.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TeamSkillCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-team-skill"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.teamSkill.home.createLabel')"> Create a new Team Skill </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && teamSkills && teamSkills.length === 0">
      <span v-text="$t('teamDojoApp.teamSkill.home.notFound')">No teamSkills found</span>
    </div>
    <div class="table-responsive" v-if="teamSkills && teamSkills.length > 0">
      <table class="table table-striped" aria-describedby="teamSkills">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('completedAt')">
              <span v-text="$t('teamDojoApp.teamSkill.completedAt')">Completed At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'completedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('verifiedAt')">
              <span v-text="$t('teamDojoApp.teamSkill.verifiedAt')">Verified At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'verifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('irrelevant')">
              <span v-text="$t('teamDojoApp.teamSkill.irrelevant')">Irrelevant</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'irrelevant'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('note')">
              <span v-text="$t('teamDojoApp.teamSkill.note')">Note</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'note'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('vote')">
              <span v-text="$t('teamDojoApp.teamSkill.vote')">Vote</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vote'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('voters')">
              <span v-text="$t('teamDojoApp.teamSkill.voters')">Voters</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'voters'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('teamDojoApp.teamSkill.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span v-text="$t('teamDojoApp.teamSkill.updatedAt')">Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('skill.title')">
              <span v-text="$t('teamDojoApp.teamSkill.skill')">Skill</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'skill.title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('team.title')">
              <span v-text="$t('teamDojoApp.teamSkill.team')">Team</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'team.title'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="teamSkill in teamSkills" :key="teamSkill.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TeamSkillView', params: { teamSkillId: teamSkill.id } }">{{ teamSkill.id }}</router-link>
            </td>
            <td>{{ teamSkill.completedAt ? $d(Date.parse(teamSkill.completedAt), 'short') : '' }}</td>
            <td>{{ teamSkill.verifiedAt ? $d(Date.parse(teamSkill.verifiedAt), 'short') : '' }}</td>
            <td>{{ teamSkill.irrelevant }}</td>
            <td>{{ teamSkill.note }}</td>
            <td>{{ teamSkill.vote }}</td>
            <td>{{ teamSkill.voters }}</td>
            <td>{{ teamSkill.createdAt ? $d(Date.parse(teamSkill.createdAt), 'short') : '' }}</td>
            <td>{{ teamSkill.updatedAt ? $d(Date.parse(teamSkill.updatedAt), 'short') : '' }}</td>
            <td>
              <div v-if="teamSkill.skill">
                <router-link :to="{ name: 'SkillView', params: { skillId: teamSkill.skill.id } }">{{ teamSkill.skill.title }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="teamSkill.team">
                <router-link :to="{ name: 'TeamView', params: { teamId: teamSkill.team.id } }">{{ teamSkill.team.title }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TeamSkillView', params: { teamSkillId: teamSkill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TeamSkillEdit', params: { teamSkillId: teamSkill.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(teamSkill)"
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
        ><span id="teamDojoApp.teamSkill.delete.question" data-cy="teamSkillDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-teamSkill-heading" v-text="$t('teamDojoApp.teamSkill.delete.question', { id: removeId })">
          Are you sure you want to delete this Team Skill?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-teamSkill"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTeamSkill()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./team-skill.component.ts"></script>
