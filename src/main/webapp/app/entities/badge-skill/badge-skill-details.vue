<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="badgeSkill">
        <h2 class="jh-entity-heading" data-cy="badgeSkillDetailsHeading">
          <span v-text="$t('teamDojoApp.badgeSkill.detail.title')">BadgeSkill</span> {{ badgeSkill.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.badgeSkill.badge')">Badge</span>
          </dt>
          <dd>
            <div v-if="badgeSkill.badge">
              <router-link :to="{ name: 'BadgeView', params: { badgeId: badgeSkill.badge.id } }">{{ badgeSkill.badge.title }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badgeSkill.skill')">Skill</span>
          </dt>
          <dd>
            <div v-if="badgeSkill.skill">
              <router-link :to="{ name: 'SkillView', params: { skillId: badgeSkill.skill.id } }">{{ badgeSkill.skill.title }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="badgeSkill.id"
          :to="{ name: 'BadgeSkillEdit', params: { badgeSkillId: badgeSkill.id } }"
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

<script lang="ts" src="./badge-skill-details.component.ts"></script>
