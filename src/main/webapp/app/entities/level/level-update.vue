<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.level.home.createOrEditLabel"
          data-cy="LevelCreateUpdateHeading"
          v-text="$t('teamDojoApp.level.home.createOrEditLabel')"
        >
          Create or edit a Level
        </h2>
        <div>
          <div class="form-group" v-if="level.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="level.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.title')" for="level-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="level-title"
              data-cy="title"
              :class="{ valid: !$v.level.title.$invalid, invalid: $v.level.title.$invalid }"
              v-model="$v.level.title.$model"
              required
            />
            <div v-if="$v.level.title.$anyDirty && $v.level.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.level.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.level.title.minLength" v-text="$t('entity.validation.minlength', { min: 3 })">
                This field is required to be at least 3 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.level.title.maxLength" v-text="$t('entity.validation.maxlength', { max: 50 })">
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.description')" for="level-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="level-description"
              data-cy="description"
              :class="{ valid: !$v.level.description.$invalid, invalid: $v.level.description.$invalid }"
              v-model="$v.level.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.requiredScore')" for="level-requiredScore"
              >Required Score</label
            >
            <input
              type="number"
              class="form-control"
              name="requiredScore"
              id="level-requiredScore"
              data-cy="requiredScore"
              :class="{ valid: !$v.level.requiredScore.$invalid, invalid: $v.level.requiredScore.$invalid }"
              v-model.number="$v.level.requiredScore.$model"
              required
            />
            <div v-if="$v.level.requiredScore.$anyDirty && $v.level.requiredScore.$invalid">
              <small class="form-text text-danger" v-if="!$v.level.requiredScore.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.level.requiredScore.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.level.requiredScore.max" v-text="$t('entity.validation.max', { max: 1 })">
                This field cannot be longer than 1 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.level.requiredScore.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.instantMultiplier')" for="level-instantMultiplier"
              >Instant Multiplier</label
            >
            <input
              type="number"
              class="form-control"
              name="instantMultiplier"
              id="level-instantMultiplier"
              data-cy="instantMultiplier"
              :class="{ valid: !$v.level.instantMultiplier.$invalid, invalid: $v.level.instantMultiplier.$invalid }"
              v-model.number="$v.level.instantMultiplier.$model"
              required
            />
            <div v-if="$v.level.instantMultiplier.$anyDirty && $v.level.instantMultiplier.$invalid">
              <small class="form-text text-danger" v-if="!$v.level.instantMultiplier.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.level.instantMultiplier.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.level.instantMultiplier.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.completionBonus')" for="level-completionBonus"
              >Completion Bonus</label
            >
            <input
              type="number"
              class="form-control"
              name="completionBonus"
              id="level-completionBonus"
              data-cy="completionBonus"
              :class="{ valid: !$v.level.completionBonus.$invalid, invalid: $v.level.completionBonus.$invalid }"
              v-model.number="$v.level.completionBonus.$model"
            />
            <div v-if="$v.level.completionBonus.$anyDirty && $v.level.completionBonus.$invalid">
              <small class="form-text text-danger" v-if="!$v.level.completionBonus.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.level.completionBonus.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.createdAt')" for="level-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="level-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.level.createdAt.$invalid, invalid: $v.level.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.level.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.level.createdAt.$anyDirty && $v.level.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.level.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.level.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.updatedAt')" for="level-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="level-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.level.updatedAt.$invalid, invalid: $v.level.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.level.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.level.updatedAt.$anyDirty && $v.level.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.level.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.level.updatedAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.dependsOn')" for="level-dependsOn">Depends On</label>
            <select class="form-control" id="level-dependsOn" data-cy="dependsOn" name="dependsOn" v-model="level.dependsOn">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="level.dependsOn && levelOption.id === level.dependsOn.id ? level.dependsOn : levelOption"
                v-for="levelOption in dependsOns"
                :key="levelOption.id"
              >
                {{ levelOption.title }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.image')" for="level-image">Image</label>
            <select class="form-control" id="level-image" data-cy="image" name="image" v-model="level.image">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="level.image && imageOption.id === level.image.id ? level.image : imageOption"
                v-for="imageOption in images"
                :key="imageOption.id"
              >
                {{ imageOption.title }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.level.dimension')" for="level-dimension">Dimension</label>
            <select class="form-control" id="level-dimension" data-cy="dimension" name="dimension" v-model="level.dimension" required>
              <option v-if="!level.dimension" v-bind:value="null" selected></option>
              <option
                v-bind:value="level.dimension && dimensionOption.id === level.dimension.id ? level.dimension : dimensionOption"
                v-for="dimensionOption in dimensions"
                :key="dimensionOption.id"
              >
                {{ dimensionOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.level.dimension.$anyDirty && $v.level.dimension.$invalid">
            <small class="form-text text-danger" v-if="!$v.level.dimension.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
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
            :disabled="$v.level.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./level-update.component.ts"></script>
