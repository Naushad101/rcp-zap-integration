import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MerchantStoreService } from 'src/app/services/merchant-store/merchant-store';
import { MerchantStore } from 'src/app/models/merchant-store';

@Component({
  selector: 'app-merchant-store',
  templateUrl: './merchant-store.component.html',
  styleUrls: ['./merchant-store.component.sass']
})
export class MerchantStoresComponent implements OnInit {
  merchantStores: MerchantStore[] = [];
  filteredMerchantStores: MerchantStore[] = [];
  filterValue: string = '';
  selectedChain: string = '';
  selectedStatus: string = '';
  selectedAddress: string = '';
  merchantChains: string[] = [];
  addresses: string[] = [];
  loading: boolean = false;

  constructor(
    private readonly router: Router,
    private readonly merchantStoreService: MerchantStoreService
  ) {}

  ngOnInit(): void {
    this.loadMerchantStores();
  }

  loadMerchantStores(): void {
    this.loading = true;
    this.merchantStoreService.getAllMerchantStores().subscribe({
      next: (response: any) => {
        if (response.data && Array.isArray(response.data)) {
          // Direct array response.d.
          this.merchantStores = response.data;
          this.filteredMerchantStores = [...this.merchantStores];
          this.updateFilterOptions();
        } else if (response.success && response.data) {
          // Wrapped response
          this.merchantStores = response.data;
          this.filteredMerchantStores = [...this.merchantStores];
          this.updateFilterOptions();
        }
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Error loading merchant stores:', error);
        this.loading = false;
        // Fallback to dummy data if API fails
        this.loadDummyData();
      }
    });
  }

  loadDummyData(): void {
    // Hardcoded dummy data as fallback - using new structure
    this.merchantStores = [
      {
        id: 1,
        code: 'LOC001',
        name: 'Main Branch',
        description: 'Primary location for retail operations',
        activateOn: '2025-07-29T12:00:00.000+00:00',
        expiryOn: '2026-07-29T12:00:00.000+00:00',
        deleted: 'N',
        locked: 'N',
        posSafetyFlag: 'Y',
        reversalTimeout: '300',
        additionalAttribute: "{\"storeType\": \"outlet\", \"manager\": \"Jane Smith\"}",
        latitude: 40.7128,
        longitude: -74.006,
        locationDetailId: 1,
        address1: '123 Main St',
        address2: 'Suite 100',
        city: 'New York',
        zip: '10001',
        phone: '555-123-4567',
        fax: '555-123-4568',
        website: 'https://example.com',
        email: 'contact@example.com',
        region: 1,
        locationId: '1',
        atmDeleted: 'N',
        atmLocked: 'N',
        sublocation: false,
        merchantChain: '',
        country: '',
        state: '',
        merchantId: 0
      }
    ];
    this.filteredMerchantStores = [...this.merchantStores];
    this.updateFilterOptions();
  }

  updateFilterOptions(): void {
    // Update filter options based on available data
    this.addresses = [...new Set(this.merchantStores.map(store => store.address1))].filter(Boolean);
    // Since there's no merchantChain in the API response, we can use region or another field
    this.merchantChains = [...new Set(this.merchantStores.map(store => `Region ${store.region}`))];
  }

  onEnterKey(event: any): void {
    this.filterMerchantStores();
  }

  onReset(): void {
    this.filterValue = '';
    this.selectedChain = '';
    this.selectedStatus = '';
    this.selectedAddress = '';
    this.filteredMerchantStores = [...this.merchantStores];
  }

  filterMerchantStores(): void {
    const filter = this.filterValue.toLowerCase();
    this.filteredMerchantStores = this.merchantStores.filter(store => {
      const matchesChain = !this.selectedChain || `Region ${store.region}` === this.selectedChain;
      const matchesStatus = !this.selectedStatus || this.getStatusFromFlags(store) === this.selectedStatus;
      const matchesAddress = !this.selectedAddress || store.address1 === this.selectedAddress;
      const matchesSearch = store.name.toLowerCase().includes(filter) ||
                           store.code.toLowerCase().includes(filter) ||
                           store.locationId?.toString().includes(filter);
      
      return matchesChain && matchesStatus && matchesAddress && matchesSearch;
    });
  }

  getStatusFromFlags(store: MerchantStore): string {
    // Convert locked/deleted flags to status
    if (store.deleted === 'Y' || store.locked === 'Y') {
      return 'DISABLED';
    }
    return 'ENABLED';
  }

  getDisplayStatus(store: MerchantStore): string {
    return this.getStatusFromFlags(store);
  }

  getPosStatus(store: MerchantStore): string {
    return store.posSafetyFlag === 'Y' ? 'ENABLED' : 'DISABLED';
  }

  onFilterChange(): void {
    this.filterMerchantStores();
  }

  onCreate(): void {
    this.router.navigate(['/layout/merchant-stores/create']);
  }

  onEdit(id: number): void {
    this.router.navigate([`/layout/merchant-stores/edit/${id}`]);
  }

  onDelete(id: number, name: string): void {
    if (confirm(`Are you sure you want to delete "${name}"? This action cannot be undone.`)) {
      this.loading = true;
      this.merchantStoreService.deleteMerchantStore(id).subscribe({
        next: (response: any) => {
          if (response.status === 'success') {
            // Remove from local arrays
            this.merchantStores = this.merchantStores.filter(store => store.id !== id);
            this.filteredMerchantStores = this.filteredMerchantStores.filter(store => store.id !== id);
            alert('Merchant store deleted successfully');
          } else {
            alert('Failed to delete merchant store: ' + response.message);
          }
          this.loading = false;
        },
        error: (error: any) => {
          console.error('Error deleting merchant store:', error);
          alert('Error deleting merchant store. Please try again.');
          this.loading = false;
        }
      });
    }
  }

  onView(id: number): void {
    this.router.navigate([`/layout/merchant-stores/view/${id}`]);
  }

  updateStatus(event: any, store: MerchantStore, field: string): void {
    const isChecked = event.target.checked;
    const updatedStore = { ...store };
    
    if (field === 'status') {
      // Update locked flag based on status
      updatedStore.locked = isChecked ? 'N' : 'Y';
    } else if (field === 'posSafety') {
      updatedStore.posSafetyFlag = isChecked ? 'Y' : 'N';
    }

    this.merchantStoreService.updateMerchantStore(store.id!, updatedStore).subscribe({
      next: (response: any) => {
        if (response.success) {
          // Update local store object
          if (field === 'status') {
            store.locked = updatedStore.locked;
          } else if (field === 'posSafety') {
            store.posSafetyFlag = updatedStore.posSafetyFlag;
          }
          console.log(`Updated ${field} for store ${store.name}`);
        } else {
          // Revert checkbox if update failed
          event.target.checked = !event.target.checked;
          alert('Failed to update status: ' + response.message);
        }
      },
      error: (error: any) => {
        console.error('Error updating status:', error);
        // Revert checkbox if update failed
        event.target.checked = !event.target.checked;
        alert('Error updating status. Please try again.');
      }
    });
  }
}