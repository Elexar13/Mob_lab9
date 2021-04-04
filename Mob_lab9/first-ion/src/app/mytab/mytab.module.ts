import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { MytabPageRoutingModule } from './mytab-routing.module';

import { MytabPage } from './mytab.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    MytabPageRoutingModule
  ],
  declarations: [MytabPage]
})
export class MytabPageModule {}
