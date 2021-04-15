<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="teamDojoApp.teamSkill.home.createOrEditLabel"
          data-cy="TeamSkillCreateUpdateHeading"
          v-text="$t('teamDojoApp.teamSkill.home.createOrEditLabel')"
        >
          Create or edit a TeamSkill
        </h2>
        <div>
          <div class="form-group" v-if="teamSkill.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="teamSkill.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.completedAt')" for="team-skill-completedAt"
              >Completed At</label
            >
            <div class="d-flex">
              <input
                id="team-skill-completedAt"
                data-cy="completedAt"
                type="datetime-local"
                class="form-control"
                name="completedAt"
                :class="{ valid: !$v.teamSkill.completedAt.$invalid, invalid: $v.teamSkill.completedAt.$invalid }"
                :value="convertDateTimeFromServer($v.teamSkill.completedAt.$model)"
                @change="updateInstantField('completedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.verifiedAt')" for="team-skill-verifiedAt"
              >Verified At</label
            >
            <div class="d-flex">
              <input
                id="team-skill-verifiedAt"
                data-cy="verifiedAt"
                type="datetime-local"
                class="form-control"
                name="verifiedAt"
                :class="{ valid: !$v.teamSkill.verifiedAt.$invalid, invalid: $v.teamSkill.verifiedAt.$invalid }"
                :value="convertDateTimeFromServer($v.teamSkill.verifiedAt.$model)"
                @change="updateInstantField('verifiedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.irrelevant')" for="team-skill-irrelevant">Irrelevant</label>
            <input
              type="checkbox"
              class="form-check"
              name="irrelevant"
              id="team-skill-irrelevant"
              data-cy="irrelevant"
              :class="{ valid: !$v.teamSkill.irrelevant.$invalid, invalid: $v.teamSkill.irrelevant.$invalid }"
              v-model="$v.teamSkill.irrelevant.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.note')" for="team-skill-note">Note</label>
            <input
              type="text"
              class="form-control"
              name="note"
              id="team-skill-note"
              data-cy="note"
              :class="{ valid: !$v.teamSkill.note.$invalid, invalid: $v.teamSkill.note.$invalid }"
              v-model="$v.teamSkill.note.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.vote')" for="team-skill-vote">Vote</label>
            <input
              type="number"
              class="form-control"
              name="vote"
              id="team-skill-vote"
              data-cy="vote"
              :class="{ valid: !$v.teamSkill.vote.$invalid, invalid: $v.teamSkill.vote.$invalid }"
              v-model.number="$v.teamSkill.vote.$model"
              required
            />
            <div v-if="$v.teamSkill.vote.$anyDirty && $v.teamSkill.vote.$invalid">
              <small class="form-text text-danger" v-if="!$v.teamSkill.vote.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.teamSkill.vote.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.voters')" for="team-skill-voters">Voters</label>
            <input
              type="text"
              class="form-control"
              name="voters"
              id="team-skill-voters"
              data-cy="voters"
              :class="{ valid: !$v.teamSkill.voters.$invalid, invalid: $v.teamSkill.voters.$invalid }"
              v-model="$v.teamSkill.voters.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.createdAt')" for="team-skill-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="team-skill-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.teamSkill.createdAt.$invalid, invalid: $v.teamSkill.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.teamSkill.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
            <div v-if="$v.teamSkill.createdAt.$anyDirty && $v.teamSkill.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.teamSkill.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.teamSkill.createdAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.updatedAt')" for="team-skill-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="team-skill-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.teamSkill.updatedAt.$invalid, invalid: $v.teamSkill.updatedAt.$invalid }"
                required
                :value="convertDateTimeFromServer($v.teamSkill.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
            <div v-if="$v.teamSkill.updatedAt.$anyDirty && $v.teamSkill.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.teamSkill.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.teamSkill.updatedAt.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.skill')" for="team-skill-skill">Skill</label>
            <select class="form-control" id="team-skill-skill" data-cy="skill" name="skill" v-model="teamSkill.skill" required>
              <option v-if="!teamSkill.skill" v-bind:value="null" selected></option>
              <option
                v-bind:value="teamSkill.skill && skillOption.id === teamSkill.skill.id ? teamSkill.skill : skillOption"
                v-for="skillOption in skills"
                :key="skillOption.id"
              >
                {{ skillOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.teamSkill.skill.$anyDirty && $v.teamSkill.skill.$invalid">
            <small class="form-text text-danger" v-if="!$v.teamSkill.skill.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('teamDojoApp.teamSkill.team')" for="team-skill-team">Team</label>
            <select class="form-control" id="team-skill-team" data-cy="team" name="team" v-model="teamSkill.team" required>
              <option v-if="!teamSkill.team" v-bind:value="null" selected></option>
              <option
                v-bind:value="teamSkill.team && teamOption.id === teamSkill.team.id ? teamSkill.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.title }}
              </option>
            </select>
          </div>
          <div v-if="$v.teamSkill.team.$anyDirty && $v.teamSkill.team.$invalid">
            <small class="form-text text-danger" v-if="!$v.teamSkill.team.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.teamSkill.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./team-skill-update.component.ts"></script>
