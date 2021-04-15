<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="skill">
        <h2 class="jh-entity-heading" data-cy="skillDetailsHeading">
          <span v-text="$t('teamDojoApp.skill.detail.title')">Skill</span> {{ skill.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.skill.title')">Title</span>
          </dt>
          <dd>
            <span>{{ skill.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.description')">Description</span>
          </dt>
          <dd>
            <span>{{ skill.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.implementation')">Implementation</span>
          </dt>
          <dd>
            <span>{{ skill.implementation }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.validation')">Validation</span>
          </dt>
          <dd>
            <span>{{ skill.validation }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.expiryPeriod')">Expiry Period</span>
          </dt>
          <dd>
            <span>{{ skill.expiryPeriod }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.contact')">Contact</span>
          </dt>
          <dd>
            <span>{{ skill.contact }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.score')">Score</span>
          </dt>
          <dd>
            <span>{{ skill.score }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.rateScore')">Rate Score</span>
          </dt>
          <dd>
            <span>{{ skill.rateScore }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.rateCount')">Rate Count</span>
          </dt>
          <dd>
            <span>{{ skill.rateCount }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="skill.createdAt">{{ $d(Date.parse(skill.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.skill.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="skill.updatedAt">{{ $d(Date.parse(skill.updatedAt), 'long') }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="skill.id" :to="{ name: 'SkillEdit', params: { skillId: skill.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./skill-details.component.ts"></script>
