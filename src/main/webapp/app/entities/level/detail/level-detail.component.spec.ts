import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LevelDetailComponent } from './level-detail.component';

describe('Level Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LevelDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: LevelDetailComponent,
              resolve: { level: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LevelDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load level on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LevelDetailComponent);

      // THEN
      expect(instance.level).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
