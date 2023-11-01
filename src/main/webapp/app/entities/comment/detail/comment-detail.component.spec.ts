import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CommentDetailComponent } from './comment-detail.component';

describe('Comment Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommentDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CommentDetailComponent,
              resolve: { comment: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CommentDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load comment on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CommentDetailComponent);

      // THEN
      expect(instance.comment).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
