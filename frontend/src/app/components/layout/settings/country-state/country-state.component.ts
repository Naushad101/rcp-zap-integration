import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CountryState } from 'src/app/models/CountryState';
import { CountryStateService } from 'src/app/services/country-state/country-state.service';
import { CountryService } from 'src/app/services/country/country.service';

@Component({
  selector: 'app-country-state',
  templateUrl: './country-state.component.html',
  styleUrls: ['./country-state.component.sass']
})
export class CountryStateComponent implements OnInit {

  countryStates: CountryState[] = [];
  filteredCountryStates: CountryState[] = [];
  filterValue: string = '';
  selectedStatusLabel: string = 'All Status';
  selectedCountryLabel: string = 'All Countries';
  
  activeCountries: { id: number; countryName: string }[] = [];

  constructor(
    private readonly countryStateService: CountryStateService,
    private readonly countryService: CountryService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.loadAllCountryStates();
    this.loadActiveCountries();
  }

  loadAllCountryStates(): void {
    this.countryStateService.getAllCountryStates().subscribe({
      next: (res) => {
        this.countryStates = res.data;
        this.filteredCountryStates = [...this.countryStates];
      }
    });
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

  onEnterKey(event: Event): void {
    if (this.filterValue.trim()) {
      this.filteredCountryStates = this.countryStates.filter((countryState: CountryState) =>
        countryState.stateName.toLowerCase().includes(this.filterValue.toLowerCase())
      );
    } else {
      this.filteredCountryStates = [...this.countryStates];
    }
  }

  onReset(): void {
    this.filterValue = '';
    this.selectedStatusLabel = 'All Status';
    this.filteredCountryStates = [...this.countryStates];
  }

  updateStatus(entry: CountryState): void {
    const updatedEntry = {
      ...entry,
      active: entry.active === '1' ? '0' : '1'
    };
    if (entry.id !== undefined) {
      this.countryStateService.updateCountryState(entry.id, updatedEntry).subscribe({
        next: () => {
          entry.active = updatedEntry.active;
        }
      });
    }
  }

  filterByStatus(event: Event, status: string): void {
    event.preventDefault();
  
    if (status === 'all') {
      this.selectedStatusLabel = 'All Status';
      this.filteredCountryStates = [...this.countryStates];
    } else {
      this.selectedStatusLabel = status === '1' ? 'Active' : 'Inactive';
      this.filteredCountryStates = this.countryStates.filter((countryStates: CountryState) => countryStates.active === status);
    }
  }

  filterByCountry(event: Event, countryName: string): void {
    event.preventDefault();

    if (countryName === 'all') {
      this.selectedCountryLabel = 'All Countries';
      this.filteredCountryStates = [...this.countryStates];
    } else {
      this.selectedCountryLabel = countryName;
      this.filteredCountryStates = this.countryStates.filter((countryState: CountryState) => countryState.country!.countryName === countryName);
    }
  }
  
  createCountryState(): void {
    this.router.navigate(['/layout/country-state/create']);
  }
}
