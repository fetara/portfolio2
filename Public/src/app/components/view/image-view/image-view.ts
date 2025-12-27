import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-image-view',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule],
  templateUrl: './image-view.html',
  styleUrl: './image-view.css',
})
export class ImageViewComponent {
  @Output() remove = new EventEmitter<void>();

  onRemove(event: Event) {
    event.stopPropagation();
    this.remove.emit();
  }
}
