/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import BadgeSkillUpdateComponent from '@/entities/badge-skill/badge-skill-update.vue';
import BadgeSkillClass from '@/entities/badge-skill/badge-skill-update.component';
import BadgeSkillService from '@/entities/badge-skill/badge-skill.service';

import BadgeService from '@/entities/badge/badge.service';

import SkillService from '@/entities/skill/skill.service';

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
  describe('BadgeSkill Management Update Component', () => {
    let wrapper: Wrapper<BadgeSkillClass>;
    let comp: BadgeSkillClass;
    let badgeSkillServiceStub: SinonStubbedInstance<BadgeSkillService>;

    beforeEach(() => {
      badgeSkillServiceStub = sinon.createStubInstance<BadgeSkillService>(BadgeSkillService);

      wrapper = shallowMount<BadgeSkillClass>(BadgeSkillUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          badgeSkillService: () => badgeSkillServiceStub,

          badgeService: () => new BadgeService(),

          skillService: () => new SkillService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.badgeSkill = entity;
        badgeSkillServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(badgeSkillServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.badgeSkill = entity;
        badgeSkillServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(badgeSkillServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBadgeSkill = { id: 123 };
        badgeSkillServiceStub.find.resolves(foundBadgeSkill);
        badgeSkillServiceStub.retrieve.resolves([foundBadgeSkill]);

        // WHEN
        comp.beforeRouteEnter({ params: { badgeSkillId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.badgeSkill).toBe(foundBadgeSkill);
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
