import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILevelSkill } from '../level-skill.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { LevelSkillService } from '../service/level-skill.service';
import { LevelSkillDeleteDialogComponent } from '../delete/level-skill-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-level-skill',
  templateUrl: './level-skill.component.html',
})
export class LevelSkillComponent implements OnInit {
  levelSkills: ILevelSkill[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected levelSkillService: LevelSkillService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.levelSkills = [];
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

    this.levelSkillService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<ILevelSkill[]>) => {
          this.isLoading = false;
          this.paginateLevelSkills(res.body, res.headers);
        },
        error: () => {
          this.isLoading = false;
        },
      });
  }

  reset(): void {
    this.page = 0;
    this.levelSkills = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILevelSkill): number {
    return item.id!;
  }

  delete(levelSkill: ILevelSkill): void {
    const modalRef = this.modalService.open(LevelSkillDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.levelSkill = levelSkill;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLevelSkills(data: ILevelSkill[] | null, headers: HttpHeaders): void {
    const linkHeader = headers.get('link');
    if (linkHeader) {
      this.links = this.parseLinks.parse(linkHeader);
    } else {
      this.links = {
        last: 0,
      };
    }
    if (data) {
      for (const d of data) {
        this.levelSkills.push(d);
      }
    }
  }
}
