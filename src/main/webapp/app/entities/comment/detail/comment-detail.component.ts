import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IComment } from '../comment.model';
import { TranslateModelPipe } from 'app/custom/shared/translate-model/translate-model.pipe';

@Component({
  standalone: true,
  selector: 'jhi-comment-detail',
  templateUrl: './comment-detail.component.html',
  imports: [
    SharedModule, 
    RouterModule, 
    DurationPipe, 
    FormatMediumDatetimePipe, 
    FormatMediumDatePipe,
    /// ### MODIFICATION-START ###
    TranslateModelPipe
    /// ### MODIFICATION-END ###
  ],
})
export class CommentDetailComponent {
  @Input() comment: IComment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
