import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AtmOption } from 'src/app/models/AtmOption';
import { CountryState } from 'src/app/models/CountryState';
import { GenericWrapper } from 'src/app/models/GenericWrapper';
import { Merchant } from 'src/app/models/Merchant';
import { MerchantDetail } from 'src/app/models/MerchantDetail';
import { MerchantProfile } from 'src/app/models/MerchantProfile';
import { CountryStateService } from 'src/app/services/country-state/country-state.service';
import { CountryService } from 'src/app/services/country/country.service';
import { CurrencyService } from 'src/app/services/currency/currency.service';
import { MerchantChainService } from 'src/app/services/merchant-chain/merchant-chain.service';
import { MerchantGroupService } from 'src/app/services/merchant-group/merchant-group.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-merchant-chain',
  templateUrl: './create-merchant-chain.component.html',
  styleUrls: ['./create-merchant-chain.component.sass']
})
export class CreateMerchantChainComponent {

  currentPage = 1;

  isEditMode = false;

  pageTitle = 'Create';

  merchant: Merchant = {
    id: 0,
    merchantInstitution: {} as GenericWrapper,
    code: '',
    name: '',
    description: '',
    activateOn: '',
    expiryOn: '',
    totalLocation: 0,
    totalDevice: 0,
    locked: '1',
    posSafetyFlag: '0',
    expired: false,
    message: '',
    reversalTimeout: '',
    deleted: '0',
    merchantProfile: {} as MerchantProfile,
    atmOption: {} as AtmOption,
    acquirerId: 0,
    merchantDetail: {} as MerchantDetail,
    currency: {} as GenericWrapper,
    bankName: '',
    accountNumber: ''
  };

  selectedServices: string[] = [];

  activeMerchantInstitutions: { id: number; name: string }[] = [];

  merchantCategoryCodes: { id: number; code: string }[] = [];

  activeCurrencies: { id: number; code: string }[] = [];

  activeCountries: { id: number; name: string }[] = [];

  activeCountryStates: { id: number; name: string }[] = [];

  allCountryStates: CountryState[] = [];

  constructor(
    private readonly merchantChainService: MerchantChainService,
    private readonly merchantGroupService: MerchantGroupService,
    private readonly currencyService: CurrencyService,
    private readonly countryService: CountryService,
    private readonly countryStateService: CountryStateService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.loadAllActiveMerchantInstitutions();
    this.loadAllMerchantCategoryCodes();
    this.loadActiveCurrencies();
    this.loadActiveCountries();
    this.loadActiveCountryStates();

    const id = this.activatedRoute.snapshot.paramMap.get('id');
    this.isEditMode = !!id;
    this.pageTitle = this.isEditMode ? 'Edit' : 'Create';

    if (this.isEditMode) {
      this.getMerchant(Number(id));
    }
  }

  loadAllActiveMerchantInstitutions(): void {
    this.merchantGroupService.getAllMerchantInstitutionsIdAndName().subscribe({
      next: (res) => {
        this.activeMerchantInstitutions = Object.entries(res.data).map(([id, name]) => ({
          id: Number(id),
          name: String(name)
        }));
      },
    });
  }

  loadAllMerchantCategoryCodes(): void {
    this.merchantChainService.getAllMerchantCategoryCodes().subscribe({
      next: (res) => {
        this.merchantCategoryCodes = Object.entries(res.data).map(([id, code]) => ({
          id: Number(id),
          code: String(code)
        }));
      },
    });
  }

  loadActiveCurrencies(): void {
    this.currencyService.getAllCurrenciesIdAndCode().subscribe({
      next: (res) => {
        this.activeCurrencies = Object.entries(res.data).map(([id, code]) => ({
          id: Number(id),
          code: String(code)
        }));
      }
    });
  }

  loadActiveCountries(): void {
    this.countryService.getAllCountriesIdAndName().subscribe({
      next: (res) => {
        this.activeCountries = Object.entries(res.data).map(([id, code]) => ({
          id: Number(id),
          name: String(code)
        }));
      }
    });
  }

  loadActiveCountryStates(): void {
    this.countryStateService.getAllCountryStatesIdAndName().subscribe({
      next: (res) => {
        this.allCountryStates = res.data;
      }
    }
    )
  }

  getMerchant(id: number): void {
    this.merchantChainService.getMerchantById(id).subscribe({
      next: (res) => {
        this.merchant = res.data;
      }
    });
  }

  onServiceChange(event: any): void {
    const value = event.target.value;
    const checked = event.target.checked;

    if (checked) {
      if (!this.selectedServices.includes(value)) {
        this.selectedServices.push(value);
      }
    } else {
      this.selectedServices = this.selectedServices.filter(v => v !== value);
    }

    this.merchant.merchantProfile!.services = this.selectedServices.join(',');
  }

  onPosSafetyToggle(event: any): void {
    this.merchant.posSafetyFlag = event.target.checked ? '1' : '0';
  }

  onPartialAuthChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.merchant.merchantProfile!.partialAuth = input.checked ? '1' : '0';
  }

  nextPage() {
    this.currentPage++;
  }

  previousPage() {
    this.currentPage--;
  }

  onCountryChange(): void {
    const selectedCountry = this.merchant.merchantDetail?.country;

    if (Array.isArray(this.allCountryStates) && selectedCountry?.id) {
      this.activeCountryStates = this.allCountryStates
        .filter(state => state.country?.id === selectedCountry.id && state.id !== undefined)
        .map(state => ({
          id: state.id as number,
          name: state.stateName
        }));
    } else {
      this.activeCountryStates = [];
    }
  }

  onSubmit(): void {
    const request = this.isEditMode
      ? this.merchantChainService.updateMerchant(this.merchant.id!, this.merchant)
      : this.merchantChainService.createMerchant(this.merchant);

    request.subscribe({
      next: (res) => {
        if (res.status === 'failure') {
          Swal.fire({
            title: 'Error!',
            text: res.message,
            icon: 'error',
            confirmButtonText: 'OK'
          });
        } else {
          Swal.fire({
            title: 'Success!',
            text: `Merchant Chain ${this.isEditMode ? 'updated' : 'created'} successfully!`,
            icon: 'success',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/layout/merchant-chain']);
          });
        }
      },
      error: (err) => {
        Swal.fire({
          title: 'Error!',
          text: 'An unexpected error occurred. Please try again later.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

}
