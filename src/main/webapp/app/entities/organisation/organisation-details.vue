<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="organisation">
        <h2 class="jh-entity-heading" data-cy="organisationDetailsHeading">
          <span v-text="$t('teamDojoApp.organisation.detail.title')">Organisation</span> {{ organisation.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.organisation.title')">Title</span>
          </dt>
          <dd>
            <span>{{ organisation.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.organisation.description')">Description</span>
          </dt>
          <dd>
            <span>{{ organisation.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.organisation.levelUpScore')">Level Up Score</span>
          </dt>
          <dd>
            <span>{{ organisation.levelUpScore }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.organisation.applicationMode')">Application Mode</span>
          </dt>
          <dd>
            <span v-text="$t('teamDojoApp.ApplicationMode.' + organisation.applicationMode)">{{ organisation.applicationMode }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.organisation.countOfConfirmations')">Count Of Confirmations</span>
          </dt>
          <dd>
            <span>{{ organisation.countOfConfirmations }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.organisation.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="organisation.createdAt">{{ $d(Date.parse(organisation.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.organisation.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="organisation.updatedAt">{{ $d(Date.parse(organisation.updatedAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.organisation.parent')">Parent</span>
          </dt>
          <dd>
            <div v-if="organisation.parent">
              <router-link :to="{ name: 'OrganisationView', params: { organisationId: organisation.parent.id } }">{{
                organisation.parent.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="organisation.id"
          :to="{ name: 'OrganisationEdit', params: { organisationId: organisation.id } }"
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

<script lang="ts" src="./organisation-details.component.ts"></script>
