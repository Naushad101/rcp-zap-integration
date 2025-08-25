import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MerchantTerminalService } from 'src/app/services/merchant-terminal/merchant-terminal.service';
import { MerchantTerminal } from 'src/app/models/merchant-terminal';

@Component({
  selector: 'app-view-merchant-terminal',
  templateUrl: './view-merchant-terminal.component.html',
  styleUrls: ['./view-merchant-terminal.component.sass']
})
export class ViewMerchantTerminalComponent implements OnInit {
  merchantTerminal: MerchantTerminal | null = null;
  loading: boolean = false;
  terminalId: number;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly router: Router,
    private readonly merchantTerminalService: MerchantTerminalService
  ) {
    this.terminalId = Number(this.activatedRoute.snapshot.paramMap.get('id'));
  }

  ngOnInit(): void {
    if (this.terminalId) {
      this.loadMerchantTerminal();
    }
  }

  loadMerchantTerminal(): void {
    this.loading = true;
    this.merchantTerminalService.getMerchantTerminalById(this.terminalId).subscribe({
      next: (response: any) => {
        if (response && response.data) {
          this.merchantTerminal = response.data;
        } else {
          console.error('No terminal data found');
          this.loadDummyData();
        }
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Error loading merchant terminal:', error);
        this.loading = false;
        this.loadDummyData();
      }
    });
  }

  loadDummyData(): void {
    // Dummy data for demonstration
    this.merchantTerminal = {
      id: this.terminalId,
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
    };
  }

  onBack(): void {
    this.router.navigate(['/layout/merchant-terminals']);
  }

  onEdit(): void {
    if (this.merchantTerminal && this.merchantTerminal.id) {
      this.router.navigate([`/layout/merchant-terminals/edit/${this.merchantTerminal.id}`]);
    }
  }
}