import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Country } from 'src/app/models/Country';
import { CountryService } from 'src/app/services/country/country.service';
import { CurrencyService } from 'src/app/services/currency/currency.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-country',
  templateUrl: './create-country.component.html',
  styleUrls: ['./create-country.component.sass']
})
export class CreateCountryComponent {

  country: Country = {
    code: '',
    isoCode: '',
    countryName: '',
    shortCode: '',
    isdCode: '',
    active: '1'
  };

  activeCurrencies: { id: number; code: string }[] = [];

  constructor(
    private readonly countryService: CountryService, 
    private readonly currencyService: CurrencyService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.loadActiveCurrencies();
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

  onSelect(event: Event, currency: { id: number; code: string }) {
    event.preventDefault();
    this.country.currencyId = currency.id;
  }

  onSubmit(): void {
    const { code, isoCode, countryName, shortCode, isdCode } = this.country;

    console.log('Country:', this.country);
    

    if (
      !String(code).trim() ||
      !String(countryName).trim() ||
      !String(isoCode).trim() ||
      !String(isdCode).trim() ||
      !String(shortCode).trim() 
    ) {
      Swal.fire({
        title: 'Missing Fields',
        text: 'Please fill in all required fields.',
        icon: 'warning',
        timer: 1500,
        showConfirmButton: false
      });
      return;
    }

    this.countryService.createCountry(this.country).subscribe({
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
            text: 'Country created successfully.',
            icon: 'success',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/layout/country']);
          });
        }
      },
      error: (err) => {
        console.error('Unexpected error:', err);
        Swal.fire({
          title: 'Error!',
          text: 'An unexpected error occurred. Please try again later.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
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
      cancelButtonText: 'Stay here'
    }).then((result) => {
      if (result.isConfirmed) {
        this.resetForm();
      }
    });
  }

  onStatusToggle(event: any): void {
    this.country.active = event.target.checked ? '1' : '0';
  }

  private resetForm(): void {
    this.country = {
      code: '',
      isoCode: '',
      countryName: '',
      shortCode: '',
      isdCode: '',
      active: '1'
    };
  }
}
