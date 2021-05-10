/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import LevelSkillUpdateComponent from '@/entities/level-skill/level-skill-update.vue';
import LevelSkillClass from '@/entities/level-skill/level-skill-update.component';
import LevelSkillService from '@/entities/level-skill/level-skill.service';

import SkillService from '@/entities/skill/skill.service';

import LevelService from '@/entities/level/level.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('LevelSkill Management Update Component', () => {
    let wrapper: Wrapper<LevelSkillClass>;
    let comp: LevelSkillClass;
    let levelSkillServiceStub: SinonStubbedInstance<LevelSkillService>;

    beforeEach(() => {
      levelSkillServiceStub = sinon.createStubInstance<LevelSkillService>(LevelSkillService);

      wrapper = shallowMount<LevelSkillClass>(LevelSkillUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          levelSkillService: () => levelSkillServiceStub,

          skillService: () => new SkillService(),

          levelService: () => new LevelService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.levelSkill = entity;
        levelSkillServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(levelSkillServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.levelSkill = entity;
        levelSkillServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(levelSkillServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLevelSkill = { id: 123 };
        levelSkillServiceStub.find.resolves(foundLevelSkill);
        levelSkillServiceStub.retrieve.resolves([foundLevelSkill]);

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
