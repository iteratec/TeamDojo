import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TeamGroupDetailComponent } from './team-group-detail.component';

describe('TeamGroup Management Detail Component', () => {
  let comp: TeamGroupDetailComponent;
  let fixture: ComponentFixture<TeamGroupDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeamGroupDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ teamGroup: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TeamGroupDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TeamGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load teamGroup on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.teamGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
