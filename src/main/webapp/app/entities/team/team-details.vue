<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="team">
        <h2 class="jh-entity-heading" data-cy="teamDetailsHeading">
          <span v-text="$t('teamDojoApp.team.detail.title')">Team</span> {{ team.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.team.title')">Title</span>
          </dt>
          <dd>
            <span>{{ team.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.shortTitle')">Short Title</span>
          </dt>
          <dd>
            <span>{{ team.shortTitle }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.slogan')">Slogan</span>
          </dt>
          <dd>
            <span>{{ team.slogan }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.contact')">Contact</span>
          </dt>
          <dd>
            <span>{{ team.contact }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.validUntil')">Valid Until</span>
          </dt>
          <dd>
            <span v-if="team.validUntil">{{ $d(Date.parse(team.validUntil), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.pureTrainingTeam')">Pure Training Team</span>
          </dt>
          <dd>
            <span>{{ team.pureTrainingTeam }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.official')">Official</span>
          </dt>
          <dd>
            <span>{{ team.official }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="team.createdAt">{{ $d(Date.parse(team.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="team.updatedAt">{{ $d(Date.parse(team.updatedAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.image')">Image</span>
          </dt>
          <dd>
            <div v-if="team.image">
              <router-link :to="{ name: 'ImageView', params: { imageId: team.image.id } }">{{ team.image.title }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.team.participations')">Participations</span>
          </dt>
          <dd>
            <span v-for="(participations, i) in team.participations" :key="participations.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'DimensionView', params: { dimensionId: participations.id } }">{{
                participations.title
              }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="team.id" :to="{ name: 'TeamEdit', params: { teamId: team.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./team-details.component.ts"></script>
