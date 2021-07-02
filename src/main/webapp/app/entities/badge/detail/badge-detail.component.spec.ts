import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BadgeDetailComponent } from './badge-detail.component';

describe('Component Tests', () => {
  describe('Badge Management Detail Component', () => {
    let comp: BadgeDetailComponent;
    let fixture: ComponentFixture<BadgeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BadgeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ badge: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(BadgeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BadgeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load badge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.badge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
