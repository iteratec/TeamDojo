<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.image.home.createOrEditLabel"
          data-cy="ImageCreateUpdateHeading"
          v-text="$t('teamDojoApp.image.home.createOrEditLabel')"
        >
          Create or edit a Image
        </h2>
        <div>
          <div class="form-group" v-if="image.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="image.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.image.title')" for="image-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="image-title"
              data-cy="title"
              :class="{ valid: !$v.image.title.$invalid, invalid: $v.image.title.$invalid }"
              v-model="$v.image.title.$model"
              required
            />
            <div v-if="$v.image.title.$anyDirty && $v.image.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.image.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.image.title.minLength" v-text="$t('entity.validation.minlength', { min: 1 })">
                This field is required to be at least 1 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.image.title.maxLength" v-text="$t('entity.validation.maxlength', { max: 50 })">
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.image.small')" for="image-small">Small</label>
            <div>
              <img
                v-bind:src="'data:' + image.smallContentType + ';base64,' + image.small"
                style="max-height: 100px"
                v-if="image.small"
                alt="image image"
              />
              <div v-if="image.small" class="form-text text-danger clearfix">
                <span class="pull-left">{{ image.smallContentType }}, {{ byteSize(image.small) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('small', 'smallContentType', 'file_small')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_small"
                id="file_small"
                data-cy="small"
                v-on:change="setFileData($event, image, 'small', true)"
                accept="image/*"
                v-text="$t('entity.action.addimage')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="small"
              id="image-small"
              data-cy="small"
              :class="{ valid: !$v.image.small.$invalid, invalid: $v.image.small.$invalid }"
              v-model="$v.image.small.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="smallContentType"
              id="image-smallContentType"
              v-model="image.smallContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.image.medium')" for="image-medium">Medium</label>
            <div>
              <img
                v-bind:src="'data:' + image.mediumContentType + ';base64,' + image.medium"
                style="max-height: 100px"
                v-if="image.medium"
                alt="image image"
              />
              <div v-if="image.medium" class="form-text text-danger clearfix">
                <span class="pull-left">{{ image.mediumContentType }}, {{ byteSize(image.medium) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('medium', 'mediumContentType', 'file_medium')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_medium"
                id="file_medium"
                data-cy="medium"
                v-on:change="setFileData($event, image, 'medium', true)"
                accept="image/*"
                v-text="$t('entity.action.addimage')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="medium"
              id="image-medium"
              data-cy="medium"
              :class="{ valid: !$v.image.medium.$invalid, invalid: $v.image.medium.$invalid }"
              v-model="$v.image.medium.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="mediumContentType"
              id="image-mediumContentType"
              v-model="image.mediumContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.image.large')" for="image-large">Large</label>
            <div>
              <img
                v-bind:src="'data:' + image.largeContentType + ';base64,' + image.large"
                style="max-height: 100px"
                v-if="image.large"
                alt="image image"
              />
              <div v-if="image.large" class="form-text text-danger clearfix">
                <span class="pull-left">{{ image.largeContentType }}, {{ byteSize(image.large) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('large', 'largeContentType', 'file_large')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_large"
                id="file_large"
                data-cy="large"
                v-on:change="setFileData($event, image, 'large', true)"
                accept="image/*"
                v-text="$t('entity.action.addimage')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="large"
              id="image-large"
              data-cy="large"
              :class="{ valid: !$v.image.large.$invalid, invalid: $v.image.large.$invalid }"
              v-model="$v.image.large.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="largeContentType"
              id="image-largeContentType"
              v-model="image.largeContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.image.hash')" for="image-hash">Hash</label>
            <input
              type="text"
              class="form-control"
              name="hash"
              id="image-hash"
              data-cy="hash"
              :class="{ valid: !$v.image.hash.$invalid, invalid: $v.image.hash.$invalid }"
              v-model="$v.image.hash.$model"
            />
            <div v-if="$v.image.hash.$anyDirty && $v.image.hash.$invalid">
              <small class="form-text text-danger" v-if="!$v.image.hash.maxLength" v-text="$t('entity.validation.maxlength', { max: 32 })">
                This field cannot be longer than 32 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.image.createdAt')" for="image-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="image-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.image.createdAt.$invalid, invalid: $v.image.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.image.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.image.createdAt.$anyDirty && $v.image.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.image.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.image.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.image.updatedAt')" for="image-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="image-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.image.updatedAt.$invalid, invalid: $v.image.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.image.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.image.updatedAt.$anyDirty && $v.image.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.image.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.image.updatedAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.image.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./image-update.component.ts"></script>
