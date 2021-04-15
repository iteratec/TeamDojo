/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ReportDetailComponent from '@/entities/report/report-details.vue';
import ReportClass from '@/entities/report/report-details.component';
import ReportService from '@/entities/report/report.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Report Management Detail Component', () => {
    let wrapper: Wrapper<ReportClass>;
    let comp: ReportClass;
    let reportServiceStub: SinonStubbedInstance<ReportService>;

    beforeEach(() => {
      reportServiceStub = sinon.createStubInstance<ReportService>(ReportService);

      wrapper = shallowMount<ReportClass>(ReportDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { reportService: () => reportServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundReport = { id: 123 };
        reportServiceStub.find.resolves(foundReport);

        // WHEN
        comp.retrieveReport(123);
        await comp.$nextTick();

        // THEN
        expect(comp.report).toBe(foundReport);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundReport = { id: 123 };
        reportServiceStub.find.resolves(foundReport);

        // WHEN
        comp.beforeRouteEnter({ params: { reportId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.report).toBe(foundReport);
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
