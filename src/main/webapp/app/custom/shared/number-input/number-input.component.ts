/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import SharedModule from 'app/shared/shared.module';

@Component({
  standalone: true,
  selector: 'jhi-number-input',
  templateUrl: './number-input.component.html',
  styleUrls: ['./number-input.scss'],
  imports: [SharedModule, RouterModule, FormsModule]
})
export class NumberInputComponent {
  @Input() value = 0;
  @Output() valueComitted = new EventEmitter<number | undefined>();

  update(): void {
    this.valueComitted.emit(this.value);
  }

  close(): void {
    this.valueComitted.emit(undefined);
  }
}
