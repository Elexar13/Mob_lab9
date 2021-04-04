import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MytabPage } from './mytab.page';

const routes: Routes = [
  {
    path: '',
    component: MytabPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MytabPageRoutingModule {}
