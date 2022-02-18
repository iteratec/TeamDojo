import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainingDetailComponent } from './training-detail.component';

describe('Training Management Detail Component', () => {
  let comp: TrainingDetailComponent;
  let fixture: ComponentFixture<TrainingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ training: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load training on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.training).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
