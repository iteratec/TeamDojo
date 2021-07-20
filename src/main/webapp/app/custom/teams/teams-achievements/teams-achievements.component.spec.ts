import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamsAchievementsComponent } from './teams-achievements.component';

describe('TeamsAchievementsComponent', () => {
  let component: TeamsAchievementsComponent;
  let fixture: ComponentFixture<TeamsAchievementsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TeamsAchievementsComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamsAchievementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
