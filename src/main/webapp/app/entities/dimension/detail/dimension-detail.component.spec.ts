import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DimensionDetailComponent } from './dimension-detail.component';

describe('Dimension Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DimensionDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DimensionDetailComponent,
              resolve: { dimension: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DimensionDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load dimension on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DimensionDetailComponent);

      // THEN
      expect(instance.dimension).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
