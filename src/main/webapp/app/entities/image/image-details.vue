<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="image">
        <h2 class="jh-entity-heading" data-cy="imageDetailsHeading">
          <span v-text="$t('teamDojoApp.image.detail.title')">Image</span> {{ image.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('teamDojoApp.image.title')">Title</span>
          </dt>
          <dd>
            <span>{{ image.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.image.small')">Small</span>
          </dt>
          <dd>
            <div v-if="image.small">
              <a v-on:click="openFile(image.smallContentType, image.small)">
                <img v-bind:src="'data:' + image.smallContentType + ';base64,' + image.small" style="max-width: 100%" alt="image image" />
              </a>
              {{ image.smallContentType }}, {{ byteSize(image.small) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.image.medium')">Medium</span>
          </dt>
          <dd>
            <div v-if="image.medium">
              <a v-on:click="openFile(image.mediumContentType, image.medium)">
                <img v-bind:src="'data:' + image.mediumContentType + ';base64,' + image.medium" style="max-width: 100%" alt="image image" />
              </a>
              {{ image.mediumContentType }}, {{ byteSize(image.medium) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.image.large')">Large</span>
          </dt>
          <dd>
            <div v-if="image.large">
              <a v-on:click="openFile(image.largeContentType, image.large)">
                <img v-bind:src="'data:' + image.largeContentType + ';base64,' + image.large" style="max-width: 100%" alt="image image" />
              </a>
              {{ image.largeContentType }}, {{ byteSize(image.large) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.image.hash')">Hash</span>
          </dt>
          <dd>
            <span>{{ image.hash }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.image.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="image.createdAt">{{ $d(Date.parse(image.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('teamDojoApp.image.updatedAt')">Updated At</span>
          </dt>
          <dd>
            <span v-if="image.updatedAt">{{ $d(Date.parse(image.updatedAt), 'long') }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="image.id" :to="{ name: 'ImageEdit', params: { imageId: image.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./image-details.component.ts"></script>
