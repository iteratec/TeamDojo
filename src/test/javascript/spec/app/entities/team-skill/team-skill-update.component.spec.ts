/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import TeamSkillUpdateComponent from '@/entities/team-skill/team-skill-update.vue';
import TeamSkillClass from '@/entities/team-skill/team-skill-update.component';
import TeamSkillService from '@/entities/team-skill/team-skill.service';

import SkillService from '@/entities/skill/skill.service';

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
  describe('TeamSkill Management Update Component', () => {
    let wrapper: Wrapper<TeamSkillClass>;
    let comp: TeamSkillClass;
    let teamSkillServiceStub: SinonStubbedInstance<TeamSkillService>;

    beforeEach(() => {
      teamSkillServiceStub = sinon.createStubInstance<TeamSkillService>(TeamSkillService);

      wrapper = shallowMount<TeamSkillClass>(TeamSkillUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          teamSkillService: () => teamSkillServiceStub,

          skillService: () => new SkillService(),

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
        comp.teamSkill = entity;
        teamSkillServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamSkillServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.teamSkill = entity;
        teamSkillServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamSkillServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeamSkill = { id: 123 };
        teamSkillServiceStub.find.resolves(foundTeamSkill);
        teamSkillServiceStub.retrieve.resolves([foundTeamSkill]);

        // WHEN
        comp.beforeRouteEnter({ params: { teamSkillId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.teamSkill).toBe(foundTeamSkill);
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
