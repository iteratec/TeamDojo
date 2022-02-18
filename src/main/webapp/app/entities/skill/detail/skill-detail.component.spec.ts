import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SkillDetailComponent } from './skill-detail.component';

describe('Skill Management Detail Component', () => {
  let comp: SkillDetailComponent;
  let fixture: ComponentFixture<SkillDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SkillDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ skill: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SkillDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SkillDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load skill on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.skill).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
