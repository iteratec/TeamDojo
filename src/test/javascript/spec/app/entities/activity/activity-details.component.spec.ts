/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ActivityDetailComponent from '@/entities/activity/activity-details.vue';
import ActivityClass from '@/entities/activity/activity-details.component';
import ActivityService from '@/entities/activity/activity.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Activity Management Detail Component', () => {
    let wrapper: Wrapper<ActivityClass>;
    let comp: ActivityClass;
    let activityServiceStub: SinonStubbedInstance<ActivityService>;

    beforeEach(() => {
      activityServiceStub = sinon.createStubInstance<ActivityService>(ActivityService);

      wrapper = shallowMount<ActivityClass>(ActivityDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { activityService: () => activityServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundActivity = { id: 123 };
        activityServiceStub.find.resolves(foundActivity);

        // WHEN
        comp.retrieveActivity(123);
        await comp.$nextTick();

        // THEN
        expect(comp.activity).toBe(foundActivity);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundActivity = { id: 123 };
        activityServiceStub.find.resolves(foundActivity);

        // WHEN
        comp.beforeRouteEnter({ params: { activityId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.activity).toBe(foundActivity);
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
