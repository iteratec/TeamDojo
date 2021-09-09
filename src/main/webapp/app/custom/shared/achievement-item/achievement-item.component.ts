import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbPopover } from '@ng-bootstrap/ng-bootstrap';
import { ILevel } from 'app/entities/level/level.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { LevelService } from 'app/entities/level/service/level.service';
import { ISkill } from 'app/entities/skill/skill.model';

@Component({
  selector: 'jhi-achievement-item',
  templateUrl: './achievement-item.component.html',
  styleUrls: ['./achievement-item.scss'],
})
export class AchievementItemComponent {
  @Input() item: any;
  @Input() irrelevancePercentage = 0;
  @Input() progress = 0;
  @Input() type = '';
  @Input() hasStatus = false;
  @Input() size = '10vh';
  @Input() completable = false;
  @Output() itemSelected = new EventEmitter<ILevel | IBadge>();
  @ViewChild('popover') popover!: NgbPopover;
  @Input() hasAuthority = false;
  inEditMode = false;
  private _active = false;

  get active(): boolean {
    return this._active;
  }

  @Input()
  set active(active: boolean) {
    this._active = active;
    if (!active) {
      this.inEditMode = false;
      this.popover.close();
    }
  }

  constructor(private badgeService: BadgeService, private levelService: LevelService) {}

  saveInstantMultiplier(newInstantMultiplier): void {
    if (newInstantMultiplier || newInstantMultiplier === 0) {
      this.item.instantMultiplier = newInstantMultiplier;
      switch (this.type) {
        case 'item-badge':
          this.badgeService.update(this.item).subscribe((res: HttpResponse<ISkill>) => {
            this.inEditMode = false;
          });
          break;
        case 'item-level':
          this.levelService.update(this.item).subscribe((res: HttpResponse<ISkill>) => {
            this.inEditMode = false;
          });
          break;
      }
    }
    if (!newInstantMultiplier) {
      this.popover.close();
    }
  }

  selectItem(event): void {
    event.preventDefault();
    event.stopPropagation();
    this.inEditMode = false;
    this.itemSelected.emit(this.item);
    if (!this._active) {
      if (!this.popover.isOpen()) {
        this.popover.open();
      }
    } else {
      if (this.popover.isOpen()) {
        this.popover.close();
      }
    }
  }

  toggleEditMode(event): void {
    event.preventDefault();
    event.stopPropagation();
    if (this.hasAuthority) {
      this.inEditMode = !this.inEditMode;
    }
  }

  onPopupEnter(): void {
    if (this.inEditMode) {
      this.popover.close();
    }
    this.popover.open();
  }

  onPopupLeave(): void {
    if (!this.inEditMode) {
      this.popover.close();
    }
  }

  get progressWidth(): number {
    return (this.progress * (100 - this.irrelevancePercentage)) / 100.0;
  }

  get itemStatusCssClass(): string {
    let itemStatus;
    const requiredScore = this.item.requiredScore * 100;
    if (this.progress >= requiredScore && this.completable) {
      itemStatus = 'complete';
    } else if (this.progress > 0) {
      itemStatus = 'incomplete';
    } else {
      itemStatus = 'disabled';
    }
    return this.hasStatus ? (this.type ? `${this.type}-${itemStatus}` : `item-${itemStatus}`) : '';
  }
}
