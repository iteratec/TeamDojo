<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.activity.home.createOrEditLabel"
          data-cy="ActivityCreateUpdateHeading"
          v-text="$t('teamDojoApp.activity.home.createOrEditLabel')"
        >
          Create or edit a Activity
        </h2>
        <div>
          <div class="form-group" v-if="activity.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="activity.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.activity.type')" for="activity-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.activity.type.$invalid, invalid: $v.activity.type.$invalid }"
              v-model="$v.activity.type.$model"
              id="activity-type"
              data-cy="type"
            >
              <option value="SKILL_COMPLETED" v-bind:label="$t('teamDojoApp.ActivityType.SKILL_COMPLETED')">SKILL_COMPLETED</option>
              <option value="BADGE_CREATED" v-bind:label="$t('teamDojoApp.ActivityType.BADGE_CREATED')">BADGE_CREATED</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.activity.data')" for="activity-data">Data</label>
            <input
              type="text"
              class="form-control"
              name="data"
              id="activity-data"
              data-cy="data"
              :class="{ valid: !$v.activity.data.$invalid, invalid: $v.activity.data.$invalid }"
              v-model="$v.activity.data.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.activity.createdAt')" for="activity-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="activity-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.activity.createdAt.$invalid, invalid: $v.activity.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.activity.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.activity.createdAt.$anyDirty && $v.activity.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.activity.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.activity.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.activity.updatedAt')" for="activity-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="activity-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.activity.updatedAt.$invalid, invalid: $v.activity.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.activity.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.activity.updatedAt.$anyDirty && $v.activity.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.activity.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.activity.updatedAt.ZonedDateTimelocal"
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
            :disabled="$v.activity.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./activity-update.component.ts"></script>
