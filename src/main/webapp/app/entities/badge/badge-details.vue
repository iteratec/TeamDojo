<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="badge">
        <h2 class="jh-entity-heading" data-cy="badgeDetailsHeading">
          <span v-text="$t('teamDojoApp.badge.detail.title')">Badge</span> {{ badge.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.badge.title')">Title</span>
          </dt>
          <dd>
            <span>{{ badge.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.description')">Description</span>
          </dt>
          <dd>
            <span>{{ badge.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.availableUntil')">Available Until</span>
          </dt>
          <dd>
            <span v-if="badge.availableUntil">{{ $d(Date.parse(badge.availableUntil), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.availableAmount')">Available Amount</span>
          </dt>
          <dd>
            <span>{{ badge.availableAmount }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.requiredScore')">Required Score</span>
          </dt>
          <dd>
            <span>{{ badge.requiredScore }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.instantMultiplier')">Instant Multiplier</span>
          </dt>
          <dd>
            <span>{{ badge.instantMultiplier }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.completionBonus')">Completion Bonus</span>
          </dt>
          <dd>
            <span>{{ badge.completionBonus }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="badge.createdAt">{{ $d(Date.parse(badge.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="badge.updatedAt">{{ $d(Date.parse(badge.updatedAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.image')">Image</span>
          </dt>
          <dd>
            <div v-if="badge.image">
              <router-link :to="{ name: 'ImageView', params: { imageId: badge.image.id } }">{{ badge.image.title }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.badge.dimensions')">Dimensions</span>
          </dt>
          <dd>
            <span v-for="(dimensions, i) in badge.dimensions" :key="dimensions.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'DimensionView', params: { dimensionId: dimensions.id } }">{{ dimensions.title }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="badge.id" :to="{ name: 'BadgeEdit', params: { badgeId: badge.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./badge-details.component.ts"></script>
