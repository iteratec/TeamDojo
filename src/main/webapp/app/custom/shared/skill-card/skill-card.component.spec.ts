import { HttpClient } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';

import { SkillCardComponent } from './skill-card.component';

describe('SkillCardComponent', () => {
  let component: SkillCardComponent;
  let teamsSelectionService: TeamsSelectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SkillCardComponent],
      providers: [TeamsSelectionService, HttpClient],
    }).compileComponents();
    teamsSelectionService = TestBed.inject(TeamsSelectionService);
    component = new SkillCardComponent(teamsSelectionService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
