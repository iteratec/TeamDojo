<template>
  <div>
    <h2 id="page-heading" data-cy="CommentHeading">
      <span v-text="$t('teamDojoApp.comment.home.title')" id="comment-heading">Comments</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.comment.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CommentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-comment"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.comment.home.createLabel')"> Create a new Comment </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && comments && comments.length === 0">
      <span v-text="$t('teamDojoApp.comment.home.notFound')">No comments found</span>
    </div>
    <div class="table-responsive" v-if="comments && comments.length > 0">
      <table class="table table-striped" aria-describedby="comments">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('text')">
              <span v-text="$t('teamDojoApp.comment.text')">Text</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'text'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('teamDojoApp.comment.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span v-text="$t('teamDojoApp.comment.updatedAt')">Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('team.shortTitle')">
              <span v-text="$t('teamDojoApp.comment.team')">Team</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'team.shortTitle'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('skill.title')">
              <span v-text="$t('teamDojoApp.comment.skill')">Skill</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'skill.title'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="comment in comments" :key="comment.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CommentView', params: { commentId: comment.id } }">{{ comment.id }}</router-link>
            </td>
            <td>{{ comment.text }}</td>
            <td>{{ comment.createdAt ? $d(Date.parse(comment.createdAt), 'short') : '' }}</td>
            <td>{{ comment.updatedAt ? $d(Date.parse(comment.updatedAt), 'short') : '' }}</td>
            <td>
              <div v-if="comment.team">
                <router-link :to="{ name: 'TeamView', params: { teamId: comment.team.id } }">{{ comment.team.shortTitle }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="comment.skill">
                <router-link :to="{ name: 'SkillView', params: { skillId: comment.skill.id } }">{{ comment.skill.title }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CommentView', params: { commentId: comment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CommentEdit', params: { commentId: comment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(comment)"
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
        ><span id="teamDojoApp.comment.delete.question" data-cy="commentDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-comment-heading" v-text="$t('teamDojoApp.comment.delete.question', { id: removeId })">
          Are you sure you want to delete this Comment?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-comment"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeComment()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./comment.component.ts"></script>
