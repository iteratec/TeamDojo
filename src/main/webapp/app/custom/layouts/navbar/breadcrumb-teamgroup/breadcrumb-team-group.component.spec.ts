import { TestBed } from '@angular/core/testing';

import { BreadcrumbTeamGroupComponent } from './breadcrumb-team-group.component';
import { TeamsSelectionService } from '../../../teams-selection/teams-selection.service';
import { ITeamGroup, TeamGroup } from '../../../../entities/team-group/team-group.model';

class MockService {}

describe('BreadcrumbTeamGroupComponent', () => {
  let sut: BreadcrumbTeamGroupComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BreadcrumbTeamGroupComponent, { provide: TeamsSelectionService, useClass: MockService }],
    });

    sut = TestBed.inject(BreadcrumbTeamGroupComponent);
  });

  it('resolveTeamGroupChain group w/o parent', () => {
    const group: ITeamGroup = new TeamGroup();
    group.title = 'group';

    const chain = sut.resolveTeamGroupChain(group);

    expect(chain.length).toBe(1);
    expect(chain[0]).toBe(group);
  });

  it('resolveTeamGroupChain group w/ one parent', () => {
    const parentOne: ITeamGroup = new TeamGroup();
    parentOne.title = 'parent one';
    const group: ITeamGroup = new TeamGroup();
    group.title = 'group';
    // Set up the chain of groups:
    group.parent = parentOne;

    const chain = sut.resolveTeamGroupChain(group);

    expect(chain.length).toBe(2);
    expect(chain[0]).toBe(parentOne);
    expect(chain[1]).toBe(group);
  });

  it('resolveTeamGroupChain group w/ four parents', () => {
    const parentOne: ITeamGroup = new TeamGroup();
    parentOne.title = 'parent one';
    const parentTwo: ITeamGroup = new TeamGroup();
    parentOne.title = 'parent two';
    const parentThree: ITeamGroup = new TeamGroup();
    parentOne.title = 'parent three';
    const parentFour: ITeamGroup = new TeamGroup();
    parentOne.title = 'parent four';
    const group: ITeamGroup = new TeamGroup();
    group.title = 'group';
    // Set up the chain of groups:
    group.parent = parentOne;
    parentOne.parent = parentTwo;
    parentTwo.parent = parentThree;
    parentThree.parent = parentFour;

    const chain = sut.resolveTeamGroupChain(group);

    expect(chain.length).toBe(5);
    expect(chain[0]).toBe(parentFour);
    expect(chain[1]).toBe(parentThree);
    expect(chain[2]).toBe(parentTwo);
    expect(chain[3]).toBe(parentOne);
    expect(chain[4]).toBe(group);
  });
});
