import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TrainingDetailComponent } from './training-detail.component';

describe('Training Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrainingDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TrainingDetailComponent,
              resolve: { training: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TrainingDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load training on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TrainingDetailComponent);

      // THEN
      expect(instance.training).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
