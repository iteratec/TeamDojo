/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OrganisationDetailComponent from '@/entities/organisation/organisation-details.vue';
import OrganisationClass from '@/entities/organisation/organisation-details.component';
import OrganisationService from '@/entities/organisation/organisation.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Organisation Management Detail Component', () => {
    let wrapper: Wrapper<OrganisationClass>;
    let comp: OrganisationClass;
    let organisationServiceStub: SinonStubbedInstance<OrganisationService>;

    beforeEach(() => {
      organisationServiceStub = sinon.createStubInstance<OrganisationService>(OrganisationService);

      wrapper = shallowMount<OrganisationClass>(OrganisationDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { organisationService: () => organisationServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOrganisation = { id: 123 };
        organisationServiceStub.find.resolves(foundOrganisation);

        // WHEN
        comp.retrieveOrganisation(123);
        await comp.$nextTick();

        // THEN
        expect(comp.organisation).toBe(foundOrganisation);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrganisation = { id: 123 };
        organisationServiceStub.find.resolves(foundOrganisation);

        // WHEN
        comp.beforeRouteEnter({ params: { organisationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.organisation).toBe(foundOrganisation);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
