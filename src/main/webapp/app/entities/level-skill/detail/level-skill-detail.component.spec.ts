import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LevelSkillDetailComponent } from './level-skill-detail.component';

describe('Component Tests', () => {
  describe('LevelSkill Management Detail Component', () => {
    let comp: LevelSkillDetailComponent;
    let fixture: ComponentFixture<LevelSkillDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [LevelSkillDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ levelSkill: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(LevelSkillDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LevelSkillDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load levelSkill on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.levelSkill).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
