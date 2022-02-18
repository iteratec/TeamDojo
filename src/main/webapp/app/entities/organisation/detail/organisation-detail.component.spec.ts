import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganisationDetailComponent } from './organisation-detail.component';

describe('Organisation Management Detail Component', () => {
  let comp: OrganisationDetailComponent;
  let fixture: ComponentFixture<OrganisationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganisationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organisation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrganisationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganisationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organisation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organisation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
