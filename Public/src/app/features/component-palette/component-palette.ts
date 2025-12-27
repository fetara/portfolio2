import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-component-palette',
  standalone: true,
  imports: [CommonModule, DragDropModule, MatIconModule],
  templateUrl: './component-palette.html',
  styleUrl: './component-palette.css',
})
export class ComponentPaletteComponent {
  availableComponents = [
    { id: 'text', name: 'Text Block' },
    { id: 'image', name: 'Image' },
    { id: 'button', name: 'Button' },
  ];

  getIcon(componentId: string): string {
    const icons: { [key: string]: string } = {
      'text': 'text_fields',
      'image': 'image',
      'button': 'smart_button'
    };
    return icons[componentId] || 'widgets';
  }
}
