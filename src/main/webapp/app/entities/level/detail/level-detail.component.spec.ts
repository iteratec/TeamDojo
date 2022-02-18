import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LevelDetailComponent } from './level-detail.component';

describe('Level Management Detail Component', () => {
  let comp: LevelDetailComponent;
  let fixture: ComponentFixture<LevelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LevelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ level: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LevelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LevelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load level on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.level).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
