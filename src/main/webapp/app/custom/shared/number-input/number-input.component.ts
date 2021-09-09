import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'jhi-number-input',
  templateUrl: './number-input.component.html',
  styleUrls: ['./number-input.scss'],
})
export class NumberInputComponent {
  @Input() value = 0;
  @Output() valueComitted = new EventEmitter<{}>();

  update(): void {
    this.valueComitted.emit(this.value);
  }

  close(): void {
    this.valueComitted.emit(undefined);
  }
}
