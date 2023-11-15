/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, Input, SimpleChanges, SimpleChange } from '@angular/core';
import { ITeam } from 'app/entities/team/team.model';
import dayjs from 'dayjs/esm';
import { ImageService } from '../../../entities/image/service/image.service';
import { Team } from 'app/custom/custom.types';
import { TruncateStringPipe } from '../pipe/truncate-string.pipe';
import SharedModule from 'app/shared/shared.module';

@Component({
  selector: 'jhi-team-image',
  templateUrl: './team-image.component.html',
  styleUrls: ['./team-image.scss'],
  standalone: true,
  imports: [TruncateStringPipe, SharedModule]
})
export class TeamImageComponent {
  @Input() team?: Team;
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

  isExpired(team?: Team): boolean {
    if (team?.expirationDate != null) {
      return team.expirationDate.isBefore(dayjs(), 'day');
    }

    return false;
  }

  private queryImageHash(team?: Team): void {
    if (team?.image?.id) {
      // TODO: getHash method not availible since update
      // this.imageService.getHash(team.image.id).subscribe(response => {
      //   if (this.team?.image) {
      //     this.team.image.hash = response.body;
      //   }
      // });
    }
  }
}
