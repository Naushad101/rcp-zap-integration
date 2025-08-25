import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Currency } from 'src/app/models/Currency';
import { CurrencyService } from 'src/app/services/currency/currency.service';

@Component({
  selector: 'app-currency',
  templateUrl: './currency.component.html',
  styleUrls: ['./currency.component.sass']
})
export class CurrencyComponent implements OnInit {

  currencies: Currency[] = [];
  filteredCurrencies: Currency[] = [];
  filterValue: string = '';
  selectedStatusLabel: string = 'All Status';

  constructor(
    private readonly currencyService: CurrencyService, 
    private readonly route: Router
  ) {}

  ngOnInit(): void {
    this.loadAllCurrencies();
  }

  loadAllCurrencies(): void {
    this.currencyService.getAllCurrencies().subscribe({
      next: (res) => {
        this.currencies = res.data;
        this.filteredCurrencies = [...this.currencies];  
      }
    });
  }

  onEnterKey(event: Event): void {
    if (this.filterValue.trim()) {
      this.filteredCurrencies = this.currencies.filter((currency: Currency) =>
        currency.currencyName.toLowerCase().includes(this.filterValue.toLowerCase())
      );
    } else {
      this.filteredCurrencies = [...this.currencies];
    }
  }

  onReset(): void {
    this.filterValue = '';
    this.selectedStatusLabel = 'All Status';
    this.filteredCurrencies = [...this.currencies]; 
  }

  updateCurrencyStatus(currency: Currency): void {
    const updatedCurrency = {
      ...currency,
      active: currency.active === '1' ? '0' : '1'
    };
    if(currency.id != undefined) {
      this.currencyService.updateCurrency(currency.id, updatedCurrency)
      .subscribe({
        next: () => {
          currency.active = updatedCurrency.active;
        }
      });
    }
  }

  filterByStatus(event: Event, status: string): void {
    event.preventDefault(); 

    if (status === 'all') {
      this.selectedStatusLabel = 'All Status';
      this.filteredCurrencies = [...this.currencies];  
    } else {
      this.selectedStatusLabel = status === '1' ? 'Active' : 'Inactive';
      this.filteredCurrencies = this.currencies.filter((currency: Currency) => currency.active === status);
    }
  }

  navigateToCreateCurrency(): void {
    this.route.navigate(['/layout/currency/create']);
  }
}
