import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { BadgeDetailComponent } from './badge-detail.component';

describe('Badge Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BadgeDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BadgeDetailComponent,
              resolve: { badge: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BadgeDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load badge on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BadgeDetailComponent);

      // THEN
      expect(instance.badge).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
