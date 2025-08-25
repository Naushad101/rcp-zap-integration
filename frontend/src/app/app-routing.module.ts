import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { 
    path: '', 
    redirectTo: 'layout', 
    pathMatch: 'full' },
  {
    path: 'layout',
    loadChildren: () =>
      import('./components/layout/layout.module').then((l) => l.LayoutModule),
  },
  {
    path: '**',
    loadComponent: () =>
      import('./components/error/error.component').then((l) => l.ErrorComponent),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
