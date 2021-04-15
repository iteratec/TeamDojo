<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="level">
        <h2 class="jh-entity-heading" data-cy="levelDetailsHeading">
          <span v-text="$t('teamDojoApp.level.detail.title')">Level</span> {{ level.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.level.title')">Title</span>
          </dt>
          <dd>
            <span>{{ level.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.level.description')">Description</span>
          </dt>
          <dd>
            <span>{{ level.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.level.requiredScore')">Required Score</span>
          </dt>
          <dd>
            <span>{{ level.requiredScore }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.level.instantMultiplier')">Instant Multiplier</span>
          </dt>
          <dd>
            <span>{{ level.instantMultiplier }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.level.completionBonus')">Completion Bonus</span>
          </dt>
          <dd>
            <span>{{ level.completionBonus }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.level.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="level.createdAt">{{ $d(Date.parse(level.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.level.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="level.updatedAt">{{ $d(Date.parse(level.updatedAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.level.dependsOn')">Depends On</span>
          </dt>
          <dd>
            <div v-if="level.dependsOn">
              <router-link :to="{ name: 'LevelView', params: { levelId: level.dependsOn.id } }">{{ level.dependsOn.title }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.level.image')">Image</span>
          </dt>
          <dd>
            <div v-if="level.image">
              <router-link :to="{ name: 'ImageView', params: { imageId: level.image.id } }">{{ level.image.title }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.level.dimension')">Dimension</span>
          </dt>
          <dd>
            <div v-if="level.dimension">
              <router-link :to="{ name: 'DimensionView', params: { dimensionId: level.dimension.id } }">{{
                level.dimension.title
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="level.id" :to="{ name: 'LevelEdit', params: { levelId: level.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./level-details.component.ts"></script>
