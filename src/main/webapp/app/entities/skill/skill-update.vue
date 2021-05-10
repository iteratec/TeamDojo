<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.skill.home.createOrEditLabel"
          data-cy="SkillCreateUpdateHeading"
          v-text="$t('teamDojoApp.skill.home.createOrEditLabel')"
        >
          Create or edit a Skill
        </h2>
        <div>
          <div class="form-group" v-if="skill.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="skill.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.title')" for="skill-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="skill-title"
              data-cy="title"
              :class="{ valid: !$v.skill.title.$invalid, invalid: $v.skill.title.$invalid }"
              v-model="$v.skill.title.$model"
              required
            />
            <div v-if="$v.skill.title.$anyDirty && $v.skill.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.skill.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.title.minLength" v-text="$t('entity.validation.minlength', { min: 5 })">
                This field is required to be at least 5 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.title.maxLength" v-text="$t('entity.validation.maxlength', { max: 80 })">
                This field cannot be longer than 80 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.description')" for="skill-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="skill-description"
              data-cy="description"
              :class="{ valid: !$v.skill.description.$invalid, invalid: $v.skill.description.$invalid }"
              v-model="$v.skill.description.$model"
            />
            <div v-if="$v.skill.description.$anyDirty && $v.skill.description.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.skill.description.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 4096 })"
              >
                This field cannot be longer than 4096 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.implementation')" for="skill-implementation"
              >Implementation</label
            >
            <input
              type="text"
              class="form-control"
              name="implementation"
              id="skill-implementation"
              data-cy="implementation"
              :class="{ valid: !$v.skill.implementation.$invalid, invalid: $v.skill.implementation.$invalid }"
              v-model="$v.skill.implementation.$model"
            />
            <div v-if="$v.skill.implementation.$anyDirty && $v.skill.implementation.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.skill.implementation.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 4096 })"
              >
                This field cannot be longer than 4096 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.validation')" for="skill-validation">Validation</label>
            <input
              type="text"
              class="form-control"
              name="validation"
              id="skill-validation"
              data-cy="validation"
              :class="{ valid: !$v.skill.validation.$invalid, invalid: $v.skill.validation.$invalid }"
              v-model="$v.skill.validation.$model"
            />
            <div v-if="$v.skill.validation.$anyDirty && $v.skill.validation.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.skill.validation.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 4096 })"
              >
                This field cannot be longer than 4096 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.expiryPeriod')" for="skill-expiryPeriod">Expiry Period</label>
            <input
              type="number"
              class="form-control"
              name="expiryPeriod"
              id="skill-expiryPeriod"
              data-cy="expiryPeriod"
              :class="{ valid: !$v.skill.expiryPeriod.$invalid, invalid: $v.skill.expiryPeriod.$invalid }"
              v-model.number="$v.skill.expiryPeriod.$model"
            />
            <div v-if="$v.skill.expiryPeriod.$anyDirty && $v.skill.expiryPeriod.$invalid">
              <small class="form-text text-danger" v-if="!$v.skill.expiryPeriod.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.expiryPeriod.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.contact')" for="skill-contact">Contact</label>
            <input
              type="text"
              class="form-control"
              name="contact"
              id="skill-contact"
              data-cy="contact"
              :class="{ valid: !$v.skill.contact.$invalid, invalid: $v.skill.contact.$invalid }"
              v-model="$v.skill.contact.$model"
            />
            <div v-if="$v.skill.contact.$anyDirty && $v.skill.contact.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.skill.contact.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 255 })"
              >
                This field cannot be longer than 255 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.score')" for="skill-score">Score</label>
            <input
              type="number"
              class="form-control"
              name="score"
              id="skill-score"
              data-cy="score"
              :class="{ valid: !$v.skill.score.$invalid, invalid: $v.skill.score.$invalid }"
              v-model.number="$v.skill.score.$model"
              required
            />
            <div v-if="$v.skill.score.$anyDirty && $v.skill.score.$invalid">
              <small class="form-text text-danger" v-if="!$v.skill.score.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.score.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.score.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.rateScore')" for="skill-rateScore">Rate Score</label>
            <input
              type="number"
              class="form-control"
              name="rateScore"
              id="skill-rateScore"
              data-cy="rateScore"
              :class="{ valid: !$v.skill.rateScore.$invalid, invalid: $v.skill.rateScore.$invalid }"
              v-model.number="$v.skill.rateScore.$model"
            />
            <div v-if="$v.skill.rateScore.$anyDirty && $v.skill.rateScore.$invalid">
              <small class="form-text text-danger" v-if="!$v.skill.rateScore.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.rateScore.max" v-text="$t('entity.validation.max', { max: 5 })">
                This field cannot be longer than 5 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.rateScore.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.rateCount')" for="skill-rateCount">Rate Count</label>
            <input
              type="number"
              class="form-control"
              name="rateCount"
              id="skill-rateCount"
              data-cy="rateCount"
              :class="{ valid: !$v.skill.rateCount.$invalid, invalid: $v.skill.rateCount.$invalid }"
              v-model.number="$v.skill.rateCount.$model"
              required
            />
            <div v-if="$v.skill.rateCount.$anyDirty && $v.skill.rateCount.$invalid">
              <small class="form-text text-danger" v-if="!$v.skill.rateCount.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.rateCount.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.skill.rateCount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.createdAt')" for="skill-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="skill-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.skill.createdAt.$invalid, invalid: $v.skill.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.skill.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.skill.createdAt.$anyDirty && $v.skill.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.skill.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.skill.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.skill.updatedAt')" for="skill-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="skill-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.skill.updatedAt.$invalid, invalid: $v.skill.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.skill.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.skill.updatedAt.$anyDirty && $v.skill.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.skill.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.skill.updatedAt.ZonedDateTimelocal"
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
            :disabled="$v.skill.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./skill-update.component.ts"></script>
