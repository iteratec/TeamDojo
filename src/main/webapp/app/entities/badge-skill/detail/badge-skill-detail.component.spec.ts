import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BadgeSkillDetailComponent } from './badge-skill-detail.component';

describe('BadgeSkill Management Detail Component', () => {
  let comp: BadgeSkillDetailComponent;
  let fixture: ComponentFixture<BadgeSkillDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BadgeSkillDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ badgeSkill: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BadgeSkillDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BadgeSkillDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load badgeSkill on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.badgeSkill).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
