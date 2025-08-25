import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MerchantStore } from 'src/app/models/merchant-store';
import { MerchantStoreService } from 'src/app/services/merchant-store/merchant-store';
import { MerchantChainService } from 'src/app/services/merchant-chain/merchant-chain.service';
import { CountryService } from 'src/app/services/country/country.service';
import { CountryStateService } from 'src/app/services/country-state/country-state.service';
import { ResponseEntityData } from 'src/app/models/ResponseEntityData';
import { Merchant } from 'src/app/models/Merchant';
import { Country } from 'src/app/models/Country';
import { CountryState } from 'src/app/models/CountryState';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-merchant-store',
  templateUrl: './create-merchant-store.component.html',
  styleUrls: ['./create-merchant-store.component.sass']
})
export class CreateMerchantStoreComponent implements OnInit {
  isEditMode = false;
  pageTitle = 'Create';
  currentStep: number = 1;
  merchantChains: Merchant[] = [];
  countries: Country[] = [];
  countryStates: CountryState[] = [];
  filteredStates: CountryState[] = [];
  isLoadingMerchantChains = false;
  isLoadingCountries = false;
  isLoadingStates = false;
  
  merchantStore: MerchantStore = {
    code: '',
    name: '',
    description: '',
    activateOn: '',
    expiryOn: '',
    deleted: 'N',
    locked: 'N',
    posSafetyFlag: 'N',
    reversalTimeout: '300',
    additionalAttribute: "{\"storeType\": \"outlet\", \"manager\": \"Jane Smith\"}",
    latitude: 0,
    longitude: 0,
    address1: '',
    address2: '',
    city: '',
    zip: '',
    phone: '',
    fax: '',
    website: '',
    email: '',
    region: 0,
    atmDeleted: 'N',
    atmLocked: 'N',
    sublocation: false,
    locationId: '',
    merchantChain: '',
    merchantId:0,
    country: '',
    state: ''
  };

  constructor(
    private readonly merchantStoreService: MerchantStoreService,
    private readonly merchantChainService: MerchantChainService,
    private readonly countryService: CountryService,
    private readonly countryStateService: CountryStateService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    this.isEditMode = !!id;
    this.pageTitle = this.isEditMode ? 'Edit' : 'Create';

    // Load all dropdown data on component initialization
    this.loadMerchantChains();
    this.loadCountries();
    this.loadCountryStates();

    if (this.isEditMode) {
      this.getMerchantStore(Number(id));
    }
  }

  loadMerchantChains(): void {
    this.isLoadingMerchantChains = true;
    this.merchantChainService.getAllMerchants().subscribe({
      next: (response: ResponseEntityData) => {
        this.isLoadingMerchantChains = false;
        
        if (response.status === 'success' && response.data) {
          this.merchantChains = response.data;
        } else {
          this.showError('Failed to load merchant chains');
          this.merchantChains = [];
        }
      },
      error: (error) => {
        this.isLoadingMerchantChains = false;
        console.error('Error loading merchant chains:', error);
        this.showError('Error loading merchant chains');
        this.merchantChains = [];
      }
    });
  }

  loadCountries(): void {
    this.isLoadingCountries = true;
    this.countryService.getAllCountries().subscribe({
      next: (response: ResponseEntityData<Country[]>) => {
        this.isLoadingCountries = false;
        
        if (response.status === 'success' && response.data) {
          this.countries = response.data; // Show all countries, template will handle inactive ones
        } else {
          this.showError('Failed to load countries');
          this.countries = [];
        }
      },
      error: (error) => {
        this.isLoadingCountries = false;
        console.error('Error loading countries:', error);
        this.showError('Error loading countries');
        this.countries = [];
      }
    });
  }

  loadCountryStates(): void {
    this.isLoadingStates = true;
    this.countryStateService.getAllCountryStates().subscribe({
      next: (response: ResponseEntityData<CountryState[]>) => {
        this.isLoadingStates = false;
        
        if (response.status === 'success' && response.data) {
          this.countryStates = response.data.filter(state => state.active === 'Y');
          this.filterStatesByCountry();
        } else {
          this.showError('Failed to load states');
          this.countryStates = [];
        }
      },
      error: (error) => {
        this.isLoadingStates = false;
        console.error('Error loading states:', error);
        this.showError('Error loading states');
        this.countryStates = [];
      }
    });
  }

  onCountryChange(): void {
    this.merchantStore.state = ''; // Reset state when country changes
    this.filterStatesByCountry();
  }

  filterStatesByCountry(): void {
    if (this.merchantStore.country) {
      this.filteredStates = this.countryStates.filter(
        state => state?.country?.id?.toString() === this.merchantStore.country
      );
    } else {
      this.filteredStates = [];
    }
  }

