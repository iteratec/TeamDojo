/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LevelSkillDetailComponent from '@/entities/level-skill/level-skill-details.vue';
import LevelSkillClass from '@/entities/level-skill/level-skill-details.component';
import LevelSkillService from '@/entities/level-skill/level-skill.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('LevelSkill Management Detail Component', () => {
    let wrapper: Wrapper<LevelSkillClass>;
    let comp: LevelSkillClass;
    let levelSkillServiceStub: SinonStubbedInstance<LevelSkillService>;

    beforeEach(() => {
      levelSkillServiceStub = sinon.createStubInstance<LevelSkillService>(LevelSkillService);

      wrapper = shallowMount<LevelSkillClass>(LevelSkillDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { levelSkillService: () => levelSkillServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLevelSkill = { id: 123 };
        levelSkillServiceStub.find.resolves(foundLevelSkill);

        // WHEN
        comp.retrieveLevelSkill(123);
        await comp.$nextTick();

        // THEN
        expect(comp.levelSkill).toBe(foundLevelSkill);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLevelSkill = { id: 123 };
        levelSkillServiceStub.find.resolves(foundLevelSkill);

        // WHEN
        comp.beforeRouteEnter({ params: { levelSkillId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.levelSkill).toBe(foundLevelSkill);
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
