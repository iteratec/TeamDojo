<template>
  <div>
    <h2 id="page-heading" data-cy="OrganisationHeading">
      <span v-text="$t('teamDojoApp.organisation.home.title')" id="organisation-heading">Organisations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.organisation.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'OrganisationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-organisation"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.organisation.home.createLabel')"> Create a new Organisation </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && organisations && organisations.length === 0">
      <span v-text="$t('teamDojoApp.organisation.home.notFound')">No organisations found</span>
    </div>
    <div class="table-responsive" v-if="organisations && organisations.length > 0">
      <table class="table table-striped" aria-describedby="organisations">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.organisation.title')">Title</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.organisation.description')">Description</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.organisation.levelUpScore')">Level Up Score</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.organisation.applicationMode')">Application Mode</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.organisation.countOfConfirmations')">Count Of Confirmations</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.organisation.createdAt')">Created At</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.organisation.updatedAt')">Updated At</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.organisation.parent')">Parent</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="organisation in organisations" :key="organisation.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrganisationView', params: { organisationId: organisation.id } }">{{
                organisation.id
              }}</router-link>
            </td>
            <td>{{ organisation.title }}</td>
            <td>{{ organisation.description }}</td>
            <td>{{ organisation.levelUpScore }}</td>
            <td v-text="$t('teamDojoApp.ApplicationMode.' + organisation.applicationMode)">{{ organisation.applicationMode }}</td>
            <td>{{ organisation.countOfConfirmations }}</td>
            <td>{{ organisation.createdAt ? $d(Date.parse(organisation.createdAt), 'short') : '' }}</td>
            <td>{{ organisation.updatedAt ? $d(Date.parse(organisation.updatedAt), 'short') : '' }}</td>
            <td>
              <div v-if="organisation.parent">
                <router-link :to="{ name: 'OrganisationView', params: { organisationId: organisation.parent.id } }">{{
                  organisation.parent.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'OrganisationView', params: { organisationId: organisation.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'OrganisationEdit', params: { organisationId: organisation.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(organisation)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="teamDojoApp.organisation.delete.question" data-cy="organisationDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-organisation-heading" v-text="$t('teamDojoApp.organisation.delete.question', { id: removeId })">
          Are you sure you want to delete this Organisation?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-organisation"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeOrganisation()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./organisation.component.ts"></script>
