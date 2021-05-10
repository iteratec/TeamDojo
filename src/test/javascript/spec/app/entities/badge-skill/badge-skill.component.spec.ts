/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import BadgeSkillComponent from '@/entities/badge-skill/badge-skill.vue';
import BadgeSkillClass from '@/entities/badge-skill/badge-skill.component';
import BadgeSkillService from '@/entities/badge-skill/badge-skill.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
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
  describe('BadgeSkill Management Component', () => {
    let wrapper: Wrapper<BadgeSkillClass>;
    let comp: BadgeSkillClass;
    let badgeSkillServiceStub: SinonStubbedInstance<BadgeSkillService>;

    beforeEach(() => {
      badgeSkillServiceStub = sinon.createStubInstance<BadgeSkillService>(BadgeSkillService);
      badgeSkillServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BadgeSkillClass>(BadgeSkillComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          badgeSkillService: () => badgeSkillServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      badgeSkillServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBadgeSkills();
      await comp.$nextTick();

      // THEN
      expect(badgeSkillServiceStub.retrieve.called).toBeTruthy();
      expect(comp.badgeSkills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      badgeSkillServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(badgeSkillServiceStub.retrieve.called).toBeTruthy();
      expect(comp.badgeSkills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      badgeSkillServiceStub.retrieve.reset();
      badgeSkillServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(badgeSkillServiceStub.retrieve.callCount).toEqual(2);
      expect(comp.page).toEqual(1);
      expect(comp.badgeSkills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      badgeSkillServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeBadgeSkill();
      await comp.$nextTick();

      // THEN
      expect(badgeSkillServiceStub.delete.called).toBeTruthy();
      expect(badgeSkillServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
