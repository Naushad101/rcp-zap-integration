import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { MerchantStoreService } from 'src/app/services/merchant-store/merchant-store';
import { MerchantStore } from 'src/app/models/merchant-store';
import { ResponseEntityData } from 'src/app/models/ResponseEntityData';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-merchant-store-view',
  templateUrl: './merchant-store-view.component.html',
  styleUrls: ['./merchant-store-view.component.sass']
})
export class MerchantStoreViewComponent implements OnInit {
  merchantStore: MerchantStore | null = null;
  loading: boolean = false;
  error: string | null = null;
  storeId!: number;

  private readonly regionMap: { [key: number]: string } = {
    1: 'North',
    2: 'South',
    3: 'East',
    4: 'West',
    5: 'Central'
  };

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly location: Location,
    private readonly merchantStoreService: MerchantStoreService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.storeId = +params['id'];
      if (this.storeId && !isNaN(this.storeId)) {
        this.loadMerchantStore();
      } else {
        this.error = 'Invalid store ID';
      }
    });
  }

  loadMerchantStore(): void {
    this.loading = true;
    this.error = null;

    this.merchantStoreService.getMerchantStoreById(this.storeId).subscribe({
      next: (response: ResponseEntityData<MerchantStore> | MerchantStore | MerchantStore[]) => {
        this.loading = false;

        // Case 1: Direct MerchantStore object
        if (this.isDirectMerchantStore(response)) {
          this.merchantStore = response;
          return;
        }

        // Case 2: Array response
        if (Array.isArray(response)) {
          const foundStore = response.find(store => store.id === this.storeId);
          if (foundStore) {
            this.merchantStore = foundStore;
            return;
          } else {
            this.error = 'Merchant store not found in response';
            this.loadDummyData();
            return;
          }
        }

        // Case 3: ResponseEntityData with single MerchantStore
        if (this.hasSuccessProperty(response)) {
          if (response.success && response.data) {
            this.merchantStore = response.data;
            return;
          } else {
            this.error = response.message || 'Failed to load merchant store details';
            this.loadDummyData();
            return;
          }
        }

        // If none of the above cases match, show error
        this.error = 'Invalid response format';
        this.loadDummyData();
      },
      error: (error) => {
        console.error('Error loading merchant store:', error);
        this.loading = false;
        this.error = 'Failed to load merchant store details';
        this.loadDummyData();
      }
    });
  }

  // Type guard to check if response is a direct MerchantStore object
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

  // Type guard to check if response is ResponseEntityData
  private hasSuccessProperty(response: any): response is ResponseEntityData<MerchantStore> {
    return response && typeof response === 'object' && 'success' in response;
  }

  loadDummyData(): void {
    const dummyStores: MerchantStore[] = [
      {
        id: 1,
        code: 'STORE001',
        name: 'Downtown Main Branch',
        description: 'Primary retail location in the city center with full service capabilities',
        activateOn: '2025-01-01T00:00:00.000Z',
        expiryOn: '2026-12-31T23:59:59.000Z',
        deleted: 'N',
        locked: 'N',
        posSafetyFlag: 'Y',
        reversalTimeout: '300',
        additionalAttribute: "{\"storeType\": \"outlet\", \"manager\": \"Jane Smith\"}",
        latitude: 40.7128,
        longitude: -74.0060,
        locationDetailId: 1,
        address1: '123 Main Street',
        address2: 'Suite 100',
        city: 'New York',
        zip: '10001',
        phone: '+1-555-123-4567',
        fax: '+1-555-123-4568',
        website: 'https://www.example.com',
        email: 'downtown@example.com',
        region: 1,
        atmDeleted: 'N',
        atmLocked: 'N',
        sublocation: false,
        locationId: '',
        merchantChain: '',
        country: '',
        state: '',
        merchantId: 0
      },
      {
        id: 2,
        code: 'STORE002',
        name: 'West Side Branch',
        description: 'Secondary location serving the western district',
        activateOn: '2025-02-01T00:00:00.000Z',
        expiryOn: '2026-12-31T23:59:59.000Z',
        deleted: 'N',
        locked: 'N',
        posSafetyFlag: 'N',
        reversalTimeout: '600',
        additionalAttribute: "{\"storeType\": \"outlet\", \"manager\": \"Jane Smith\"}",
        latitude: 34.0522,
        longitude: -118.2437,
        locationDetailId: 2,
        address1: '456 West Avenue',
        address2: '',
        city: 'Los Angeles',
        zip: '90001',
        phone: '+1-555-987-6543',
        fax: '',
        website: 'https://www.example-west.com',
        email: 'westside@example.com',
        region: 4,
        atmDeleted: 'N',
        atmLocked: 'N',
        sublocation: true,
        locationId: '',
        merchantChain: '',
        country: '',
        state: '',
        merchantId: 0
      },
      {
        id: 3,
        code: 'STORE003',
        name: 'North Plaza Store',
        description: 'Shopping mall location with extended hours',
        activateOn: '2025-03-01T00:00:00.000Z',
        expiryOn: '2026-12-31T23:59:59.000Z',
        deleted: 'N',
        locked: 'Y',
        posSafetyFlag: 'Y',
        reversalTimeout: '450',
        additionalAttribute: "{\"storeType\": \"outlet\", \"manager\": \"Jane Smith\"}",
        latitude: 41.8781,
        longitude: -87.6298,
        locationDetailId: 3,
        address1: '789 North Plaza',
        address2: 'Unit 205',
        city: 'Chicago',
        zip: '60601',
        phone: '+1-555-456-7890',
        fax: '+1-555-456-7891',
        website: '',
        email: 'northplaza@example.com',
        region: 1,
        atmDeleted: 'N',
        atmLocked: 'N',
        sublocation: false,
        locationId: '',
        merchantChain: '',
        country: '',
        state: '',
        merchantId: 0
      }
    ];

    const foundStore = dummyStores.find(store => store.id === this.storeId);
    if (foundStore) {
      this.merchantStore = foundStore;
      this.error = null;
      console.log('Loaded dummy data for store ID:', this.storeId);
    } else {
      this.error = 'Merchant store not found';
    }
  }

  retry(): void {
    this.loadMerchantStore();
  }

  goBack(): void {
    this.location.back();
  }

  onEdit(): void {
    if (this.merchantStore?.id) {
      this.router.navigate([`/layout/merchant-stores/edit/${this.merchantStore.id}`]);
    }
  }

  onDelete(): void {
    if (!this.merchantStore) return;

    Swal.fire({
      title: 'Are you sure?',
      text: `Do you want to delete "${this.merchantStore.name}"? This action cannot be undone.`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'Cancel',
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#6c757d'
    }).then((result) => {
      if (result.isConfirmed && this.merchantStore) {
        this.performDelete();
      }
    });
  }

  private performDelete(): void {
    if (!this.merchantStore) return;

    this.loading = true;
    this.merchantStoreService.deleteMerchantStore(this.merchantStore.id!).subscribe({
      next: (response) => {
        this.loading = false;
        if (response && response.success) {
          Swal.fire({
            title: 'Deleted!',
            text: 'Merchant store has been deleted successfully.',
            icon: 'success',
            confirmButtonColor: '#28a745'
          }).then(() => {
            this.router.navigate(['/layout/merchant-stores']);
          });
        } else {
          Swal.fire({
            title: 'Error!',
            text: response?.message || 'Failed to delete merchant store.',
            icon: 'error',
            confirmButtonColor: '#dc3545'
          });
        }
      },
      error: (error) => {
        this.loading = false;
        console.error('Error deleting merchant store:', error);
        Swal.fire({
          title: 'Error!',
          text: 'An error occurred while deleting the merchant store.',
          icon: 'error',
          confirmButtonColor: '#dc3545'
        });
      }
    });
  }

  getRegionName(regionId: number): string {
    return this.regionMap[regionId] || `Region ${regionId}`;
  }

  formatDate(dateString: string): string {
    if (!dateString) return 'N/A';
    
    try {
      const date = new Date(dateString);
      if (isNaN(date.getTime())) {
        return dateString;
      }
      return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    } catch (error) {
      return dateString;
    }
  }

  getStatus(): string {
    if (!this.merchantStore) return 'N/A';
    return (this.merchantStore.deleted === 'Y' || this.merchantStore.locked === 'Y') ? 'Disabled' : 'Enabled';
  }

  getPosStatus(): string {
    if (!this.merchantStore) return 'N/A';
    return this.merchantStore.posSafetyFlag === 'Y' ? 'Enabled' : 'Disabled';
  }
}