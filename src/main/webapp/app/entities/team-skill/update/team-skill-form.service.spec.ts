import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../team-skill.test-samples';

import { TeamSkillFormService } from './team-skill-form.service';

describe('TeamSkill Form Service', () => {
  let service: TeamSkillFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeamSkillFormService);
  });

  describe('Service methods', () => {
    describe('createTeamSkillFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTeamSkillFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            completedAt: expect.any(Object),
            verifiedAt: expect.any(Object),
            irrelevant: expect.any(Object),
            skillStatus: expect.any(Object),
            note: expect.any(Object),
            vote: expect.any(Object),
            voters: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            skill: expect.any(Object),
            team: expect.any(Object),
          })
        );
      });

      it('passing ITeamSkill should create a new form with FormGroup', () => {
        const formGroup = service.createTeamSkillFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            completedAt: expect.any(Object),
            verifiedAt: expect.any(Object),
            irrelevant: expect.any(Object),
            skillStatus: expect.any(Object),
            note: expect.any(Object),
            vote: expect.any(Object),
            voters: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            skill: expect.any(Object),
            team: expect.any(Object),
          })
        );
      });
    });

    describe('getTeamSkill', () => {
      it('should return NewTeamSkill for default TeamSkill initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTeamSkillFormGroup(sampleWithNewData);

        const teamSkill = service.getTeamSkill(formGroup) as any;

        expect(teamSkill).toMatchObject(sampleWithNewData);
      });

      it('should return NewTeamSkill for empty TeamSkill initial value', () => {
        const formGroup = service.createTeamSkillFormGroup();

        const teamSkill = service.getTeamSkill(formGroup) as any;

        expect(teamSkill).toMatchObject({});
      });

      it('should return ITeamSkill', () => {
        const formGroup = service.createTeamSkillFormGroup(sampleWithRequiredData);

        const teamSkill = service.getTeamSkill(formGroup) as any;

        expect(teamSkill).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITeamSkill should not enable id FormControl', () => {
        const formGroup = service.createTeamSkillFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTeamSkill should disable id FormControl', () => {
        const formGroup = service.createTeamSkillFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
