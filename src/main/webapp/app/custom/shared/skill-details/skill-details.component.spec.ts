/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { of } from 'rxjs';
import { convertToParamMap } from '@angular/router';
import { TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { SkillDetailsBaseComponent } from 'app/custom/shared/skill-details/skill-details-base.component';
import { Comment } from 'app/entities/comment/comment.model';

import dayjs from 'dayjs/esm';
import { TeamsSkillsService } from 'app/custom/teams/teams-skills.service';

import { HttpClientTestingModule } from '@angular/common/http/testing';

class ActivatedRouteMock {
  public paramMap = of(
    convertToParamMap({
      badge: '10',
      level: '10',
      dimension: '10',
    })
  );
}

describe('SkillDetailsComponent', () => {
  let comp: SkillDetailsBaseComponent;
  let activatedRoute: ActivatedRoute;
  let teamsSkillService: TeamsSkillsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      // provide the component under test and dependency
      imports: [HttpClientTestingModule],
      providers: [{ provide: ActivatedRoute, useClass: ActivatedRouteMock }, TeamsSkillsService],
    });

    // inject both the component and the dependencies
    activatedRoute = TestBed.inject(ActivatedRoute);
    teamsSkillService = TestBed.inject(TeamsSkillsService);
    comp = new SkillDetailsBaseComponent(activatedRoute, teamsSkillService);
  });

  it('compareCommentByCreationDate should return 0 if left.creationdate and right.creationdate are undefined', () => {
    const left = new Comment();
    const right = new Comment();

    expect(comp.compareCommentByCreationDate(left, right)).toBe(0);
  });

  it('compareCommentByCreationDate should return 1 if left.creationdate is defined and right.creationdate is undefined', () => {
    const left = new Comment(1, '', dayjs());
    const right = new Comment(2, '', undefined);

    expect(comp.compareCommentByCreationDate(left, right)).toBe(1);
  });

  it('compareCommentByCreationDate should return -1 if left.creationdate is undefined and right.creationdate is defined', () => {
    const left = new Comment(2, '', undefined);
    const right = new Comment(1, '', dayjs());

    expect(comp.compareCommentByCreationDate(left, right)).toBe(-1);
  });

  it('compareCommentByCreationDate should return 0 if left.creationdate and right.creationdate are equal', () => {
    const now = dayjs();
    const left = new Comment(2, '', now);
    const right = new Comment(1, '', now);

    expect(comp.compareCommentByCreationDate(left, right)).toBe(0);
  });

  it('compareCommentByCreationDate should return -1 if left.creationdate is earlier then right.creationdate', () => {
    const earlier = dayjs('2021-10-03T16:00:00.000Z');
    const later = dayjs('2021-10-04T16:00:00.000Z');
    const left = new Comment(2, '', earlier);
    const right = new Comment(1, '', later);

    expect(comp.compareCommentByCreationDate(left, right)).toBe(-1);
  });

  it('compareCommentByCreationDate should return 1 if left.creationdate is later then right.creationdate', () => {
    const earlier = dayjs('2021-10-03T16:00:00.000Z');
    const later = dayjs('2021-10-04T16:00:00.000Z');
    const left = new Comment(2, '', later);
    const right = new Comment(1, '', earlier);

    expect(comp.compareCommentByCreationDate(left, right)).toBe(1);
  });
});
