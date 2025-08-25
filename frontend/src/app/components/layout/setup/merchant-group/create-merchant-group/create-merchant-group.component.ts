import { Component } from '@angular/core';
import {  ActivatedRoute, Router } from '@angular/router';
import { Acquirer } from 'src/app/models/Acquirer';
import { CountryState } from 'src/app/models/CountryState';
import { MerchantInstitution } from 'src/app/models/MerchantInstitution';
import { MerchantInstitutionDetail } from 'src/app/models/MerchantInstitutionDetail';
import { AcquirerService } from 'src/app/services/acquirer/acquirer.service';
import { CountryStateService } from 'src/app/services/country-state/country-state.service';
import { CountryService } from 'src/app/services/country/country.service';
import { MerchantGroupService } from 'src/app/services/merchant-group/merchant-group.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-merchant-group',
  templateUrl: './create-merchant-group.component.html',
  styleUrls: ['./create-merchant-group.component.sass']
})
export class CreateMerchantGroupComponent {

  isDisabled = false;

  currentPage = 1;

  isEditMode = false;

  pageTitle = 'Create Merchant Group';

  merchantInstitution: MerchantInstitution = {
    institution: {
      id: 1,
      name: '',
      code: '',
      active: ''
    },
    acquirer: {} as Acquirer,
    name: '',
    description: '',
    activateOn: '',
    expiryOn: '',
    totalMerchant: 0,
    totalLocation: 0,
    totalDevice: 0,
    expried: false,
    merchantInstitutionDetail: {} as MerchantInstitutionDetail,
    message: '',
    locked: '1',
    deleted:'0'
  };

  activeAcquirers: { id: number; name: string }[] = [];

  activeCountries: { id: number; name: string }[] = [];

  activeCountryStates: { id: number; name: string }[] = [];

  allCountryStates: CountryState[] = [];

  constructor(
    private readonly acquirerService: AcquirerService,
    private readonly countryService: CountryService,
    private readonly countryStateService: CountryStateService,
    private readonly merchantGroupService: MerchantGroupService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.loadActiveAcquirers();
    this.loadActiveCountries();
    this.loadActiveCountryStates();

    const id = this.activatedRoute.snapshot.paramMap.get('id');
    this.isEditMode = !!id;
    this.pageTitle = this.isEditMode ? 'Edit' : 'Create';

    if (this.isEditMode) {
      this.getMerchantInstitution(Number(id));
    }
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

  loadActiveAcquirers(): void {
    this.acquirerService.getAllAcquirersIdAndName().subscribe({
      next: (res) => {
        this.activeAcquirers = Object.entries(res.data).map(([id, code]) => ({
          id: Number(id),
          name: String(code)
        }));
      }
    });
  }

  getMerchantInstitution(id: number): void {
    this.merchantGroupService.getMerchantInstitutionById(id).subscribe({
      next: (res) => {
        this.merchantInstitution = res.data;
        
        if (this.merchantInstitution.activateOn) {
          const activateDate = new Date(this.merchantInstitution.activateOn);
          this.merchantInstitution.activateOn = activateDate.toISOString().split('T')[0] + 'T' + 
            activateDate.toTimeString().split(' ')[0];
        }
        if (this.merchantInstitution.expiryOn) {
          const expiryDate = new Date(this.merchantInstitution.expiryOn);
          this.merchantInstitution.expiryOn = expiryDate.toISOString().split('T')[0] + 'T' + 
            expiryDate.toTimeString().split(' ')[0];
        }
      }
    });
  }

  onCountryChange(): void {
    const selectedCountry = this.merchantInstitution.merchantInstitutionDetail?.country;
    
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

  nextPage() {
    this.currentPage = 2;
  }

  previousPage() {
    this.currentPage = 1;
  }

  onSubmit(): void {
    const payload = {
      ...this.merchantInstitution,
      activateOn: new Date(this.merchantInstitution.activateOn).toISOString(),
      expiryOn: new Date(this.merchantInstitution.expiryOn).toISOString()
    };

    if (!this.isFormValid()) {
      Swal.fire({
        title: 'Missing Fields',
        text: 'Please fill in all required fields.',
        icon: 'warning',
        timer: 1500,
        showConfirmButton: false
      });
      return;
    }

    const request = this.isEditMode
      ? this.merchantGroupService.updateMerchantInstitution(this.merchantInstitution.id!, payload)
      : this.merchantGroupService.createMerchantInstitution(payload);

    request.subscribe({
      next: (res) => {
        if (res.status === 'failure') {
          Swal.fire('Error!', res.message, 'error');
        } else {
          Swal.fire('Success!', `Merchant Group ${this.isEditMode ? 'updated' : 'created'} successfully!`, 'success')
            .then(() => this.router.navigate(['/layout/merchant-group']));
        }
      },
      error: () => {
        Swal.fire('Error!', 'An unexpected error occurred.', 'error');
      }
    });
  }

  isFormValid(): boolean {
    const d = this.merchantInstitution.merchantInstitutionDetail!;
    const requiredFields = [
      this.merchantInstitution.name,
      this.merchantInstitution.activateOn,
      this.merchantInstitution.expiryOn,
      this.merchantInstitution.acquirer?.id,
      d.email,
      d.phone,
      d.address1,
      d.city,
      d.country?.id,
      d.countryState?.id,
      d.zip
    ];
    return requiredFields.every(val => String((val ?? '').toString()).trim() !== '');
  }

}
