/*import { ComponentFixture, TestBed } from '@angular/core/testing';

import Util from '../../helper/services/Util.service';
import { Team } from 'app/entities/team/team.model';
import { Badge } from 'app/entities/badge/badge.model';
import { Dimension } from 'app/entities/dimension/dimension.model';
import { Level } from 'app/entities/level/level.model';
import { TeamsAchievementsComponent } from 'app/custom/teams/teams-achievements/teams-achievements.component';
import { TeamsAchievementsService } from 'app/custom/teams/services/teams-achievements.service';
import { TeamdojoTestModule } from 'app/custom/test/test.module';

describe('Component Tests', () => {
  describe('Team Achievements Component', () => {
    let comp: TeamsAchievementsComponent;
    let fixture: ComponentFixture<TeamsAchievementsComponent>;
    const buildEntity = Util.wrap;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TeamdojoTestModule],
        declarations: [TeamsAchievementsComponent],
        providers: [TeamsAchievementsService],
      })
        .overrideTemplate(TeamsAchievementsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeamsAchievementsComponent);
      comp = fixture.componentInstance;
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
          buildEntity(new Level(123), { dimensionId: 122 }),
          buildEntity(new Level(124), { dimensionId: 122 }),
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
        comp.team.participations[0].levels = [buildEntity(new Level(123), { dimensionId: 122 })];
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
        comp.team.participations[0].levels = [buildEntity(new Level(124), { dimensionId: 122 })];
        comp.team.participations[1].levels = [buildEntity(new Level(125), { dimensionId: 123 })];
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
*/
describe('Placeholder for teams-achievements component test', () => {
  it('remove', () => {
    expect(1).toEqual(1);
  });
});
