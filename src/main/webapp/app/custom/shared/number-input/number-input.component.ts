import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'jhi-number-input',
  templateUrl: './number-input.component.html',
  styleUrls: ['./number-input.scss'],
})
export class NumberInputComponent {
  @Input() value: number;
  @Output() valueComitted = new EventEmitter<{}>();

  update() {
    this.valueComitted.emit(this.value);
  }

  close() {
    this.valueComitted.emit(null);
  }
}
