import { Routes } from '@angular/router';
import { PageBuilderComponent } from './features/page-builder/page-builder';
import { PageRendererComponent } from './features/page-renderer/page-renderer';

export const routes: Routes = [
  { path: '', redirectTo: 'builder', pathMatch: 'full' },
  { path: 'builder', component: PageBuilderComponent },
  { path: 'page/:pageId', component: PageRendererComponent },
];
