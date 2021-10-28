import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Team } from 'app/entities/team/team.model';

import { TeamsSelectionService } from 'app/custom/teams-selection/teams-selection.service';
import { TeamsService } from 'app/custom/teams/teams.service';
import { TeamsSelectionComponent } from 'app/custom/teams-selection/teams-selection.component';

class MockService {}
describe('Team Achievements Component', () => {
  let comp: TeamsSelectionComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      providers: [
        TeamsSelectionComponent,
        { provide: TeamsSelectionService, useClass: MockService },
        { provide: TeamsService, useClass: MockService },
        { provide: NgbActiveModal, useClass: MockService },
      ],
    });

    comp = TestBed.inject(TeamsSelectionComponent);
  });

  it('compareTeamShortTitle should return 1 if leftShorTitle is defined and rightShortTitle is not', () => {
    const left = new Team(1);
    const right = new Team(2);

    left.shortTitle = 'abcdef';

    expect(comp.compareTeamByShortTitle(left, right)).toEqual(1);
  });

  it('compareTeamShortTitle should return -1 if leftShorTitle is undefined and rightShortTitle is defined', () => {
    const left = new Team(1);
    const right = new Team(2);

    right.shortTitle = 'abcdef';

    expect(comp.compareTeamByShortTitle(left, right)).toEqual(-1);
  });

  it('compareTeamShortTitle should return 0 if leftShorTitle is undefined and rightShortTitle is undefined', () => {
    const left = new Team(1);
    const right = new Team(2);

    expect(comp.compareTeamByShortTitle(left, right)).toEqual(0);
  });

  it(
    'compareTeamShortTitle should be equal invoking localCompare on the leftShortTitle with the ' + 'rightShortTitle as an argument',
    () => {
      const left = new Team(1);
      const right = new Team(2);

      left.shortTitle = 'abcdef';
      right.shortTitle = 'test';

      expect(comp.compareTeamByShortTitle(left, right)).toEqual(left.shortTitle.localeCompare(right.shortTitle));
    }
  );
});
