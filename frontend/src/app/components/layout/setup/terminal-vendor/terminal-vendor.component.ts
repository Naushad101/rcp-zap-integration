import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TerminalVendorService } from 'src/app/services/terminal-vendor/terminal-vendor.service';
import { ResponseEntityData } from 'src/app/models/ResponseEntityData';
import { TerminalVendor } from 'src/app/models/TerminalVendor';

@Component({
  selector: 'app-terminal-vendor',
  templateUrl: './terminal-vendor.component.html',
  styleUrls: ['./terminal-vendor.component.sass']
})
export class TerminalVendorComponent implements OnInit {
  vendors: TerminalVendor[] = [];
  searchQuery: string = '';
  filteredVendors: TerminalVendor[] = [];
  totalCount: number = 0;

  constructor(private router: Router, private vendorService: TerminalVendorService) {}

  ngOnInit(): void {
    // Fetch initial vendor data
    this.loadVendors();
  }

  loadVendors(): void {
    // Simulate fetching data from a service
    this.vendorService.getAllTerminalVendors().subscribe((response: ResponseEntityData) => {
      this.vendors = response.data as TerminalVendor[];
      this.filteredVendors = [...this.vendors];
      this.totalCount = this.vendors.length;
    }, (error: any) => {
      console.error('Error loading vendors:', error);
    });
  }

  onSearch(): void {
    if (!this.searchQuery) {
      this.filteredVendors = [...this.vendors];
    } else {
      this.filteredVendors = this.vendors.filter(vendor =>
        vendor.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
    this.totalCount = this.filteredVendors.length;
  }

  onReset(): void {
    this.searchQuery = '';
    this.filteredVendors = [...this.vendors];
    this.totalCount = this.vendors.length;
  }

  onCreate(): void {
    // Navigate to the create vendor page
    this.router.navigate(['/layout/terminal-vendors/create']);
  }
}