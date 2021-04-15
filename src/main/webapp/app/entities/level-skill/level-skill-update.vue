<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.levelSkill.home.createOrEditLabel"
          data-cy="LevelSkillCreateUpdateHeading"
          v-text="$t('teamDojoApp.levelSkill.home.createOrEditLabel')"
        >
          Create or edit a LevelSkill
        </h2>
        <div>
          <div class="form-group" v-if="levelSkill.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="levelSkill.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.levelSkill.skill')" for="level-skill-skill">Skill</label>
            <select class="form-control" id="level-skill-skill" data-cy="skill" name="skill" v-model="levelSkill.skill" required>
              <option v-if="!levelSkill.skill" v-bind:value="null" selected></option>
              <option
                v-bind:value="levelSkill.skill && skillOption.id === levelSkill.skill.id ? levelSkill.skill : skillOption"
                v-for="skillOption in skills"
                :key="skillOption.id"
              >
                {{ skillOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.levelSkill.skill.$anyDirty && $v.levelSkill.skill.$invalid">
            <small class="form-text text-danger" v-if="!$v.levelSkill.skill.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.levelSkill.level')" for="level-skill-level">Level</label>
            <select class="form-control" id="level-skill-level" data-cy="level" name="level" v-model="levelSkill.level" required>
              <option v-if="!levelSkill.level" v-bind:value="null" selected></option>
              <option
                v-bind:value="levelSkill.level && levelOption.id === levelSkill.level.id ? levelSkill.level : levelOption"
                v-for="levelOption in levels"
                :key="levelOption.id"
              >
                {{ levelOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.levelSkill.level.$anyDirty && $v.levelSkill.level.$invalid">
            <small class="form-text text-danger" v-if="!$v.levelSkill.level.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.levelSkill.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./level-skill-update.component.ts"></script>
