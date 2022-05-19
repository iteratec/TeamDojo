import { TeamGroupFilterPipe } from './team-group-filter.pipe';
import { TeamGroup } from '../../../entities/team-group/team-group.model';
import { Team } from '../../../entities/team/team.model';
import { TeamScore } from '../../entities/team-score/team-score.model';

describe('TeamGroupFilter', () => {
  const teamGroupFilterPipe = new TeamGroupFilterPipe();

  const root = new TeamGroup(1, 'root', '', undefined, undefined, undefined, null);
  const shapeGroup = new TeamGroup(2, 'Shapes', '', undefined, undefined, undefined, root);
  const colorGroup = new TeamGroup(3, 'Colors', '', undefined, undefined, undefined, root);
  const emptyGroup = new TeamGroup(8, 'Empty', '', undefined, undefined, undefined, root);
  const circle = new TeamGroup(4, 'Circle', '', undefined, undefined, undefined, shapeGroup);
  const triangle = new TeamGroup(5, 'Triangle', '', undefined, undefined, undefined, shapeGroup);
  const red = new TeamGroup(6, 'Red', '', undefined, undefined, undefined, colorGroup);
  const green = new TeamGroup(7, 'Green', '', undefined, undefined, undefined, colorGroup);

  const teamRed = new Team(1, 'Team 1', 'Team 1', '', '', undefined, true, undefined, undefined, undefined, undefined, undefined, red);
  const teamGreen = new Team(2, 'Team 2', 'Team 2', '', '', undefined, true, undefined, undefined, undefined, undefined, undefined, green);
  const teamCircle = new Team(
    3,
    'Team 3',
    'Team 3',
    '',
    '',
    undefined,
    true,
    undefined,
    undefined,
    undefined,
    undefined,
    undefined,
    circle
  );
  const teamTriangle = new Team(
    4,
    'Team 4',
    'Team 4',
    '',
    '',
    undefined,
    true,
    undefined,
    undefined,
    undefined,
    undefined,
    undefined,
    triangle
  );

  const teamNoGroup = new Team(
    5,
    'Team 5',
    'Team 5',
    '',
    '',
    undefined,
    true,
    undefined,
    undefined,
    undefined,
    undefined,
    undefined,
    undefined
  );

  const teamRedScore = new TeamScore(teamRed);
  const teamGreenScore = new TeamScore(teamGreen);
  const teamCircleScore = new TeamScore(teamCircle);
  const teamTriangleScore = new TeamScore(teamTriangle);
  const noGroupTeamScore = new TeamScore(teamNoGroup);

  const allTeams = [teamRedScore, teamGreenScore, teamCircleScore, teamTriangleScore];

  const shapeTeams = [teamCircleScore, teamTriangleScore];

  const colorTeams = [teamRedScore, teamGreenScore];

  const redTeams = [teamRedScore];

  const noGroupTeams = [noGroupTeamScore];

  it("should return all teams if selectedTeamGroup is '' ", () => {
    expect(teamGroupFilterPipe.transform([...allTeams], '')).toEqual(allTeams);
  });

  it('should return all teams if root team is selected ', () => {
    expect(teamGroupFilterPipe.transform([...allTeams], 'root')).toEqual(allTeams);
  });

  it("should return all shapeTeams if 'Shapes' group is selected", () => {
    expect(teamGroupFilterPipe.transform([...allTeams], 'Shapes')).toEqual(shapeTeams);
  });

  it("should return all colorTeams if 'Colors' group is selected", () => {
    expect(teamGroupFilterPipe.transform([...allTeams], 'Colors')).toEqual(colorTeams);
  });

  it("should return no teams if 'Empty' group is selected", () => {
    expect(teamGroupFilterPipe.transform([...allTeams], 'Empty')).toEqual([]);
  });

  it("should return only red team if 'Red' group is selected", () => {
    expect(teamGroupFilterPipe.transform([...allTeams], 'Red')).toEqual(redTeams);
  });

  it('should return an empty array of teamScores is empty', () => {
    expect(teamGroupFilterPipe.transform([], 'Red')).toEqual([]);
  });

  it("should return an empty array of teamScores is empty and selectedTeamGroup == '' ", () => {
    expect(teamGroupFilterPipe.transform([], '')).toEqual([]);
  });

  it("should return teamNoGroups if the selectedTeamGroup is == '' ", () => {
    expect(teamGroupFilterPipe.transform([...noGroupTeams], '')).toEqual(noGroupTeams);
  });

  it("should return empty array if selectedTeamGroup != '' and team doesn't belong to any group", () => {
    expect(teamGroupFilterPipe.transform([...noGroupTeams], 'Shapes')).toEqual([]);
  });

  it("should return empty array if selectedTeamGroup != '' and team doesn't belong to any group", () => {
    expect(teamGroupFilterPipe.transform([...noGroupTeams.concat(shapeTeams)], 'Shapes')).toEqual(shapeTeams);
  });
});
