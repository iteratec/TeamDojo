import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SkillDetailComponent } from './skill-detail.component';

describe('Skill Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SkillDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SkillDetailComponent,
              resolve: { skill: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SkillDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load skill on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SkillDetailComponent);

      // THEN
      expect(instance.skill).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
