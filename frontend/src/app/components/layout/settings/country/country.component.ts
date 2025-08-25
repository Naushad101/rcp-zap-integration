import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Country } from 'src/app/models/Country';
import { CountryService } from 'src/app/services/country/country.service';

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.sass']
})
export class CountryComponent implements OnInit {

  countries: Country[] = [];
  filteredCountries: Country[] = [];
  filterValue: string = '';
  selectedStatusLabel: string = 'All Status';

  constructor(
    private readonly countryService: CountryService, 
    private readonly route: Router
  ) {}

  ngOnInit(): void {
    this.loadAllCountries();
  }

  loadAllCountries(): void {
    this.countryService.getAllCountries().subscribe({
      next: (res) => {
        this.countries = res.data;
        this.filteredCountries = [...this.countries];  
        console.log(this.filteredCountries);
        
      }
    });
    console.log(this.filteredCountries);
    
  }

  onEnterKey(event: Event): void {
    if (this.filterValue.trim()) {
      this.filteredCountries = this.countries.filter((country: Country) =>
        country.countryName.toLowerCase().includes(this.filterValue.toLowerCase())
      );
    } else {
      this.filteredCountries = [...this.countries];
    }
  }

  onReset(): void {
    this.filterValue = '';
    this.selectedStatusLabel = 'All Status';
    this.filteredCountries = [...this.countries]; 
  }

  updateCountryStatus(country: Country): void {
    const updatedCountry = {
      ...country,
      active: country.active === '1' ? '0' : '1'
    };
    
    if(country.id != undefined) {
      this.countryService.updateCountry(country.id, updatedCountry)
      .subscribe({
        next: () => {
          country.active = updatedCountry.active;
        }
      });
    }
  }

  filterByStatus(event: Event, status: string): void {
    event.preventDefault(); 

    if (status === 'all') {
      this.selectedStatusLabel = 'All Status';
      this.filteredCountries = [...this.countries];  
    } else {
      this.selectedStatusLabel = status === '1' ? 'Active' : 'Inactive';
      this.filteredCountries = this.countries.filter((country: Country) => country.active === status);
    }
  }

  navigateToCreateCountry(): void {
    this.route.navigate(['/layout/country/create']);
  }
}
