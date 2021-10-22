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
      // GIVEN
      const entity = new Team(122);
      comp.team = entity;
      comp.badges = [new Badge(123)];

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.badges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('Should load levels depending on team participations', () => {
      // GIVEN
      const entity = new Team(121);
      entity.participations = [new Dimension(122)];
      comp.team = entity;
      comp.badges = [];
      if (comp.team.participations) {
        comp.team.participations[0].levels = [
          new Level(
            123,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            new Dimension(122)
          ),
          new Level(
            124,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            new Dimension(122)
          ),
        ];

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.team.participations[0].id).toEqual(122);
        expect(comp.team.participations[0].levels.length).toEqual(2);
        expect(comp.team.participations[0].levels[0].id).toEqual(123);
        expect(comp.team.participations[0].levels[1].id).toEqual(124);
      }
    });

    it('Should not fail if no level exists for dimension', () => {
      // GIVEN
      const entity = new Team(121);
      entity.participations = [new Dimension(122)];
      comp.team = entity;
      if (comp.team.participations) {
        comp.team.participations[0].levels = [];
        comp.badges = [];
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.team.participations[0].id).toEqual(122);
        expect(comp.team.participations[0].levels.length).toEqual(0);
      }
    });

    it('Should not fail if only a single level is retrieved', () => {
      // GIVEN
      const entity = new Team(121);
      entity.participations = [new Dimension(122)];
      comp.team = entity;
      if (comp.team.participations) {
        comp.team.participations[0].levels = [
          new Level(
            123,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            new Dimension(122)
          ),
        ];
        comp.badges = [];
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.team.participations[0].id).toEqual(122);
        expect(comp.team.participations[0].levels.length).toEqual(1);
        expect(comp.team.participations[0].levels[0].id).toEqual(123);
      }
    });

    it('Should load multiple dimensions with levels', () => {
      // GIVEN
      const entity = new Team(121);
      entity.participations = [new Dimension(122), new Dimension(123)];
      comp.team = entity;
      if (comp.team.participations) {
        comp.team.participations[0].levels = [
          new Level(
            124,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            new Dimension(122)
          ),
        ];
        comp.team.participations[1].levels = [
          new Level(
            125,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            undefined,
            new Dimension(123)
          ),
        ];
        comp.badges = [];
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.team.participations[0].id).toEqual(122);
        expect(comp.team.participations[0].levels.length).toEqual(1);
        expect(comp.team.participations[0].levels[0].id).toEqual(124);

        expect(comp.team.participations[1].id).toEqual(123);
        expect(comp.team.participations[1].levels.length).toEqual(1);
        expect(comp.team.participations[1].levels[0].id).toEqual(125);
      }
    });
  });
});
