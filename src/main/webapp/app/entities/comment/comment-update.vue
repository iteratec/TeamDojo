<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.comment.home.createOrEditLabel"
          data-cy="CommentCreateUpdateHeading"
          v-text="$t('teamDojoApp.comment.home.createOrEditLabel')"
        >
          Create or edit a Comment
        </h2>
        <div>
          <div class="form-group" v-if="comment.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="comment.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.comment.text')" for="comment-text">Text</label>
            <input
              type="text"
              class="form-control"
              name="text"
              id="comment-text"
              data-cy="text"
              :class="{ valid: !$v.comment.text.$invalid, invalid: $v.comment.text.$invalid }"
              v-model="$v.comment.text.$model"
              required
            />
            <div v-if="$v.comment.text.$anyDirty && $v.comment.text.$invalid">
              <small class="form-text text-danger" v-if="!$v.comment.text.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.comment.createdAt')" for="comment-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="comment-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.comment.createdAt.$invalid, invalid: $v.comment.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.comment.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.comment.createdAt.$anyDirty && $v.comment.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.comment.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.comment.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.comment.updatedAt')" for="comment-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="comment-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.comment.updatedAt.$invalid, invalid: $v.comment.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.comment.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.comment.updatedAt.$anyDirty && $v.comment.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.comment.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.comment.updatedAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.comment.team')" for="comment-team">Team</label>
            <select class="form-control" id="comment-team" data-cy="team" name="team" v-model="comment.team" required>
              <option v-if="!comment.team" v-bind:value="null" selected></option>
              <option
                v-bind:value="comment.team && teamOption.id === comment.team.id ? comment.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.shortTitle }}
              </option>
            </select>
          </div>
          <div v-if="$v.comment.team.$anyDirty && $v.comment.team.$invalid">
            <small class="form-text text-danger" v-if="!$v.comment.team.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.comment.skill')" for="comment-skill">Skill</label>
            <select class="form-control" id="comment-skill" data-cy="skill" name="skill" v-model="comment.skill" required>
              <option v-if="!comment.skill" v-bind:value="null" selected></option>
              <option
                v-bind:value="comment.skill && skillOption.id === comment.skill.id ? comment.skill : skillOption"
                v-for="skillOption in skills"
                :key="skillOption.id"
              >
                {{ skillOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.comment.skill.$anyDirty && $v.comment.skill.$invalid">
            <small class="form-text text-danger" v-if="!$v.comment.skill.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.comment.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./comment-update.component.ts"></script>
