<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.dimension.home.createOrEditLabel"
          data-cy="DimensionCreateUpdateHeading"
          v-text="$t('teamDojoApp.dimension.home.createOrEditLabel')"
        >
          Create or edit a Dimension
        </h2>
        <div>
          <div class="form-group" v-if="dimension.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="dimension.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.dimension.title')" for="dimension-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="dimension-title"
              data-cy="title"
              :class="{ valid: !$v.dimension.title.$invalid, invalid: $v.dimension.title.$invalid }"
              v-model="$v.dimension.title.$model"
              required
            />
            <div v-if="$v.dimension.title.$anyDirty && $v.dimension.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.dimension.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.dimension.title.minLength"
                v-text="$t('entity.validation.minlength', { min: 1 })"
              >
                This field is required to be at least 1 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.dimension.title.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.dimension.description')" for="dimension-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="dimension-description"
              data-cy="description"
              :class="{ valid: !$v.dimension.description.$invalid, invalid: $v.dimension.description.$invalid }"
              v-model="$v.dimension.description.$model"
            />
            <div v-if="$v.dimension.description.$anyDirty && $v.dimension.description.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.dimension.description.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 255 })"
              >
                This field cannot be longer than 255 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.dimension.createdAt')" for="dimension-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="dimension-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.dimension.createdAt.$invalid, invalid: $v.dimension.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.dimension.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.dimension.createdAt.$anyDirty && $v.dimension.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.dimension.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.dimension.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.dimension.updatedAt')" for="dimension-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="dimension-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.dimension.updatedAt.$invalid, invalid: $v.dimension.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.dimension.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.dimension.updatedAt.$anyDirty && $v.dimension.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.dimension.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.dimension.updatedAt.ZonedDateTimelocal"
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
            :disabled="$v.dimension.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./dimension-update.component.ts"></script>
