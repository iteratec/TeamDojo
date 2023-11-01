import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LevelSkillDetailComponent } from './level-skill-detail.component';

describe('LevelSkill Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LevelSkillDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: LevelSkillDetailComponent,
              resolve: { levelSkill: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LevelSkillDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load levelSkill on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LevelSkillDetailComponent);

      // THEN
      expect(instance.levelSkill).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
