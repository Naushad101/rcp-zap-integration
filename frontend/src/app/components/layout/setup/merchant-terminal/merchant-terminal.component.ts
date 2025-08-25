import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MerchantTerminalService } from 'src/app/services/merchant-terminal/merchant-terminal.service';
import { MerchantTerminal } from 'src/app/models/merchant-terminal';

@Component({
  selector: 'app-merchant-terminal',
  templateUrl: './merchant-terminal.component.html',
  styleUrls: ['./merchant-terminal.component.sass']
})
export class MerchantTerminalComponent implements OnInit {
  merchantTerminals: MerchantTerminal[] = [];
  filteredMerchantTerminals: MerchantTerminal[] = [];
  filterValue: string = '';
  loading: boolean = false;
  selectedChainFilter: string = '';
  selectedStoreFilter: string = '';
  selectedStatusFilter: string = '';
  merchantChains: string[] = ['Chain 1', 'Chain 2', 'Chain 3'];
  merchantStores: string[] = ['Store 1', 'Store 2', 'Store 3'];

  constructor(
    private readonly router: Router,
    private readonly merchantTerminalService: MerchantTerminalService
  ) {}

  ngOnInit(): void {
    this.loadMerchantTerminals();
  }

  loadMerchantTerminals(): void {
    this.loading = true;
    this.merchantTerminalService.getAllMerchantTerminals().subscribe({
      next: (response: any) => {
        if (response.data && Array.isArray(response.data)) {
          this.merchantTerminals = response.data;
          this.filteredMerchantTerminals = [...this.merchantTerminals];
        } else if (response.success && response.data) {
          this.merchantTerminals = response.data;
          this.filteredMerchantTerminals = [...this.merchantTerminals];
        }
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Error loading merchant terminals:', error);
        this.loading = false;
        this.loadDummyData();
      }
    });
  }

  loadDummyData(): void {
    this.merchantTerminals = [
      {
        id: 1,
        name: 'Terminal Store Alpha',
        terminalId: 'T001',
        pedId: 'P001',
        pedSerialNo: 'SN001234',
        deviceType: 'POS Terminal',
        activateOn: '2024-01-15',
        posSafety: true,
        status: true,
        deviceModelType: 'Model X1',
        merchantChain: 'Chain 1',
        merchantStore: 'Store Alpha'
      },
      {
        id: 2,
        name: 'Terminal Store Beta',
        terminalId: 'T002',
        pedId: 'P002',
        pedSerialNo: 'SN002345',
        deviceType: 'Mobile Terminal',
        activateOn: '2024-02-20',
        posSafety: false,
        status: true,
        deviceModelType: 'Model Y2',
        merchantChain: 'Chain 2',
        merchantStore: 'Store Beta'
      },
      {
        id: 3,
        name: 'Terminal Store Gamma',
        terminalId: 'T003',
        pedId: 'P003',
        pedSerialNo: 'SN003456',
        deviceType: 'Countertop Terminal',
        activateOn: '2024-03-10',
        posSafety: true,
        status: false,
        deviceModelType: 'Model Z3',
        merchantChain: 'Chain 1',
        merchantStore: 'Store Gamma'
      }
    ];
    this.filteredMerchantTerminals = [...this.merchantTerminals];
  }

  filterMerchantTerminals(): void {
    const filter = this.filterValue.toLowerCase();
    this.filteredMerchantTerminals = this.merchantTerminals.filter(terminal => {
      const matchesSearch = !filter || 
        terminal.name.toLowerCase().includes(filter) ||
        terminal.terminalId.toLowerCase().includes(filter) ||
        terminal.pedId.toLowerCase().includes(filter) ||
        terminal.pedSerialNo.toLowerCase().includes(filter);

      const matchesChain = !this.selectedChainFilter || terminal.merchantChain === this.selectedChainFilter;
      const matchesStore = !this.selectedStoreFilter || terminal.merchantStore === this.selectedStoreFilter;
      const matchesStatus = !this.selectedStatusFilter || 
        (this.selectedStatusFilter === 'enabled' && terminal.status) ||
        (this.selectedStatusFilter === 'disabled' && !terminal.status);

      return matchesSearch && matchesChain && matchesStore && matchesStatus;
    });
  }

  onChainFilterChange(event: any): void {
    this.selectedChainFilter = event.target.value;
    this.filterMerchantTerminals();
  }

  onStoreFilterChange(event: any): void {
    this.selectedStoreFilter = event.target.value;
    this.filterMerchantTerminals();
  }

  onStatusFilterChange(event: any): void {
    this.selectedStatusFilter = event.target.value;
    this.filterMerchantTerminals();
  }

  getStatus(terminal: MerchantTerminal): string {
    return terminal.status ? 'ENABLED' : 'DISABLED';
  }

  onReset(): void {
    this.filterValue = '';
    this.selectedChainFilter = '';
    this.selectedStoreFilter = '';
    this.selectedStatusFilter = '';
    this.filteredMerchantTerminals = [...this.merchantTerminals];
  }

  onSubmit(): void {
    this.router.navigate(['/layout/merchant-terminals/create']);
  }

  onEdit(id: number): void {
    this.router.navigate([`/layout/merchant-terminals/edit/${id}`]);
  }

  onDelete(id: number, name: string): void {
    if (confirm(`Are you sure you want to delete "${name}"? This action cannot be undone.`)) {
      this.loading = true;
      this.merchantTerminalService.deleteMerchantTerminal(id).subscribe({
        next: (response: any) => {
          if (response.status === 'success') {
            this.merchantTerminals = this.merchantTerminals.filter(t => t.id !== id);
            this.filteredMerchantTerminals = this.filteredMerchantTerminals.filter(t => t.id !== id);
            console.log('Terminal deleted successfully');
          }
          this.loading = false;
        },
        error: (error: any) => {
          console.error('Error deleting merchant terminal:', error);
          // For demo purposes, remove from local array even on error
          this.merchantTerminals = this.merchantTerminals.filter(t => t.id !== id);
          this.filteredMerchantTerminals = this.filteredMerchantTerminals.filter(t => t.id !== id);
          this.loading = false;
        }
      });
    }
  }

  onView(id: number): void {
    this.router.navigate([`/layout/merchant-terminals/view/${id}`]);
  }
}