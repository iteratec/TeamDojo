/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BadgeSkillDetailComponent from '@/entities/badge-skill/badge-skill-details.vue';
import BadgeSkillClass from '@/entities/badge-skill/badge-skill-details.component';
import BadgeSkillService from '@/entities/badge-skill/badge-skill.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('BadgeSkill Management Detail Component', () => {
    let wrapper: Wrapper<BadgeSkillClass>;
    let comp: BadgeSkillClass;
    let badgeSkillServiceStub: SinonStubbedInstance<BadgeSkillService>;

    beforeEach(() => {
      badgeSkillServiceStub = sinon.createStubInstance<BadgeSkillService>(BadgeSkillService);

      wrapper = shallowMount<BadgeSkillClass>(BadgeSkillDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { badgeSkillService: () => badgeSkillServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBadgeSkill = { id: 123 };
        badgeSkillServiceStub.find.resolves(foundBadgeSkill);

        // WHEN
        comp.retrieveBadgeSkill(123);
        await comp.$nextTick();

        // THEN
        expect(comp.badgeSkill).toBe(foundBadgeSkill);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBadgeSkill = { id: 123 };
        badgeSkillServiceStub.find.resolves(foundBadgeSkill);

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
