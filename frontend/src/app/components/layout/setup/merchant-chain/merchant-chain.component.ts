import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Merchant } from 'src/app/models/Merchant';
import { MerchantChainService } from 'src/app/services/merchant-chain/merchant-chain.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-merchant-chain',
  templateUrl: './merchant-chain.component.html',
  styleUrls: ['./merchant-chain.component.sass']
})
export class MerchantChainComponent implements OnInit {

  merchantChains: Merchant[] = [];
  filteredMerchantChains: Merchant[] = [];
  filterValue: string = '';
  selectedStatusLabel: string = 'All Status';

  constructor(
    private readonly merchantChainService: MerchantChainService,
    private readonly router: Router
  ) { }

  ngOnInit(): void {
    this.loadAllMerchantChains();
  }

  loadAllMerchantChains(): void {
    this.merchantChainService.getAllMerchants().subscribe({
      next: (res) => {
        if (Array.isArray(res?.data)) {
          this.merchantChains = res.data;
        } else {
          this.merchantChains = [];
        }
        this.filteredMerchantChains = [...this.merchantChains];
      },
      error: () => {
        this.merchantChains = [];
        this.filteredMerchantChains = [];
      }
    });
  }

  onEnterKey(event: Event): void {
    if (this.filterValue.trim()) {
      this.filteredMerchantChains = this.merchantChains.filter(chain =>
        chain.name?.toLowerCase().includes(this.filterValue.toLowerCase())
      );
    } else {
      this.filteredMerchantChains = [...this.merchantChains];
    }
  }

  onReset(): void {
    this.filterValue = '';
    this.selectedStatusLabel = 'All Status';
    this.filteredMerchantChains = [...this.merchantChains];
  }

  filterByStatus(event: Event, status: string): void {
    event.preventDefault();

    if (status === 'all') {
      this.selectedStatusLabel = 'All Status';
      this.filteredMerchantChains = [...this.merchantChains];
    } else {
      this.selectedStatusLabel = status === '1' ? 'Active' : 'Inactive';
      this.filteredMerchantChains = this.merchantChains.filter(mc => mc.locked === status);
    }
  }

  updateStatus(event: Event, merchant: Merchant): void {
    const checkbox = event.target as HTMLInputElement;
    merchant.locked = checkbox.checked ? '1' : '0';
    this.updateMerchant(merchant);
  }

  updatePosSafetyStatus(event: Event, merchant: Merchant): void {
    const checkbox = event.target as HTMLInputElement;
    merchant.posSafetyFlag = checkbox.checked ? '1' : '0';
    this.updateMerchant(merchant);
  }

  updateMerchant(merchant: Merchant): void {
    if (merchant.id !== undefined) {
      this.merchantChainService.updateMerchant(merchant.id, merchant).subscribe();
    }
  }

  deleteMerchantChain(id: number, name: string): void {
    Swal.fire({
      title: `Are you sure?`,
      text: `Do you want to delete ${name} record?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.merchantChainService.deleteMerchant(id).subscribe({
          next: () => {
            this.merchantChains = this.merchantChains.filter(m => m.id !== id);
            this.filteredMerchantChains = this.filteredMerchantChains.filter(m => m.id !== id);
            Swal.fire('Deleted!', `${name} has been deleted.`, 'success');
          },
          error: () => {
            Swal.fire('Error!', `There was a problem deleting ${name}.`, 'error');
          }
        });
      }
    });
  }

  navigateToCreateMerchantChain(): void {
    this.router.navigate(['/layout/merchant-chain/create']);
  }

  navigateToEditMerchantChain(id: number): void {
    this.router.navigate(['/layout/merchant-chain/edit', id]);
  }
}
