/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DimensionComponent from '@/entities/dimension/dimension.vue';
import DimensionClass from '@/entities/dimension/dimension.component';
import DimensionService from '@/entities/dimension/dimension.service';

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
  describe('Dimension Management Component', () => {
    let wrapper: Wrapper<DimensionClass>;
    let comp: DimensionClass;
    let dimensionServiceStub: SinonStubbedInstance<DimensionService>;

    beforeEach(() => {
      dimensionServiceStub = sinon.createStubInstance<DimensionService>(DimensionService);
      dimensionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DimensionClass>(DimensionComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          dimensionService: () => dimensionServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dimensionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDimensions();
      await comp.$nextTick();

      // THEN
      expect(dimensionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dimensions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      dimensionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(dimensionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dimensions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      dimensionServiceStub.retrieve.reset();
      dimensionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(dimensionServiceStub.retrieve.callCount).toEqual(2);
      expect(comp.page).toEqual(1);
      expect(comp.dimensions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      dimensionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDimension();
      await comp.$nextTick();

      // THEN
      expect(dimensionServiceStub.delete.called).toBeTruthy();
      expect(dimensionServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
