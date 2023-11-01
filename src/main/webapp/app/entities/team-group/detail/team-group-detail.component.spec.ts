import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TeamGroupDetailComponent } from './team-group-detail.component';

describe('TeamGroup Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeamGroupDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TeamGroupDetailComponent,
              resolve: { teamGroup: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TeamGroupDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load teamGroup on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TeamGroupDetailComponent);

      // THEN
      expect(instance.teamGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
