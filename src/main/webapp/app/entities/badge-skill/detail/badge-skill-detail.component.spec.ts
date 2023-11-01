import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { BadgeSkillDetailComponent } from './badge-skill-detail.component';

describe('BadgeSkill Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BadgeSkillDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BadgeSkillDetailComponent,
              resolve: { badgeSkill: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BadgeSkillDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load badgeSkill on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BadgeSkillDetailComponent);

      // THEN
      expect(instance.badgeSkill).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
