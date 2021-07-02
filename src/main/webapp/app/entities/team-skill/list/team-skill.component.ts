import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeamSkill } from '../team-skill.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { TeamSkillService } from '../service/team-skill.service';
import { TeamSkillDeleteDialogComponent } from '../delete/team-skill-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-team-skill',
  templateUrl: './team-skill.component.html',
})
export class TeamSkillComponent implements OnInit {
  teamSkills: ITeamSkill[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected teamSkillService: TeamSkillService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.teamSkills = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.teamSkillService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<ITeamSkill[]>) => {
          this.isLoading = false;
          this.paginateTeamSkills(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.teamSkills = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITeamSkill): number {
    return item.id!;
  }

  delete(teamSkill: ITeamSkill): void {
    const modalRef = this.modalService.open(TeamSkillDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.teamSkill = teamSkill;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTeamSkills(data: ITeamSkill[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.teamSkills.push(d);
      }
    }
  }
}
