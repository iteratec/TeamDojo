/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, Input, SimpleChanges, SimpleChange } from '@angular/core';
import { ITeam } from 'app/entities/team/team.model';
import dayjs from 'dayjs/esm';
import { ImageService } from '../../../entities/image/service/image.service';

@Component({
  selector: 'jhi-team-image',
  templateUrl: './team-image.component.html',
  styleUrls: ['./team-image.scss'],
})
export class TeamImageComponent {
  @Input() team?: ITeam;
  @Input() size = '50px';
  @Input() imageSize = 'large';
  @Input() hasPlaceholder = true;
  @Input() hasBorder = true;
  @Input() hasOverlay = false;
  @Input() showExpiredLabel = true;

  constructor(protected imageService: ImageService) {
    this.queryImageHash(this.team);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (Object.prototype.hasOwnProperty.call(changes, 'team')) {
      const teamChange: SimpleChange = changes['team'];

      this.queryImageHash(teamChange.currentValue);
    }
  }

  isExpired(team?: ITeam): boolean {
    if (team?.expirationDate != null) {
      return team.expirationDate.isBefore(dayjs(), 'day');
    }

    return false;
  }

  private queryImageHash(team?: ITeam): void {
    if (team?.image?.id) {
      this.imageService.find(team.image.id).subscribe(response => {
        if (this.team?.image) {
          this.team.image.hash = response.body?.hash;
        }
      });
    }
  }
}
