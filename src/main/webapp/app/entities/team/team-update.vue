<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.team.home.createOrEditLabel"
          data-cy="TeamCreateUpdateHeading"
          v-text="$t('teamDojoApp.team.home.createOrEditLabel')"
        >
          Create or edit a Team
        </h2>
        <div>
          <div class="form-group" v-if="team.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="team.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.title')" for="team-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="team-title"
              data-cy="title"
              :class="{ valid: !$v.team.title.$invalid, invalid: $v.team.title.$invalid }"
              v-model="$v.team.title.$model"
              required
            />
            <div v-if="$v.team.title.$anyDirty && $v.team.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.title.minLength" v-text="$t('entity.validation.minlength', { min: 2 })">
                This field is required to be at least 2 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.title.maxLength" v-text="$t('entity.validation.maxlength', { max: 50 })">
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.shortTitle')" for="team-shortTitle">Short Title</label>
            <input
              type="text"
              class="form-control"
              name="shortTitle"
              id="team-shortTitle"
              data-cy="shortTitle"
              :class="{ valid: !$v.team.shortTitle.$invalid, invalid: $v.team.shortTitle.$invalid }"
              v-model="$v.team.shortTitle.$model"
              required
            />
            <div v-if="$v.team.shortTitle.$anyDirty && $v.team.shortTitle.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.shortTitle.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.team.shortTitle.minLength"
                v-text="$t('entity.validation.minlength', { min: 2 })"
              >
                This field is required to be at least 2 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.team.shortTitle.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 20 })"
              >
                This field cannot be longer than 20 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.team.shortTitle.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Short Title' })"
              >
                This field should follow pattern for "Short Title".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.slogan')" for="team-slogan">Slogan</label>
            <input
              type="text"
              class="form-control"
              name="slogan"
              id="team-slogan"
              data-cy="slogan"
              :class="{ valid: !$v.team.slogan.$invalid, invalid: $v.team.slogan.$invalid }"
              v-model="$v.team.slogan.$model"
            />
            <div v-if="$v.team.slogan.$anyDirty && $v.team.slogan.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.team.slogan.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 255 })"
              >
                This field cannot be longer than 255 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.contact')" for="team-contact">Contact</label>
            <input
              type="text"
              class="form-control"
              name="contact"
              id="team-contact"
              data-cy="contact"
              :class="{ valid: !$v.team.contact.$invalid, invalid: $v.team.contact.$invalid }"
              v-model="$v.team.contact.$model"
            />
            <div v-if="$v.team.contact.$anyDirty && $v.team.contact.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.team.contact.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 255 })"
              >
                This field cannot be longer than 255 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.validUntil')" for="team-validUntil">Valid Until</label>
            <div class="d-flex">
              <input
                id="team-validUntil"
                data-cy="validUntil"
                type="datetime-local"
                class="form-control"
                name="validUntil"
                :class="{ valid: !$v.team.validUntil.$invalid, invalid: $v.team.validUntil.$invalid }"
                :value="convertDateTimeFromServer($v.team.validUntil.$model)"
                @change="updateInstantField('validUntil', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.pureTrainingTeam')" for="team-pureTrainingTeam"
              >Pure Training Team</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="pureTrainingTeam"
              id="team-pureTrainingTeam"
              data-cy="pureTrainingTeam"
              :class="{ valid: !$v.team.pureTrainingTeam.$invalid, invalid: $v.team.pureTrainingTeam.$invalid }"
              v-model="$v.team.pureTrainingTeam.$model"
              required
            />
            <div v-if="$v.team.pureTrainingTeam.$anyDirty && $v.team.pureTrainingTeam.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.pureTrainingTeam.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.official')" for="team-official">Official</label>
            <input
              type="checkbox"
              class="form-check"
              name="official"
              id="team-official"
              data-cy="official"
              :class="{ valid: !$v.team.official.$invalid, invalid: $v.team.official.$invalid }"
              v-model="$v.team.official.$model"
              required
            />
            <div v-if="$v.team.official.$anyDirty && $v.team.official.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.official.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.createdAt')" for="team-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="team-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.team.createdAt.$invalid, invalid: $v.team.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.team.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.team.createdAt.$anyDirty && $v.team.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.team.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.updatedAt')" for="team-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="team-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.team.updatedAt.$invalid, invalid: $v.team.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.team.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.team.updatedAt.$anyDirty && $v.team.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.team.updatedAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.team.image')" for="team-image">Image</label>
            <select class="form-control" id="team-image" data-cy="image" name="image" v-model="team.image">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="team.image && imageOption.id === team.image.id ? team.image : imageOption"
                v-for="imageOption in images"
                :key="imageOption.id"
              >
                {{ imageOption.title }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="$t('teamDojoApp.team.participations')" for="team-participations">Participations</label>
            <select
              class="form-control"
              id="team-participations"
              data-cy="participations"
              multiple
              name="participations"
              v-if="team.participations !== undefined"
              v-model="team.participations"
            >
              <option
                v-bind:value="getSelected(team.participations, dimensionOption)"
                v-for="dimensionOption in dimensions"
                :key="dimensionOption.id"
              >
                {{ dimensionOption.title }}
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
            :disabled="$v.team.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./team-update.component.ts"></script>
