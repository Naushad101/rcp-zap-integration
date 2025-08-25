import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './layout.component';
import { CurrencyComponent } from './settings/currency/currency.component';
import { CreateCurrencyComponent } from './settings/currency/create-currency/create-currency.component';
import { CreateCountryComponent } from './settings/country/create-country/create-country.component';
import { CountryComponent } from './settings/country/country.component';
import { AcquirerComponent } from './setup/acquirer/acquirer.component';
import { CreateAcquirerComponent } from './setup/acquirer/create-acquirer/create-acquirer.component';
import { ViewAcquirerComponent } from './setup/acquirer/view-acquirer/view-acquirer.component';
import { CountryStateComponent } from './settings/country-state/country-state.component';
import { CreateCountryStateComponent } from './settings/country-state/create-country-state/create-country-state.component';
import { MerchantGroupComponent } from './setup/merchant-group/merchant-group.component';
import { CreateMerchantGroupComponent } from './setup/merchant-group/create-merchant-group/create-merchant-group.component';
import { MerchantChainComponent } from './setup/merchant-chain/merchant-chain.component';
import { CreateMerchantChainComponent } from './setup/merchant-chain/create-merchant-chain/create-merchant-chain.component';
import { ViewMerchantGroupComponent } from './setup/merchant-group/view-merchant-group/view-merchant-group.component';
import { CreateMerchantStoreComponent } from './setup/merchant-store/create-merchant-store/create-merchant-store.component';
import { MerchantStoresComponent } from './setup/merchant-store/merchant-store.component';
import {TerminalTypeComponent} from './setup/terminal-types/terminal-type.component';
import {CreateTerminalTypeComponent} from './setup/terminal-types/create-terminal-types/create-terminal-type.component';
import { TerminalVendorComponent } from './setup/terminal-vendor/terminal-vendor.component';
import { CreateTerminalVendorComponent } from './setup/terminal-vendor/create-terminal-vendor/create-terminal-vendor.component';
import {TerminalSitingComponent} from './setup/terminal-siting/terminal-siting.component';
import {CreateTerminalSitingComponent} from './setup/terminal-siting/create-terminal-siting/create-terminal-siting.component';
import { TerminalModelComponent } from './setup/terminal-model/terminal-model.component';
import { CreateTerminalModelComponent } from './setup/terminal-model/create-terminal-model/create-terminal-model.component';
import { MerchantStoreViewComponent } from './setup/merchant-store/view-merchant-store/merchant-store-view.component';
import { MerchantTerminalComponent } from './setup/merchant-terminal/merchant-terminal.component';
import { CreateMerchantTerminalComponent } from './setup/merchant-terminal/create-merchant-terminal/create-merchant-terminal.component';
import { ViewMerchantTerminalComponent } from './setup/merchant-terminal/view-merchant-terminal/view-merchant-terminal.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'currency',
        component: CurrencyComponent
      },
      {
        path: 'currency/create',
        component: CreateCurrencyComponent
      },
      {
        path: 'country',
        component: CountryComponent
      },
      {
        path: 'country/create',
        component: CreateCountryComponent
      },
      {
        path: 'country-state',
        component: CountryStateComponent
      },
      {
        path: 'country-state/create',
        component: CreateCountryStateComponent
      },
      {
        path: 'acquirer',
        component: AcquirerComponent
      },
      {
        path: 'acquirer/create',
        component: CreateAcquirerComponent
      },
      {
        path: 'acquirer/edit/:id',
        component: CreateAcquirerComponent
      },
      {
        path: 'acquirer/view/:id',
        component: ViewAcquirerComponent
      },
      {
        path: 'merchant-group',
        component: MerchantGroupComponent
      },
      {
        path: 'merchant-group/create',
        component: CreateMerchantGroupComponent
      },
      {
        path: 'merchant-group/edit/:id',
        component: CreateMerchantGroupComponent
      },
      {
        path: 'merchant-group/view/:id',
        component: ViewMerchantGroupComponent
      },
      {
        path: 'merchant-chain',
        component: MerchantChainComponent
      },
      {
        path: 'merchant-stores/view/:id',
        component: MerchantStoreViewComponent
      },
      {
        path: 'merchant-chain/create',
        component: CreateMerchantChainComponent
      },
      {
        path: 'merchant-chain/edit/:id',
        component: CreateMerchantChainComponent
      },
      {
        path: 'merchant-stores',
        component: MerchantStoresComponent
      },
      {
        path: 'merchant-stores/create',
        component: CreateMerchantStoreComponent
      },
      {
        path: 'merchant-stores/edit/:id',
        component: CreateMerchantStoreComponent
      },
      {
        path:'terminal-types',
        component:TerminalTypeComponent
      },
      {
        path:'terminal-type/create',
        component:CreateTerminalTypeComponent
      },
      {
        path:'terminal-vendors',
        component:TerminalVendorComponent
      },
      {
        path:'terminal-vendors/create',
        component:CreateTerminalVendorComponent
      },
      {
        path:'terminal-sitings',
        component:TerminalSitingComponent
      },
      {
        path:'terminal-sitings/create',
        component:CreateTerminalSitingComponent
      },
      {
        path:'terminal-models',
        component:TerminalModelComponent
      },
      {
        path:'terminal-models/create',
        component:CreateTerminalModelComponent
      },
      {
        path:'merchant-terminals',
        component:MerchantTerminalComponent
      },
      {
        path:'merchant-terminals/create',
        component: CreateMerchantTerminalComponent
      },
      {
    path: 'merchant-terminals/edit/:id',
    component: CreateMerchantTerminalComponent
  },
  {
    path: 'merchant-terminals/view/:id',
    component: ViewMerchantTerminalComponent
  }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule { }