import { Component, Input } from '@angular/core';
import { ITeam } from 'app/entities/team/team.model';
import dayjs from 'dayjs/esm';

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

  isExpired(team?: ITeam): boolean {
    if (team?.expirationDate != null) {
      return team.expirationDate.isBefore(dayjs(), 'day');
    }

    return false;
  }
}
