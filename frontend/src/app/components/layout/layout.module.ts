import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LayoutRoutingModule } from './layout-routing.module';
import { FormsModule } from '@angular/forms';
import { SidebarComponent } from './sidebar/sidebar.component';
import { LayoutComponent } from './layout.component';
import { CurrencyComponent } from './settings/currency/currency.component';
import { CreateCurrencyComponent } from './settings/currency/create-currency/create-currency.component';
import { CreateCountryComponent } from './settings/country/create-country/create-country.component';
import { CountryComponent } from './settings/country/country.component';
import { AcquirerComponent } from './setup/acquirer/acquirer.component';
import { CreateAcquirerComponent } from './setup/acquirer/create-acquirer/create-acquirer.component';
import { MerchantGroupComponent } from './setup/merchant-group/merchant-group.component';
import { ViewAcquirerComponent } from './setup/acquirer/view-acquirer/view-acquirer.component';
import { CountryStateComponent } from './settings/country-state/country-state.component';
import { CreateCountryStateComponent } from './settings/country-state/create-country-state/create-country-state.component';
import { CreateMerchantGroupComponent } from './setup/merchant-group/create-merchant-group/create-merchant-group.component';
import { MerchantChainComponent } from './setup/merchant-chain/merchant-chain.component';
import { CreateMerchantChainComponent } from './setup/merchant-chain/create-merchant-chain/create-merchant-chain.component';
import { ViewMerchantGroupComponent } from './setup/merchant-group/view-merchant-group/view-merchant-group.component';
import { CreateMerchantStoreComponent } from './setup/merchant-store/create-merchant-store/create-merchant-store.component';
import { MerchantStoresComponent } from './setup/merchant-store/merchant-store.component';
import {TerminalTypeComponent} from './setup/terminal-types/terminal-type.component';
import {CreateTerminalTypeComponent} from './setup/terminal-types/create-terminal-types/create-terminal-type.component';
import { ReactiveFormsModule } from '@angular/forms';
import {TerminalVendorComponent} from './setup/terminal-vendor/terminal-vendor.component';
import {CreateTerminalVendorComponent} from './setup/terminal-vendor/create-terminal-vendor/create-terminal-vendor.component';
import {TerminalSitingComponent} from './setup/terminal-siting/terminal-siting.component';
import {CreateTerminalSitingComponent} from './setup/terminal-siting/create-terminal-siting/create-terminal-siting.component';
import { TerminalModelComponent } from './setup/terminal-model/terminal-model.component';
import { CreateTerminalModelComponent } from './setup/terminal-model/create-terminal-model/create-terminal-model.component';
import { MerchantStoreViewComponent } from './setup/merchant-store/view-merchant-store/merchant-store-view.component';
import { MerchantTerminalComponent } from './setup/merchant-terminal/merchant-terminal.component';
import { CreateMerchantTerminalComponent } from './setup/merchant-terminal/create-merchant-terminal/create-merchant-terminal.component';
import { ViewMerchantTerminalComponent } from './setup/merchant-terminal/view-merchant-terminal/view-merchant-terminal.component';


@NgModule({
  declarations: [
    LayoutComponent,
    SidebarComponent,
    CurrencyComponent,
    CreateCurrencyComponent,
    CountryComponent,
    CreateCountryComponent,
    AcquirerComponent,
    CreateAcquirerComponent,
    MerchantGroupComponent,
    ViewAcquirerComponent,
    CountryStateComponent,
    CreateCountryStateComponent,
    CreateMerchantGroupComponent,
    MerchantChainComponent,
    CreateMerchantChainComponent,
    ViewMerchantGroupComponent,
    CreateMerchantStoreComponent,
    MerchantStoreViewComponent,
    MerchantStoresComponent,
    TerminalTypeComponent,
    CreateTerminalTypeComponent,
    TerminalVendorComponent,
    CreateTerminalVendorComponent,
    TerminalSitingComponent,
    CreateTerminalSitingComponent,
    TerminalModelComponent,
    CreateTerminalModelComponent,
    MerchantTerminalComponent,
    CreateMerchantTerminalComponent,
    ViewMerchantTerminalComponent
  ],
  imports: [
    CommonModule,
    LayoutRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class LayoutModule { }
