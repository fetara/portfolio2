import { Component, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatMenuModule } from '@angular/material/menu';
import { MatRadioModule } from '@angular/material/radio';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ComponentPaletteComponent } from '../component-palette/component-palette';
import {
  CdkDragDrop,
  DragDropModule,
  CdkDragEnd,
  CdkDragMove,
  moveItemInArray,
} from '@angular/cdk/drag-drop';
import { TextViewComponent } from '../../components/view/text-view/text-view';
import { ImageViewComponent } from '../../components/view/image-view/image-view';
import { ButtonViewComponent } from '../../components/view/button-view/button-view';

export interface PageComponent {
  id: string;
  name: string;
  x: number;
  y: number;
  width?: number;
  height?: number;
  uniqueId: string;
  zIndex?: number;
}

@Component({
  selector: 'app-page-builder',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatMenuModule,
    MatRadioModule,
    MatFormFieldModule,
    ComponentPaletteComponent,
    DragDropModule,
    TextViewComponent,
    ImageViewComponent,
    ButtonViewComponent,
  ],
  templateUrl: './page-builder.html',
  styleUrl: './page-builder.css',
})
export class PageBuilderComponent implements AfterViewInit {
  @ViewChild('canvasElement') canvasElement!: ElementRef;
  
  page: PageComponent[] = [];
  private componentCounter = 0;
  private maxZIndex = 1;
  
  canvasWidth = 0;
  canvasHeight = 0;
  gridSize = 10; // Pixels pour le snap to grid
  snapToGrid = false;
  
  selectedComponent: PageComponent | null = null;

  canvasBackground: { type: 'color' | 'image'; value: string } = {
    type: 'color',
    value: '#f0f2f5',
  };

  ngAfterViewInit() {
    // Obtenir les dimensions du canvas
    this.updateCanvasDimensions();
  }

  private updateCanvasDimensions() {
    if (this.canvasElement) {
      const element = this.canvasElement.nativeElement;
      this.canvasWidth = element.offsetWidth;
      this.canvasHeight = element.offsetHeight;
    }
  }

  drop(event: CdkDragDrop<any[]>) {
    // Si on déplace dans le même conteneur (réorganisation)
    if (event.previousContainer === event.container) {
      moveItemInArray(this.page, event.previousIndex, event.currentIndex);
      return;
    }

    // Si on glisse depuis la palette vers le canvas
    if (event.previousContainer.id === 'palette' && event.container.id === 'canvas') {
      const sourceComponent = event.previousContainer.data[event.previousIndex];
      
      // Obtenir la position du canvas
      const canvasElement = event.container.element.nativeElement;
      const canvasRect = canvasElement.getBoundingClientRect();
      
      // Calculer la position relative au canvas
      let x = event.dropPoint.x - canvasRect.left - 75; // Centrer sur le curseur
      let y = event.dropPoint.y - canvasRect.top - 25;
      
      // Snap to grid si activé
      if (this.snapToGrid) {
        x = Math.round(x / this.gridSize) * this.gridSize;
        y = Math.round(y / this.gridSize) * this.gridSize;
      }
      
      // S'assurer que le composant reste dans les limites
      x = Math.max(0, Math.min(x, this.canvasWidth - 150));
      y = Math.max(0, Math.min(y, this.canvasHeight - 100));
      
      // Créer le nouveau composant avec position
      const newComponent: PageComponent = {
        id: sourceComponent.id,
        name: sourceComponent.name,
        x: x,
        y: y,
        uniqueId: `${sourceComponent.id}_${this.componentCounter++}`,
        zIndex: ++this.maxZIndex
      };
      
      this.page.push(newComponent);
      this.selectComponent(newComponent);
    }
  }

  onDragMoved(event: CdkDragMove, component: PageComponent) {
    // Mettre à jour le z-index pendant le déplacement
    if (component.zIndex !== this.maxZIndex) {
      component.zIndex = ++this.maxZIndex;
    }
  }

  onDragEnded(event: CdkDragEnd, component: PageComponent) {
    // Calculer la nouvelle position en ajoutant la distance de glissement
    let newX = component.x + event.distance.x;
    let newY = component.y + event.distance.y;

    // Snap to grid si activé
    if (this.snapToGrid) {
      newX = Math.round(newX / this.gridSize) * this.gridSize;
      newY = Math.round(newY / this.gridSize) * this.gridSize;
    }

    // Obtenir les dimensions du canvas pour les limites
    this.updateCanvasDimensions();
    const elementRect = event.source.element.nativeElement.getBoundingClientRect();

    // S'assurer que le composant reste dans les limites du canvas
    newX = Math.max(0, Math.min(newX, this.canvasWidth - elementRect.width));
    newY = Math.max(0, Math.min(newY, this.canvasHeight - elementRect.height));

    // Mettre à jour la position du composant
    component.x = newX;
    component.y = newY;

    // Réinitialiser la transformation appliquée par cdkDrag
    event.source.element.nativeElement.style.transform = 'none';

    // Mettre à jour le z-index pour amener le composant au premier plan
    component.zIndex = ++this.maxZIndex;
  }

  removeItem(uniqueId: string) {
    const index = this.page.findIndex(item => item.uniqueId === uniqueId);
    if (index !== -1) {
      if (this.selectedComponent?.uniqueId === uniqueId) {
        this.selectedComponent = null;
      }
      this.page.splice(index, 1);
    }
  }

  selectComponent(component: PageComponent) {
    this.selectedComponent = component;
    component.zIndex = ++this.maxZIndex;
  }

  deselectComponent() {
    this.selectedComponent = null;
  }

  isSelected(component: PageComponent): boolean {
    return this.selectedComponent?.uniqueId === component.uniqueId;
  }

  getComponentStyle(component: PageComponent) {
    return {
      position: 'absolute',
      left: `${component.x}px`,
      top: `${component.y}px`,
      width: component.width ? `${component.width}px` : 'auto',
      height: component.height ? `${component.height}px` : 'auto',
      zIndex: component.zIndex || 1
    };
  }

  getCanvasStyle() {
    if (this.canvasBackground.type === 'image') {
      return {
        'background-image': `url('${this.canvasBackground.value}')`,
        'background-size': 'cover',
        'background-position': 'center',
      };
    }
    return {
      'background-color': this.canvasBackground.value,
    };
  }

  toggleSnapToGrid() {
    this.snapToGrid = !this.snapToGrid;
  }

  clearCanvas() {
    if (confirm('Êtes-vous sûr de vouloir supprimer tous les composants ?')) {
      this.page = [];
      this.selectedComponent = null;
      this.componentCounter = 0;
      this.maxZIndex = 1;
    }
  }

  duplicateComponent(component: PageComponent) {
    const newComponent: PageComponent = {
      ...component,
      x: component.x + 20,
      y: component.y + 20,
      uniqueId: `${component.id}_${this.componentCounter++}`,
      zIndex: ++this.maxZIndex
    };
    this.page.push(newComponent);
    this.selectComponent(newComponent);
  }

  bringToFront(component: PageComponent) {
    component.zIndex = ++this.maxZIndex;
  }

  sendToBack(component: PageComponent) {
    component.zIndex = 0;
    // Réorganiser les z-index
    this.page.forEach(item => {
      if (item.uniqueId !== component.uniqueId && item.zIndex) {
        item.zIndex++;
      }
    });
  }
}
