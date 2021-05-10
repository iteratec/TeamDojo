/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import DimensionUpdateComponent from '@/entities/dimension/dimension-update.vue';
import DimensionClass from '@/entities/dimension/dimension-update.component';
import DimensionService from '@/entities/dimension/dimension.service';

import LevelService from '@/entities/level/level.service';

import BadgeService from '@/entities/badge/badge.service';

import TeamService from '@/entities/team/team.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Dimension Management Update Component', () => {
    let wrapper: Wrapper<DimensionClass>;
    let comp: DimensionClass;
    let dimensionServiceStub: SinonStubbedInstance<DimensionService>;

    beforeEach(() => {
      dimensionServiceStub = sinon.createStubInstance<DimensionService>(DimensionService);

      wrapper = shallowMount<DimensionClass>(DimensionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          dimensionService: () => dimensionServiceStub,

          levelService: () => new LevelService(),

          badgeService: () => new BadgeService(),

          teamService: () => new TeamService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.dimension = entity;
        dimensionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dimensionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.dimension = entity;
        dimensionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dimensionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDimension = { id: 123 };
        dimensionServiceStub.find.resolves(foundDimension);
        dimensionServiceStub.retrieve.resolves([foundDimension]);

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
