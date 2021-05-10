<template>
  <div>
    <h2 id="page-heading" data-cy="ReportHeading">
      <span v-text="$t('teamDojoApp.report.home.title')" id="report-heading">Reports</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('teamDojoApp.report.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ReportCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-report"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('teamDojoApp.report.home.createLabel')"> Create a new Report </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && reports && reports.length === 0">
      <span v-text="$t('teamDojoApp.report.home.notFound')">No reports found</span>
    </div>
    <div class="table-responsive" v-if="reports && reports.length > 0">
      <table class="table table-striped" aria-describedby="reports">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.report.title')">Title</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.report.description')">Description</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.report.type')">Type</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.report.createdAt')">Created At</span></th>
            <th scope="row"><span v-text="$t('teamDojoApp.report.updatedAt')">Updated At</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="report in reports" :key="report.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ReportView', params: { reportId: report.id } }">{{ report.id }}</router-link>
            </td>
            <td>{{ report.title }}</td>
            <td>{{ report.description }}</td>
            <td v-text="$t('teamDojoApp.ReportType.' + report.type)">{{ report.type }}</td>
            <td>{{ report.createdAt ? $d(Date.parse(report.createdAt), 'short') : '' }}</td>
            <td>{{ report.updatedAt ? $d(Date.parse(report.updatedAt), 'short') : '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ReportView', params: { reportId: report.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ReportEdit', params: { reportId: report.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(report)"
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
        ><span id="teamDojoApp.report.delete.question" data-cy="reportDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-report-heading" v-text="$t('teamDojoApp.report.delete.question', { id: removeId })">
          Are you sure you want to delete this Report?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-report"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeReport()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./report.component.ts"></script>
