import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-text-view',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule],
  templateUrl: './text-view.html',
  styleUrl: './text-view.css',
})
export class TextViewComponent {
  @Output() remove = new EventEmitter<void>();

  onRemove(event: Event) {
    event.stopPropagation();
    this.remove.emit();
  }
}
