<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.report.home.createOrEditLabel"
          data-cy="ReportCreateUpdateHeading"
          v-text="$t('teamDojoApp.report.home.createOrEditLabel')"
        >
          Create or edit a Report
        </h2>
        <div>
          <div class="form-group" v-if="report.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="report.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.report.title')" for="report-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="report-title"
              data-cy="title"
              :class="{ valid: !$v.report.title.$invalid, invalid: $v.report.title.$invalid }"
              v-model="$v.report.title.$model"
              required
            />
            <div v-if="$v.report.title.$anyDirty && $v.report.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.report.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.report.title.minLength" v-text="$t('entity.validation.minlength', { min: 1 })">
                This field is required to be at least 1 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.report.title.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.report.description')" for="report-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="report-description"
              data-cy="description"
              :class="{ valid: !$v.report.description.$invalid, invalid: $v.report.description.$invalid }"
              v-model="$v.report.description.$model"
              required
            />
            <div v-if="$v.report.description.$anyDirty && $v.report.description.$invalid">
              <small class="form-text text-danger" v-if="!$v.report.description.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.report.description.minLength"
                v-text="$t('entity.validation.minlength', { min: 1 })"
              >
                This field is required to be at least 1 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.report.description.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 4096 })"
              >
                This field cannot be longer than 4096 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.report.type')" for="report-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.report.type.$invalid, invalid: $v.report.type.$invalid }"
              v-model="$v.report.type.$model"
              id="report-type"
              data-cy="type"
              required
            >
              <option value="BUG" v-bind:label="$t('teamDojoApp.ReportType.BUG')">BUG</option>
              <option value="WISH" v-bind:label="$t('teamDojoApp.ReportType.WISH')">WISH</option>
              <option value="REVIEW" v-bind:label="$t('teamDojoApp.ReportType.REVIEW')">REVIEW</option>
              <option value="COMPLIMENT" v-bind:label="$t('teamDojoApp.ReportType.COMPLIMENT')">COMPLIMENT</option>
            </select>
            <div v-if="$v.report.type.$anyDirty && $v.report.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.report.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.report.createdAt')" for="report-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="report-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.report.createdAt.$invalid, invalid: $v.report.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.report.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.report.createdAt.$anyDirty && $v.report.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.report.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.report.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.report.updatedAt')" for="report-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="report-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.report.updatedAt.$invalid, invalid: $v.report.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.report.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.report.updatedAt.$anyDirty && $v.report.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.report.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.report.updatedAt.ZonedDateTimelocal"
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
            :disabled="$v.report.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./report-update.component.ts"></script>
