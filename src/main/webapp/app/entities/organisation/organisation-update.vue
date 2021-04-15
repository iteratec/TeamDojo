<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.organisation.home.createOrEditLabel"
          data-cy="OrganisationCreateUpdateHeading"
          v-text="$t('teamDojoApp.organisation.home.createOrEditLabel')"
        >
          Create or edit a Organisation
        </h2>
        <div>
          <div class="form-group" v-if="organisation.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="organisation.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.organisation.title')" for="organisation-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="organisation-title"
              data-cy="title"
              :class="{ valid: !$v.organisation.title.$invalid, invalid: $v.organisation.title.$invalid }"
              v-model="$v.organisation.title.$model"
              required
            />
            <div v-if="$v.organisation.title.$anyDirty && $v.organisation.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.organisation.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organisation.title.minLength"
                v-text="$t('entity.validation.minlength', { min: 1 })"
              >
                This field is required to be at least 1 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organisation.title.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.organisation.description')" for="organisation-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="organisation-description"
              data-cy="description"
              :class="{ valid: !$v.organisation.description.$invalid, invalid: $v.organisation.description.$invalid }"
              v-model="$v.organisation.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.organisation.levelUpScore')" for="organisation-levelUpScore"
              >Level Up Score</label
            >
            <input
              type="number"
              class="form-control"
              name="levelUpScore"
              id="organisation-levelUpScore"
              data-cy="levelUpScore"
              :class="{ valid: !$v.organisation.levelUpScore.$invalid, invalid: $v.organisation.levelUpScore.$invalid }"
              v-model.number="$v.organisation.levelUpScore.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.organisation.applicationMode')" for="organisation-applicationMode"
              >Application Mode</label
            >
            <select
              class="form-control"
              name="applicationMode"
              :class="{ valid: !$v.organisation.applicationMode.$invalid, invalid: $v.organisation.applicationMode.$invalid }"
              v-model="$v.organisation.applicationMode.$model"
              id="organisation-applicationMode"
              data-cy="applicationMode"
              required
            >
              <option value="PERSON" v-bind:label="$t('teamDojoApp.ApplicationMode.PERSON')">PERSON</option>
              <option value="TEAM" v-bind:label="$t('teamDojoApp.ApplicationMode.TEAM')">TEAM</option>
            </select>
            <div v-if="$v.organisation.applicationMode.$anyDirty && $v.organisation.applicationMode.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organisation.applicationMode.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('teamDojoApp.organisation.countOfConfirmations')"
              for="organisation-countOfConfirmations"
              >Count Of Confirmations</label
            >
            <input
              type="number"
              class="form-control"
              name="countOfConfirmations"
              id="organisation-countOfConfirmations"
              data-cy="countOfConfirmations"
              :class="{ valid: !$v.organisation.countOfConfirmations.$invalid, invalid: $v.organisation.countOfConfirmations.$invalid }"
              v-model.number="$v.organisation.countOfConfirmations.$model"
            />
            <div v-if="$v.organisation.countOfConfirmations.$anyDirty && $v.organisation.countOfConfirmations.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.organisation.countOfConfirmations.min"
                v-text="$t('entity.validation.min', { min: 0 })"
              >
                This field should be at least 0.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organisation.countOfConfirmations.numeric"
                v-text="$t('entity.validation.number')"
              >
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.organisation.createdAt')" for="organisation-createdAt"
              >Created At</label
            >
            <div class="d-flex">
              <input
                id="organisation-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.organisation.createdAt.$invalid, invalid: $v.organisation.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.organisation.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.organisation.createdAt.$anyDirty && $v.organisation.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.organisation.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organisation.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.organisation.updatedAt')" for="organisation-updatedAt"
              >Updated At</label
            >
            <div class="d-flex">
              <input
                id="organisation-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.organisation.updatedAt.$invalid, invalid: $v.organisation.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.organisation.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.organisation.updatedAt.$anyDirty && $v.organisation.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.organisation.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.organisation.updatedAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.organisation.parent')" for="organisation-parent">Parent</label>
            <select class="form-control" id="organisation-parent" data-cy="parent" name="parent" v-model="organisation.parent">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  organisation.parent && organisationOption.id === organisation.parent.id ? organisation.parent : organisationOption
                "
                v-for="organisationOption in organisations"
                :key="organisationOption.id"
              >
                {{ organisationOption.id }}
              </option>
            </select>
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
            :disabled="$v.organisation.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./organisation-update.component.ts"></script>
