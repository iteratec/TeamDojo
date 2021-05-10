/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import TeamUpdateComponent from '@/entities/team/team-update.vue';
import TeamClass from '@/entities/team/team-update.component';
import TeamService from '@/entities/team/team.service';

import TeamSkillService from '@/entities/team-skill/team-skill.service';

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
  describe('Team Management Update Component', () => {
    let wrapper: Wrapper<TeamClass>;
    let comp: TeamClass;
    let teamServiceStub: SinonStubbedInstance<TeamService>;

    beforeEach(() => {
      teamServiceStub = sinon.createStubInstance<TeamService>(TeamService);

      wrapper = shallowMount<TeamClass>(TeamUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          teamService: () => teamServiceStub,

          teamSkillService: () => new TeamSkillService(),

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
        comp.team = entity;
        teamServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.team = entity;
        teamServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeam = { id: 123 };
        teamServiceStub.find.resolves(foundTeam);
        teamServiceStub.retrieve.resolves([foundTeam]);

        // WHEN
        comp.beforeRouteEnter({ params: { teamId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.team).toBe(foundTeam);
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
