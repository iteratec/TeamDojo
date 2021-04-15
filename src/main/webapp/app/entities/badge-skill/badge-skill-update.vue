<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.badgeSkill.home.createOrEditLabel"
          data-cy="BadgeSkillCreateUpdateHeading"
          v-text="$t('teamDojoApp.badgeSkill.home.createOrEditLabel')"
        >
          Create or edit a BadgeSkill
        </h2>
        <div>
          <div class="form-group" v-if="badgeSkill.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="badgeSkill.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badgeSkill.badge')" for="badge-skill-badge">Badge</label>
            <select class="form-control" id="badge-skill-badge" data-cy="badge" name="badge" v-model="badgeSkill.badge" required>
              <option v-if="!badgeSkill.badge" v-bind:value="null" selected></option>
              <option
                v-bind:value="badgeSkill.badge && badgeOption.id === badgeSkill.badge.id ? badgeSkill.badge : badgeOption"
                v-for="badgeOption in badges"
                :key="badgeOption.id"
              >
                {{ badgeOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.badgeSkill.badge.$anyDirty && $v.badgeSkill.badge.$invalid">
            <small class="form-text text-danger" v-if="!$v.badgeSkill.badge.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.badgeSkill.skill')" for="badge-skill-skill">Skill</label>
            <select class="form-control" id="badge-skill-skill" data-cy="skill" name="skill" v-model="badgeSkill.skill" required>
              <option v-if="!badgeSkill.skill" v-bind:value="null" selected></option>
              <option
                v-bind:value="badgeSkill.skill && skillOption.id === badgeSkill.skill.id ? badgeSkill.skill : skillOption"
                v-for="skillOption in skills"
                :key="skillOption.id"
              >
                {{ skillOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.badgeSkill.skill.$anyDirty && $v.badgeSkill.skill.$invalid">
            <small class="form-text text-danger" v-if="!$v.badgeSkill.skill.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.badgeSkill.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./badge-skill-update.component.ts"></script>
