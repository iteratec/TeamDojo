/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import BadgeUpdateComponent from '@/entities/badge/badge-update.vue';
import BadgeClass from '@/entities/badge/badge-update.component';
import BadgeService from '@/entities/badge/badge.service';

import BadgeSkillService from '@/entities/badge-skill/badge-skill.service';

import ImageService from '@/entities/image/image.service';

import DimensionService from '@/entities/dimension/dimension.service';

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
  describe('Badge Management Update Component', () => {
    let wrapper: Wrapper<BadgeClass>;
    let comp: BadgeClass;
    let badgeServiceStub: SinonStubbedInstance<BadgeService>;

    beforeEach(() => {
      badgeServiceStub = sinon.createStubInstance<BadgeService>(BadgeService);

      wrapper = shallowMount<BadgeClass>(BadgeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          badgeService: () => badgeServiceStub,

          badgeSkillService: () => new BadgeSkillService(),

          imageService: () => new ImageService(),

          dimensionService: () => new DimensionService(),
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
        comp.badge = entity;
        badgeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(badgeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.badge = entity;
        badgeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(badgeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBadge = { id: 123 };
        badgeServiceStub.find.resolves(foundBadge);
        badgeServiceStub.retrieve.resolves([foundBadge]);

        // WHEN
        comp.beforeRouteEnter({ params: { badgeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.badge).toBe(foundBadge);
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
