<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="training">
        <h2 class="jh-entity-heading" data-cy="trainingDetailsHeading">
          <span v-text="$t('teamDojoApp.training.detail.title')">Training</span> {{ training.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.training.title')">Title</span>
          </dt>
          <dd>
            <span>{{ training.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.training.description')">Description</span>
          </dt>
          <dd>
            <span>{{ training.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.training.contact')">Contact</span>
          </dt>
          <dd>
            <span>{{ training.contact }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.training.link')">Link</span>
          </dt>
          <dd>
            <span>{{ training.link }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.training.validUntil')">Valid Until</span>
          </dt>
          <dd>
            <span v-if="training.validUntil">{{ $d(Date.parse(training.validUntil), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.training.isOfficial')">Is Official</span>
          </dt>
          <dd>
            <span>{{ training.isOfficial }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.training.suggestedBy')">Suggested By</span>
          </dt>
          <dd>
            <span>{{ training.suggestedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.training.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="training.createdAt">{{ $d(Date.parse(training.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.training.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="training.updatedAt">{{ $d(Date.parse(training.updatedAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.training.skill')">Skill</span>
          </dt>
          <dd>
            <span v-for="(skill, i) in training.skills" :key="skill.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'SkillView', params: { skillId: skill.id } }">{{ skill.title }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="training.id" :to="{ name: 'TrainingEdit', params: { trainingId: training.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./training-details.component.ts"></script>
