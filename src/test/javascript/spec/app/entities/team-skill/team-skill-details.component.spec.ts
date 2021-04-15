/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TeamSkillDetailComponent from '@/entities/team-skill/team-skill-details.vue';
import TeamSkillClass from '@/entities/team-skill/team-skill-details.component';
import TeamSkillService from '@/entities/team-skill/team-skill.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TeamSkill Management Detail Component', () => {
    let wrapper: Wrapper<TeamSkillClass>;
    let comp: TeamSkillClass;
    let teamSkillServiceStub: SinonStubbedInstance<TeamSkillService>;

    beforeEach(() => {
      teamSkillServiceStub = sinon.createStubInstance<TeamSkillService>(TeamSkillService);

      wrapper = shallowMount<TeamSkillClass>(TeamSkillDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { teamSkillService: () => teamSkillServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTeamSkill = { id: 123 };
        teamSkillServiceStub.find.resolves(foundTeamSkill);

        // WHEN
        comp.retrieveTeamSkill(123);
        await comp.$nextTick();

        // THEN
        expect(comp.teamSkill).toBe(foundTeamSkill);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeamSkill = { id: 123 };
        teamSkillServiceStub.find.resolves(foundTeamSkill);

        // WHEN
        comp.beforeRouteEnter({ params: { teamSkillId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.teamSkill).toBe(foundTeamSkill);
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