  getMerchantStore(id: number): void {
    if (id) {
      this.merchantStoreService.getMerchantStoreById(id).subscribe({
        next: (response: ResponseEntityData<MerchantStore> | MerchantStore | MerchantStore[]) => {
          if (Array.isArray(response)) {
            this.merchantStore = response.find(store => store.id === id) || this.merchantStore;
          } else if (this.isDirectMerchantStore(response)) {
            this.merchantStore = response;
          } else if (response.success && response.data) {
            this.merchantStore = response.data;
          } else {
            this.showError('Failed to load merchant store data');
          }
          // Filter states after loading merchant store data
          this.filterStatesByCountry();
        },
        error: (error) => {
          console.error('Error loading merchant store:', error);
          this.showError('Error loading merchant store data');
        }
      });
    }
  }

  private isDirectMerchantStore(response: any): response is MerchantStore {
    return (
      response &&
      typeof response === 'object' &&
      !Array.isArray(response) &&
      typeof response.id === 'number' &&
      typeof response.code === 'string' &&
      typeof response.name === 'string' &&
      !('success' in response) &&
      !('data' in response)
    );
  }

  nextStep(): void {
    if (this.isStep1Valid() && this.currentStep === 1) {
      this.currentStep = 2;
    }
  }

  previousStep(): void {
    if (this.currentStep === 2) {
      this.currentStep = 1;
    }
  }

  isStep1Valid(): boolean {
    return !!(
      this.merchantStore.name &&
      this.merchantStore.region &&
      this.merchantStore.code &&
      this.merchantStore.merchantId &&
      this.merchantStore.activateOn &&
      this.merchantStore.expiryOn &&
      this.merchantStore.merchantChain
    );
  }

  isFormValid(): boolean {
    const step1Valid = this.isStep1Valid();
    const step2Valid = !!(
      this.merchantStore.email &&
      this.merchantStore.phone &&
      this.merchantStore.address1 &&
      this.merchantStore.city &&
      this.merchantStore.zip &&
      this.merchantStore.latitude !== undefined &&
      this.merchantStore.longitude !== undefined &&
      this.merchantStore.country &&
      this.merchantStore.state
    );

    return step1Valid && step2Valid;
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      this.showError('Please fill in all required fields.');
      return;
    }

    const request = this.isEditMode
      ? this.merchantStoreService.updateMerchantStore(this.merchantStore.id!, this.merchantStore)
      : this.merchantStoreService.createMerchantStore(this.merchantStore);

      console.log('Payload sent to backend:', this.merchantStore);

    request.subscribe({
      next: (res: ResponseEntityData<MerchantStore>) => {
        if (res.success === false) {
          this.showError(res.message);
        } else {
          this.showSuccess(
            `Merchant Store ${this.isEditMode ? 'updated' : 'created'} successfully!`
          ).then(() => this.router.navigate(['/layout/merchant-stores']));
        }
      },
      error: (error) => {
        console.error('Submit error:', error);
        this.showError('An unexpected error occurred.');
      }
    });
  }

  onCancel(): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'This will clear all entered data.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, clear it',
      cancelButtonText: 'Stay here',
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#6c757d'
    }).then((result) => {
      if (result.isConfirmed) {
        this.router.navigate(['/layout/merchant-stores']);
      }
    });
  }

  onPosSafetyToggle(event: any): void {
    this.merchantStore.posSafetyFlag = event.target.checked ? 'Y' : 'N';
  }

  onStatusToggle(event: any): void {
    this.merchantStore.locked = event.target.checked ? 'N' : 'Y';
    this.merchantStore.deleted = event.target.checked ? 'N' : 'Y';
  }

  private showError(message: string): void {
    Swal.fire({
      title: 'Error!',
      text: message,
      icon: 'error',
      confirmButtonColor: '#dc3545'
    });
  }

  private showSuccess(message: string): Promise<any> {
    return Swal.fire({
      title: 'Success!',
      text: message,
      icon: 'success',
      confirmButtonColor: '#28a745'
    });
  }

  private resetForm(): void {
    this.merchantStore = {
      code: '',
      name: '',
      description: '',
      activateOn: '',
      expiryOn: '',
      deleted: 'N',
      locked: 'N',
      posSafetyFlag: 'N',
      reversalTimeout: '300',
      additionalAttribute: "{\"storeType\": \"outlet\, \"manager\": \"Jane Smith\"}",
      latitude: 0,
      longitude: 0,
      address1: '',
      address2: '',
      city: '',
      zip: '',
      phone: '',
      fax: '',
      website: '',
      email: '',
      region: 0,
      atmDeleted: 'N',
      atmLocked: 'N',
      sublocation: false,
      locationId: '',
      merchantChain: '',
      merchantId: 0,
      country: '',
      state: ''
    };
    this.currentStep = 1;
  }
}