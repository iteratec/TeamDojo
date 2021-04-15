/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DimensionDetailComponent from '@/entities/dimension/dimension-details.vue';
import DimensionClass from '@/entities/dimension/dimension-details.component';
import DimensionService from '@/entities/dimension/dimension.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Dimension Management Detail Component', () => {
    let wrapper: Wrapper<DimensionClass>;
    let comp: DimensionClass;
    let dimensionServiceStub: SinonStubbedInstance<DimensionService>;

    beforeEach(() => {
      dimensionServiceStub = sinon.createStubInstance<DimensionService>(DimensionService);

      wrapper = shallowMount<DimensionClass>(DimensionDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { dimensionService: () => dimensionServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDimension = { id: 123 };
        dimensionServiceStub.find.resolves(foundDimension);

        // WHEN
        comp.retrieveDimension(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dimension).toBe(foundDimension);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDimension = { id: 123 };
        dimensionServiceStub.find.resolves(foundDimension);

        // WHEN
        comp.beforeRouteEnter({ params: { dimensionId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.dimension).toBe(foundDimension);
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
