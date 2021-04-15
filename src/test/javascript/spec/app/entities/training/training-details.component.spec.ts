/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TrainingDetailComponent from '@/entities/training/training-details.vue';
import TrainingClass from '@/entities/training/training-details.component';
import TrainingService from '@/entities/training/training.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Training Management Detail Component', () => {
    let wrapper: Wrapper<TrainingClass>;
    let comp: TrainingClass;
    let trainingServiceStub: SinonStubbedInstance<TrainingService>;

    beforeEach(() => {
      trainingServiceStub = sinon.createStubInstance<TrainingService>(TrainingService);

      wrapper = shallowMount<TrainingClass>(TrainingDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { trainingService: () => trainingServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTraining = { id: 123 };
        trainingServiceStub.find.resolves(foundTraining);

        // WHEN
        comp.retrieveTraining(123);
        await comp.$nextTick();

        // THEN
        expect(comp.training).toBe(foundTraining);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTraining = { id: 123 };
        trainingServiceStub.find.resolves(foundTraining);

        // WHEN
        comp.beforeRouteEnter({ params: { trainingId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.training).toBe(foundTraining);
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
