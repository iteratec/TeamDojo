import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { LocalStorageMock } from 'app/custom/overview/teams/overview-teams.component.spec';
import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { LocalStorageService } from 'ngx-webstorage';

import { SkillCardComponent } from './skill-card.component';

describe('SkillCardComponent', () => {
  let component: SkillCardComponent;
  let teamsSelectionService: TeamsSelectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [SkillCardComponent],
      providers: [TeamsSelectionService, { provide: LocalStorageService, useClass: LocalStorageMock }],
    }).compileComponents();
    teamsSelectionService = TestBed.inject(TeamsSelectionService);
    component = new SkillCardComponent(teamsSelectionService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
