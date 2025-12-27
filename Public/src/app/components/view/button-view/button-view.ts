import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-button-view',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule],
  templateUrl: './button-view.html',
  styleUrl: './button-view.css',
})
export class ButtonViewComponent {
  @Output() remove = new EventEmitter<void>();

  onRemove(event: Event) {
    event.stopPropagation();
    this.remove.emit();
  }
}
