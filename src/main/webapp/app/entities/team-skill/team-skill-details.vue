<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="teamSkill">
        <h2 class="jh-entity-heading" data-cy="teamSkillDetailsHeading">
          <span v-text="$t('teamDojoApp.teamSkill.detail.title')">TeamSkill</span> {{ teamSkill.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.completedAt')">Completed At</span>
          </dt>
          <dd>
            <span v-if="teamSkill.completedAt">{{ $d(Date.parse(teamSkill.completedAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.verifiedAt')">Verified At</span>
          </dt>
          <dd>
            <span v-if="teamSkill.verifiedAt">{{ $d(Date.parse(teamSkill.verifiedAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.irrelevant')">Irrelevant</span>
          </dt>
          <dd>
            <span>{{ teamSkill.irrelevant }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.note')">Note</span>
          </dt>
          <dd>
            <span>{{ teamSkill.note }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.vote')">Vote</span>
          </dt>
          <dd>
            <span>{{ teamSkill.vote }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.voters')">Voters</span>
          </dt>
          <dd>
            <span>{{ teamSkill.voters }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="teamSkill.createdAt">{{ $d(Date.parse(teamSkill.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="teamSkill.updatedAt">{{ $d(Date.parse(teamSkill.updatedAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.skill')">Skill</span>
          </dt>
          <dd>
            <div v-if="teamSkill.skill">
              <router-link :to="{ name: 'SkillView', params: { skillId: teamSkill.skill.id } }">{{ teamSkill.skill.title }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.teamSkill.team')">Team</span>
          </dt>
          <dd>
            <div v-if="teamSkill.team">
              <router-link :to="{ name: 'TeamView', params: { teamId: teamSkill.team.id } }">{{ teamSkill.team.title }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="teamSkill.id"
          :to="{ name: 'TeamSkillEdit', params: { teamSkillId: teamSkill.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./team-skill-details.component.ts"></script>
