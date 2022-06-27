/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'jhi-number-input',
  templateUrl: './number-input.component.html',
  styleUrls: ['./number-input.scss'],
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
