import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CountryState } from 'src/app/models/CountryState';
import { CountryStateService } from 'src/app/services/country-state/country-state.service';
import Swal from 'sweetalert2';
import { CountryService } from 'src/app/services/country/country.service';

@Component({
  selector: 'app-create-country-state',
  templateUrl: './create-country-state.component.html',
  styleUrls: ['./create-country-state.component.sass']
})
export class CreateCountryStateComponent implements OnInit {

  countryState: CountryState = {
    code: '',
    stateName: '',
    active: '1',
  };

  activeCountries: { id: number; countryName: string }[] = [];

  constructor(
    private readonly countryStateService: CountryStateService,
    private readonly countryService: CountryService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.loadActiveCountries();
  }

  loadActiveCountries(): void {
    this.countryService.getAllCountriesIdAndName().subscribe({
      next: (res) => {
        this.activeCountries = Object.entries(res.data).map(([id, code]) => ({
          id: Number(id),
          countryName: String(code)
        }));
      }
    });
  }

  onSubmit(): void {
    const { code, stateName, countryId } = this.countryState;

    if (!String(code).trim() || !String(stateName).trim() || !countryId) {
      Swal.fire({
        title: 'Missing Fields',
        text: 'Please fill in all required fields.',
        icon: 'warning',
        timer: 1500,
        showConfirmButton: false
      });
      return;
    }

    this.countryStateService.createCountryState(this.countryState).subscribe({
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
            text: 'Country state created successfully.',
            icon: 'success',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/layout/country-state']);
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
    this.countryState.active = event.target.checked;
  }

  private resetForm(): void {
    this.countryState = {
      code: '',
      stateName: '',
      active: '1'
    };
  }
}
