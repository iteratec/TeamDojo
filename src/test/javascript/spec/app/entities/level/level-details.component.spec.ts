/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LevelDetailComponent from '@/entities/level/level-details.vue';
import LevelClass from '@/entities/level/level-details.component';
import LevelService from '@/entities/level/level.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Level Management Detail Component', () => {
    let wrapper: Wrapper<LevelClass>;
    let comp: LevelClass;
    let levelServiceStub: SinonStubbedInstance<LevelService>;

    beforeEach(() => {
      levelServiceStub = sinon.createStubInstance<LevelService>(LevelService);

      wrapper = shallowMount<LevelClass>(LevelDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { levelService: () => levelServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLevel = { id: 123 };
        levelServiceStub.find.resolves(foundLevel);

        // WHEN
        comp.retrieveLevel(123);
        await comp.$nextTick();

        // THEN
        expect(comp.level).toBe(foundLevel);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLevel = { id: 123 };
        levelServiceStub.find.resolves(foundLevel);

        // WHEN
        comp.beforeRouteEnter({ params: { levelId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.level).toBe(foundLevel);
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
