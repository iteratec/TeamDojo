import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TeamSkillDetailComponent } from './team-skill-detail.component';

describe('TeamSkill Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeamSkillDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TeamSkillDetailComponent,
              resolve: { teamSkill: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TeamSkillDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load teamSkill on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TeamSkillDetailComponent);

      // THEN
      expect(instance.teamSkill).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
