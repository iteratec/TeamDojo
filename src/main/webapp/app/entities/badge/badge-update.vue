<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.badge.home.createOrEditLabel"
          data-cy="BadgeCreateUpdateHeading"
          v-text="$t('teamDojoApp.badge.home.createOrEditLabel')"
        >
          Create or edit a Badge
        </h2>
        <div>
          <div class="form-group" v-if="badge.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="badge.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.title')" for="badge-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="badge-title"
              data-cy="title"
              :class="{ valid: !$v.badge.title.$invalid, invalid: $v.badge.title.$invalid }"
              v-model="$v.badge.title.$model"
              required
            />
            <div v-if="$v.badge.title.$anyDirty && $v.badge.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.badge.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.badge.title.minLength" v-text="$t('entity.validation.minlength', { min: 2 })">
                This field is required to be at least 2 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.badge.title.maxLength" v-text="$t('entity.validation.maxlength', { max: 20 })">
                This field cannot be longer than 20 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.description')" for="badge-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="badge-description"
              data-cy="description"
              :class="{ valid: !$v.badge.description.$invalid, invalid: $v.badge.description.$invalid }"
              v-model="$v.badge.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.availableUntil')" for="badge-availableUntil"
              >Available Until</label
            >
            <div class="d-flex">
              <input
                id="badge-availableUntil"
                data-cy="availableUntil"
                type="datetime-local"
                class="form-control"
                name="availableUntil"
                :class="{ valid: !$v.badge.availableUntil.$invalid, invalid: $v.badge.availableUntil.$invalid }"
                :value="convertDateTimeFromServer($v.badge.availableUntil.$model)"
                @change="updateInstantField('availableUntil', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.availableAmount')" for="badge-availableAmount"
              >Available Amount</label
            >
            <input
              type="number"
              class="form-control"
              name="availableAmount"
              id="badge-availableAmount"
              data-cy="availableAmount"
              :class="{ valid: !$v.badge.availableAmount.$invalid, invalid: $v.badge.availableAmount.$invalid }"
              v-model.number="$v.badge.availableAmount.$model"
            />
            <div v-if="$v.badge.availableAmount.$anyDirty && $v.badge.availableAmount.$invalid">
              <small class="form-text text-danger" v-if="!$v.badge.availableAmount.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.badge.availableAmount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.requiredScore')" for="badge-requiredScore"
              >Required Score</label
            >
            <input
              type="number"
              class="form-control"
              name="requiredScore"
              id="badge-requiredScore"
              data-cy="requiredScore"
              :class="{ valid: !$v.badge.requiredScore.$invalid, invalid: $v.badge.requiredScore.$invalid }"
              v-model.number="$v.badge.requiredScore.$model"
              required
            />
            <div v-if="$v.badge.requiredScore.$anyDirty && $v.badge.requiredScore.$invalid">
              <small class="form-text text-danger" v-if="!$v.badge.requiredScore.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.badge.requiredScore.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.badge.requiredScore.max" v-text="$t('entity.validation.max', { max: 1 })">
                This field cannot be longer than 1 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.badge.requiredScore.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.instantMultiplier')" for="badge-instantMultiplier"
              >Instant Multiplier</label
            >
            <input
              type="number"
              class="form-control"
              name="instantMultiplier"
              id="badge-instantMultiplier"
              data-cy="instantMultiplier"
              :class="{ valid: !$v.badge.instantMultiplier.$invalid, invalid: $v.badge.instantMultiplier.$invalid }"
              v-model.number="$v.badge.instantMultiplier.$model"
              required
            />
            <div v-if="$v.badge.instantMultiplier.$anyDirty && $v.badge.instantMultiplier.$invalid">
              <small class="form-text text-danger" v-if="!$v.badge.instantMultiplier.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.badge.instantMultiplier.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.badge.instantMultiplier.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.completionBonus')" for="badge-completionBonus"
              >Completion Bonus</label
            >
            <input
              type="number"
              class="form-control"
              name="completionBonus"
              id="badge-completionBonus"
              data-cy="completionBonus"
              :class="{ valid: !$v.badge.completionBonus.$invalid, invalid: $v.badge.completionBonus.$invalid }"
              v-model.number="$v.badge.completionBonus.$model"
            />
            <div v-if="$v.badge.completionBonus.$anyDirty && $v.badge.completionBonus.$invalid">
              <small class="form-text text-danger" v-if="!$v.badge.completionBonus.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.badge.completionBonus.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.createdAt')" for="badge-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="badge-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.badge.createdAt.$invalid, invalid: $v.badge.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.badge.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.badge.createdAt.$anyDirty && $v.badge.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.badge.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.badge.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.updatedAt')" for="badge-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="badge-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.badge.updatedAt.$invalid, invalid: $v.badge.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.badge.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.badge.updatedAt.$anyDirty && $v.badge.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.badge.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.badge.updatedAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badge.image')" for="badge-image">Image</label>
            <select class="form-control" id="badge-image" data-cy="image" name="image" v-model="badge.image">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="badge.image && imageOption.id === badge.image.id ? badge.image : imageOption"
                v-for="imageOption in images"
                :key="imageOption.id"
              >
                {{ imageOption.title }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="$t('teamDojoApp.badge.dimensions')" for="badge-dimensions">Dimensions</label>
            <select
              class="form-control"
              id="badge-dimensions"
              data-cy="dimensions"
              multiple
              name="dimensions"
              v-if="badge.dimensions !== undefined"
              v-model="badge.dimensions"
            >
              <option
                v-bind:value="getSelected(badge.dimensions, dimensionOption)"
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
            :disabled="$v.badge.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./badge-update.component.ts"></script>
