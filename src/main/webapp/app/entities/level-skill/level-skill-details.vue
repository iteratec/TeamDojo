<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="levelSkill">
        <h2 class="jh-entity-heading" data-cy="levelSkillDetailsHeading">
          <span v-text="$t('teamDojoApp.levelSkill.detail.title')">LevelSkill</span> {{ levelSkill.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.levelSkill.skill')">Skill</span>
          </dt>
          <dd>
            <div v-if="levelSkill.skill">
              <router-link :to="{ name: 'SkillView', params: { skillId: levelSkill.skill.id } }">{{ levelSkill.skill.title }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.levelSkill.level')">Level</span>
          </dt>
          <dd>
            <div v-if="levelSkill.level">
              <router-link :to="{ name: 'LevelView', params: { levelId: levelSkill.level.id } }">{{ levelSkill.level.title }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="levelSkill.id"
          :to="{ name: 'LevelSkillEdit', params: { levelSkillId: levelSkill.id } }"
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

<script lang="ts" src="./level-skill-details.component.ts"></script>
