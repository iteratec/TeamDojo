/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TeamDetailComponent from '@/entities/team/team-details.vue';
import TeamClass from '@/entities/team/team-details.component';
import TeamService from '@/entities/team/team.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Team Management Detail Component', () => {
    let wrapper: Wrapper<TeamClass>;
    let comp: TeamClass;
    let teamServiceStub: SinonStubbedInstance<TeamService>;

    beforeEach(() => {
      teamServiceStub = sinon.createStubInstance<TeamService>(TeamService);

      wrapper = shallowMount<TeamClass>(TeamDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { teamService: () => teamServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTeam = { id: 123 };
        teamServiceStub.find.resolves(foundTeam);

        // WHEN
        comp.retrieveTeam(123);
        await comp.$nextTick();

        // THEN
        expect(comp.team).toBe(foundTeam);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeam = { id: 123 };
        teamServiceStub.find.resolves(foundTeam);

        // WHEN
        comp.beforeRouteEnter({ params: { teamId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.team).toBe(foundTeam);
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
