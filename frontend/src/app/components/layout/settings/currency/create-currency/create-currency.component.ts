import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Currency } from 'src/app/models/Currency';
import { CurrencyService } from 'src/app/services/currency/currency.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-currency',
  templateUrl: './create-currency.component.html',
  styleUrls: ['./create-currency.component.sass']
})
export class CreateCurrencyComponent {

  currency: Currency = {
    code: '',
    isoCode: '',
    currencyName: '',
    currencyMinorUnit: '',
    active: '1'
  };

  constructor(
    private readonly currencyService: CurrencyService,
    private readonly router: Router
  ) {}

  onSubmit(): void {
    const { code, isoCode, currencyName, currencyMinorUnit } = this.currency;

    if (
      !String(code).trim() ||
      !String(isoCode).trim() ||
      !String(currencyName).trim() ||
      !String(currencyMinorUnit).trim()
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

    this.currencyService.createCurrency(this.currency).subscribe({
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
            text: 'Currency created successfully.',
            icon: 'success',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/layout/currency']);
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
    this.currency.active = event.target.checked ? '1' : '0';
  }

  private resetForm(): void {
    this.currency = {
      id: 0,
      code: '',
      isoCode: '',
      currencyName: '',
      currencyMinorUnit: '',
      active: '1'
    };
  }
}
