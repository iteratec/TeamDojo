import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { NgbPopover } from '@ng-bootstrap/ng-bootstrap';

import { ISkill } from 'app/entities/skill/skill.model';
import { AchievableSkill, IAchievableSkill } from 'app/custom/entities/achievable-skill/achievable-skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';

@Component({
  selector: 'jhi-skill-score',
  templateUrl: './skill-score.component.html',
  styleUrls: ['./skill-score.scss'],
})
export class SkillScoreComponent {
  @Input() skill?: ISkill;
  @Input() hasAuthority = false;
  @Output() onSkillChanged = new EventEmitter<{ iSkill: ISkill; aSkill: IAchievableSkill }>();
  private _isEditingScore: { [index: number]: boolean } = {};
  @ViewChild('scorePopover') popover?: NgbPopover;

  constructor(private skillService: SkillService) {}

  updateScore(newScore: number): void {
    if (newScore || newScore === 0) {
      const skillPromise = this.skill.skillId ? this.skillService.find(this.skill.skillId).pipe(map(res => res.body)) : of(this.skill);

      skillPromise.subscribe(
        skill => {
          skill.score = newScore;
          this.skillService.update(skill).subscribe((res: HttpResponse<ISkill>) => {
            this.onSkillChanged.emit({
              iSkill: res.body,
              aSkill: null,
            });
            this.skill = res.body;
            this.popover.close();
          });
        },
        (res: HttpErrorResponse) => {
          console.log(res);
        }
      );
    } else {
      this.popover.close();
    }
  }

  onPopupEnter(popover: NgbPopover, isEditing: boolean): void {
    if (this.skill?.id) {
      this._isEditingScore[this.skill.id] = isEditing && this.hasAuthority;
    }
    if (this.isEditingScore()) {
      popover.close();
    }
    popover.open();
  }

  onPopupLeave(popover: NgbPopover): void {
    if (!this.isEditingScore()) {
      popover.close();
    }
  }

  isEditingScore(): boolean {
    if (this.skill?.id) {
      return this._isEditingScore[this.skill.id];
    }

    return false;
  }
}
