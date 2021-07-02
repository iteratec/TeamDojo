import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DimensionDetailComponent } from './dimension-detail.component';

describe('Component Tests', () => {
  describe('Dimension Management Detail Component', () => {
    let comp: DimensionDetailComponent;
    let fixture: ComponentFixture<DimensionDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DimensionDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dimension: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DimensionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DimensionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dimension on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dimension).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
