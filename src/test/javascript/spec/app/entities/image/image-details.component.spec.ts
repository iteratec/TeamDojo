/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ImageDetailComponent from '@/entities/image/image-details.vue';
import ImageClass from '@/entities/image/image-details.component';
import ImageService from '@/entities/image/image.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Image Management Detail Component', () => {
    let wrapper: Wrapper<ImageClass>;
    let comp: ImageClass;
    let imageServiceStub: SinonStubbedInstance<ImageService>;

    beforeEach(() => {
      imageServiceStub = sinon.createStubInstance<ImageService>(ImageService);

      wrapper = shallowMount<ImageClass>(ImageDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { imageService: () => imageServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundImage = { id: 123 };
        imageServiceStub.find.resolves(foundImage);

        // WHEN
        comp.retrieveImage(123);
        await comp.$nextTick();

        // THEN
        expect(comp.image).toBe(foundImage);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundImage = { id: 123 };
        imageServiceStub.find.resolves(foundImage);

        // WHEN
        comp.beforeRouteEnter({ params: { imageId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.image).toBe(foundImage);
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
