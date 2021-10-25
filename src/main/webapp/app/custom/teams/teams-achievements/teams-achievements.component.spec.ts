import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { Observable, of } from 'rxjs';

import { Team } from 'app/entities/team/team.model';
import { Badge } from 'app/entities/badge/badge.model';
import { Dimension } from 'app/entities/dimension/dimension.model';
import { Level } from 'app/entities/level/level.model';
import { TeamsAchievementsComponent } from 'app/custom/teams/teams-achievements/teams-achievements.component';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

class MockAccountService {
  authenticate(identity: Account | null): void {
    // do nothing
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    return false;
  }

  identity(force?: boolean): Observable<Account | null> {
    return new Observable();
  }

  isAuthenticated(): boolean {
    return false;
  }

  getAuthenticationState(): Observable<Account | null> {
    return new Observable();
  }

  getImageUrl(): string {
    return '';
  }
}

class MockActivatedRoute {
  public queryParamMap = of(
    convertToParamMap({
      dimension: 1,
      level: 2,
      badge: 3,
    })
  );
}

describe('Component Tests', () => {
  describe('Team Achievements Component', () => {
    let comp: TeamsAchievementsComponent;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RouterTestingModule],
        providers: [
          TeamsAchievementsComponent,
          { provide: AccountService, useClass: MockAccountService },
          { provide: ActivatedRoute, useClass: MockActivatedRoute },
        ],
      });

      comp = TestBed.inject(TeamsAchievementsComponent);
    });

    it('Should call load all on init', () => {
      comp.team = new Team(122);
      comp.badges = [new Badge(123)];

      comp.ngOnInit();

      expect(comp.badges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should load levels depending on team participation', () => {
      const entity = new Team(121);
      entity.participations = [new Dimension(122)];
      comp.team = entity;
      comp.badges = [];
      const levelOne = new Level(123);
      levelOne.dimension = new Dimension(122);
      const levelTwo = new Level(124);
      levelTwo.dimension = new Dimension(122);
      expect(comp.team.participations).toBeDefined();
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore This is checked above by an assertion.
      const participation = comp.team.participations[0];
      participation.levels = [levelOne, levelTwo];

      comp.ngOnInit();

      expect(participation.id).toEqual(122);
      expect(participation.levels.length).toEqual(2);
      expect(participation.levels[0].id).toEqual(123);
      expect(participation.levels[1].id).toEqual(124);
    });

    it('Should not fail if no level exists for dimension', () => {
      const entity = new Team(121);
      entity.participations = [new Dimension(122)];
      comp.team = entity;
      expect(comp.team.participations).toBeDefined();
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore This is checked above by an assertion.
      const participation = comp.team.participations[0];
      participation.levels = [];
      comp.badges = [];

      comp.ngOnInit();

      expect(participation.id).toEqual(122);
      expect(participation.levels.length).toEqual(0);
    });

    it('Should not fail if only a single level is retrieved', () => {
      const entity = new Team(121);
      entity.participations = [new Dimension(122)];
      comp.team = entity;
      const level = new Level(123);
      level.dimension = new Dimension(122);
      expect(comp.team.participations).toBeDefined();
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore This is checked above by an assertion.
      const participation = comp.team.participations[0];
      participation.levels = [level];
      comp.badges = [];

      comp.ngOnInit();

      expect(participation.id).toEqual(122);
      expect(participation.levels.length).toEqual(1);
      expect(participation.levels[0].id).toEqual(123);
    });

    it('Should load multiple dimensions with levels', () => {
      const entity = new Team(121);
      entity.participations = [new Dimension(122), new Dimension(123)];
      comp.team = entity;
      const levelOne = new Level(124);
      levelOne.dimension = new Dimension(122);
      const levelTwo = new Level(125);
      levelTwo.dimension = new Dimension(123);
      expect(comp.team.participations).toBeDefined();
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore This is checked above by an assertion.
      const participationOne = comp.team.participations[0];
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore This is checked above by an assertion.
      const participationTwo = comp.team.participations[1];
      participationOne.levels = [levelOne];
      participationTwo.levels = [levelTwo];
      comp.badges = [];

      comp.ngOnInit();

      expect(participationOne.id).toEqual(122);
      expect(participationOne.levels.length).toEqual(1);
      expect(participationOne.levels[0].id).toEqual(124);

      expect(participationTwo.id).toEqual(123);
      expect(participationTwo.levels.length).toEqual(1);
      expect(participationTwo.levels[0].id).toEqual(125);
    });
  });
});
