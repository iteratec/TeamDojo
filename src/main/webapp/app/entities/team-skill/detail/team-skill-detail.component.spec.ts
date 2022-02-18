import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TeamSkillDetailComponent } from './team-skill-detail.component';

describe('TeamSkill Management Detail Component', () => {
  let comp: TeamSkillDetailComponent;
  let fixture: ComponentFixture<TeamSkillDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeamSkillDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ teamSkill: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TeamSkillDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TeamSkillDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load teamSkill on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.teamSkill).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
