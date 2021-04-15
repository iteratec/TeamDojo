/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LevelSkillComponent from '@/entities/level-skill/level-skill.vue';
import LevelSkillClass from '@/entities/level-skill/level-skill.component';
import LevelSkillService from '@/entities/level-skill/level-skill.service';

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
  describe('LevelSkill Management Component', () => {
    let wrapper: Wrapper<LevelSkillClass>;
    let comp: LevelSkillClass;
    let levelSkillServiceStub: SinonStubbedInstance<LevelSkillService>;

    beforeEach(() => {
      levelSkillServiceStub = sinon.createStubInstance<LevelSkillService>(LevelSkillService);
      levelSkillServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LevelSkillClass>(LevelSkillComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          levelSkillService: () => levelSkillServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      levelSkillServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLevelSkills();
      await comp.$nextTick();

      // THEN
      expect(levelSkillServiceStub.retrieve.called).toBeTruthy();
      expect(comp.levelSkills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      levelSkillServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(levelSkillServiceStub.retrieve.called).toBeTruthy();
      expect(comp.levelSkills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      levelSkillServiceStub.retrieve.reset();
      levelSkillServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(levelSkillServiceStub.retrieve.callCount).toEqual(2);
      expect(comp.page).toEqual(1);
      expect(comp.levelSkills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      levelSkillServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeLevelSkill();
      await comp.$nextTick();

      // THEN
      expect(levelSkillServiceStub.delete.called).toBeTruthy();
      expect(levelSkillServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
