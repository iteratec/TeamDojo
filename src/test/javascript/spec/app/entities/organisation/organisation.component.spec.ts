/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OrganisationComponent from '@/entities/organisation/organisation.vue';
import OrganisationClass from '@/entities/organisation/organisation.component';
import OrganisationService from '@/entities/organisation/organisation.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Organisation Management Component', () => {
    let wrapper: Wrapper<OrganisationClass>;
    let comp: OrganisationClass;
    let organisationServiceStub: SinonStubbedInstance<OrganisationService>;

    beforeEach(() => {
      organisationServiceStub = sinon.createStubInstance<OrganisationService>(OrganisationService);
      organisationServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OrganisationClass>(OrganisationComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          organisationService: () => organisationServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      organisationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOrganisations();
      await comp.$nextTick();

      // THEN
      expect(organisationServiceStub.retrieve.called).toBeTruthy();
      expect(comp.organisations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      organisationServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOrganisation();
      await comp.$nextTick();

      // THEN
      expect(organisationServiceStub.delete.called).toBeTruthy();
      expect(organisationServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
