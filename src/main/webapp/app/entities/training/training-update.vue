<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.training.home.createOrEditLabel"
          data-cy="TrainingCreateUpdateHeading"
          v-text="$t('teamDojoApp.training.home.createOrEditLabel')"
        >
          Create or edit a Training
        </h2>
        <div>
          <div class="form-group" v-if="training.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="training.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.training.title')" for="training-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="training-title"
              data-cy="title"
              :class="{ valid: !$v.training.title.$invalid, invalid: $v.training.title.$invalid }"
              v-model="$v.training.title.$model"
              required
            />
            <div v-if="$v.training.title.$anyDirty && $v.training.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.training.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.training.title.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 80 })"
              >
                This field cannot be longer than 80 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.training.description')" for="training-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="training-description"
              data-cy="description"
              :class="{ valid: !$v.training.description.$invalid, invalid: $v.training.description.$invalid }"
              v-model="$v.training.description.$model"
            />
            <div v-if="$v.training.description.$anyDirty && $v.training.description.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.training.description.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.training.contact')" for="training-contact">Contact</label>
            <input
              type="text"
              class="form-control"
              name="contact"
              id="training-contact"
              data-cy="contact"
              :class="{ valid: !$v.training.contact.$invalid, invalid: $v.training.contact.$invalid }"
              v-model="$v.training.contact.$model"
            />
            <div v-if="$v.training.contact.$anyDirty && $v.training.contact.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.training.contact.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 255 })"
              >
                This field cannot be longer than 255 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.training.link')" for="training-link">Link</label>
            <input
              type="text"
              class="form-control"
              name="link"
              id="training-link"
              data-cy="link"
              :class="{ valid: !$v.training.link.$invalid, invalid: $v.training.link.$invalid }"
              v-model="$v.training.link.$model"
            />
            <div v-if="$v.training.link.$anyDirty && $v.training.link.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.training.link.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 255 })"
              >
                This field cannot be longer than 255 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.training.validUntil')" for="training-validUntil">Valid Until</label>
            <div class="d-flex">
              <input
                id="training-validUntil"
                data-cy="validUntil"
                type="datetime-local"
                class="form-control"
                name="validUntil"
                :class="{ valid: !$v.training.validUntil.$invalid, invalid: $v.training.validUntil.$invalid }"
                :value="convertDateTimeFromServer($v.training.validUntil.$model)"
                @change="updateInstantField('validUntil', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.training.isOfficial')" for="training-isOfficial">Is Official</label>
            <input
              type="checkbox"
              class="form-check"
              name="isOfficial"
              id="training-isOfficial"
              data-cy="isOfficial"
              :class="{ valid: !$v.training.isOfficial.$invalid, invalid: $v.training.isOfficial.$invalid }"
              v-model="$v.training.isOfficial.$model"
              required
            />
            <div v-if="$v.training.isOfficial.$anyDirty && $v.training.isOfficial.$invalid">
              <small class="form-text text-danger" v-if="!$v.training.isOfficial.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.training.suggestedBy')" for="training-suggestedBy"
              >Suggested By</label
            >
            <input
              type="text"
              class="form-control"
              name="suggestedBy"
              id="training-suggestedBy"
              data-cy="suggestedBy"
              :class="{ valid: !$v.training.suggestedBy.$invalid, invalid: $v.training.suggestedBy.$invalid }"
              v-model="$v.training.suggestedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.training.createdAt')" for="training-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="training-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.training.createdAt.$invalid, invalid: $v.training.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.training.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.training.createdAt.$anyDirty && $v.training.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.training.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.training.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.training.updatedAt')" for="training-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="training-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.training.updatedAt.$invalid, invalid: $v.training.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.training.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.training.updatedAt.$anyDirty && $v.training.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.training.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.training.updatedAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label v-text="$t('teamDojoApp.training.skill')" for="training-skill">Skill</label>
            <select
              class="form-control"
              id="training-skill"
              data-cy="skill"
              multiple
              name="skill"
              v-if="training.skills !== undefined"
              v-model="training.skills"
            >
              <option v-bind:value="getSelected(training.skills, skillOption)" v-for="skillOption in skills" :key="skillOption.id">
                {{ skillOption.title }}
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
            :disabled="$v.training.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./training-update.component.ts"></script>
