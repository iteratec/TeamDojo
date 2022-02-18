import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBadgeSkill } from '../badge-skill.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { BadgeSkillService } from '../service/badge-skill.service';
import { BadgeSkillDeleteDialogComponent } from '../delete/badge-skill-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-badge-skill',
  templateUrl: './badge-skill.component.html',
})
export class BadgeSkillComponent implements OnInit {
  badgeSkills: IBadgeSkill[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected badgeSkillService: BadgeSkillService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.badgeSkills = [];
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

    this.badgeSkillService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IBadgeSkill[]>) => {
          this.isLoading = false;
          this.paginateBadgeSkills(res.body, res.headers);
        },
        error: () => {
          this.isLoading = false;
        },
      });
  }

  reset(): void {
    this.page = 0;
    this.badgeSkills = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IBadgeSkill): number {
    return item.id!;
  }

  delete(badgeSkill: IBadgeSkill): void {
    const modalRef = this.modalService.open(BadgeSkillDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.badgeSkill = badgeSkill;
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

  protected paginateBadgeSkills(data: IBadgeSkill[] | null, headers: HttpHeaders): void {
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
        this.badgeSkills.push(d);
      }
    }
  }
}
