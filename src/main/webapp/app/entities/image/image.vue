<template>
  <div>
    <h2 id="page-heading" data-cy="ImageHeading">
      <span v-text="$t('teamDojoApp.image.home.title')" id="image-heading">Images</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.image.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ImageCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-image"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.image.home.createLabel')"> Create a new Image </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && images && images.length === 0">
      <span v-text="$t('teamDojoApp.image.home.notFound')">No images found</span>
    </div>
    <div class="table-responsive" v-if="images && images.length > 0">
      <table class="table table-striped" aria-describedby="images">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('teamDojoApp.image.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('small')">
              <span v-text="$t('teamDojoApp.image.small')">Small</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'small'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('medium')">
              <span v-text="$t('teamDojoApp.image.medium')">Medium</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'medium'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('large')">
              <span v-text="$t('teamDojoApp.image.large')">Large</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'large'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hash')">
              <span v-text="$t('teamDojoApp.image.hash')">Hash</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hash'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="$t('teamDojoApp.image.createdAt')">Created At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updatedAt')">
              <span v-text="$t('teamDojoApp.image.updatedAt')">Updated At</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="image in images" :key="image.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ImageView', params: { imageId: image.id } }">{{ image.id }}</router-link>
            </td>
            <td>{{ image.title }}</td>
            <td>
              <a v-if="image.small" v-on:click="openFile(image.smallContentType, image.small)">
                <img v-bind:src="'data:' + image.smallContentType + ';base64,' + image.small" style="max-height: 30px" alt="image image" />
              </a>
              <span v-if="image.small">{{ image.smallContentType }}, {{ byteSize(image.small) }}</span>
            </td>
            <td>
              <a v-if="image.medium" v-on:click="openFile(image.mediumContentType, image.medium)">
                <img
                  v-bind:src="'data:' + image.mediumContentType + ';base64,' + image.medium"
                  style="max-height: 30px"
                  alt="image image"
                />
              </a>
              <span v-if="image.medium">{{ image.mediumContentType }}, {{ byteSize(image.medium) }}</span>
            </td>
            <td>
              <a v-if="image.large" v-on:click="openFile(image.largeContentType, image.large)">
                <img v-bind:src="'data:' + image.largeContentType + ';base64,' + image.large" style="max-height: 30px" alt="image image" />
              </a>
              <span v-if="image.large">{{ image.largeContentType }}, {{ byteSize(image.large) }}</span>
            </td>
            <td>{{ image.hash }}</td>
            <td>{{ image.createdAt ? $d(Date.parse(image.createdAt), 'short') : '' }}</td>
            <td>{{ image.updatedAt ? $d(Date.parse(image.updatedAt), 'short') : '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ImageView', params: { imageId: image.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ImageEdit', params: { imageId: image.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(image)"
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
        ><span id="teamDojoApp.image.delete.question" data-cy="imageDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-image-heading" v-text="$t('teamDojoApp.image.delete.question', { id: removeId })">
          Are you sure you want to delete this Image?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-image"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeImage()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./image.component.ts"></script>
