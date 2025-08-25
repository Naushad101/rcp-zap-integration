import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MerchantTerminalService } from 'src/app/services/merchant-terminal/merchant-terminal.service';
import { MerchantTerminal } from 'src/app/models/merchant-terminal';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-merchant-terminal',
  templateUrl: './create-merchant-terminal.component.html',
  styleUrls: ['./create-merchant-terminal.component.sass']
})
export class CreateMerchantTerminalComponent implements OnInit {
  isEditMode = false;
  pageTitle = 'Create';
  currentStep: number = 1;
  merchantTerminal: MerchantTerminal = {
    name: '',
    deviceType: '',
    deviceModelType: '',
    merchantChain: '',
    merchantStore: '',
    terminalId: '',
    pedId: '',
    pedSerialNo: '',
    activateOn: '',
    posSafety: false,
    status: true
  };
  deviceTypes: string[] = ['Type1', 'Type2', 'Type3'];
  deviceModels: string[] = ['Model1', 'Model2', 'Model3'];
  merchantChains: string[] = ['Chain1', 'Chain2', 'Chain3'];
  merchantStores: string[] = ['Store1', 'Store2', 'Store3'];
  isLoadingDeviceTypes = false;
  isLoadingDeviceModels = false;
  isLoadingMerchantChains = false;
  isLoadingMerchantStores = false;

  constructor(
    private readonly merchantTerminalService: MerchantTerminalService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    this.isEditMode = !!id;
    this.pageTitle = this.isEditMode ? 'Edit' : 'Create';

    if (this.isEditMode) {
      this.getMerchantTerminal(Number(id));
    }
  }

  getMerchantTerminal(id: number): void {
    if (id) {
      this.merchantTerminalService.getMerchantTerminalById(id).subscribe({
        next: (response: any) => {
          if (response && response.data) {
            this.merchantTerminal = response.data;
          } else {
            this.showError('Failed to load merchant terminal data');
          }
        },
        error: (error) => {
          console.error('Error loading merchant terminal:', error);
          this.showError('Error loading merchant terminal data');
        }
      });
    }
  }

  nextStep(): void {
    if (this.isStep1Valid() && this.currentStep === 1) {
      this.currentStep = 2;
    }
  }

  previousStep(): void {
    if (this.currentStep === 2) {
      this.currentStep = 1;
    }
  }

  isStep1Valid(): boolean {
    return !!(this.merchantTerminal.name && this.merchantTerminal.deviceType && this.merchantTerminal.deviceModelType &&
              this.merchantTerminal.merchantChain && this.merchantTerminal.merchantStore && this.merchantTerminal.terminalId &&
              this.merchantTerminal.pedId && this.merchantTerminal.pedSerialNo && this.merchantTerminal.activateOn);
  }

  isFormValid(): boolean {
    return this.isStep1Valid();
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      this.showError('Please fill in all required fields.');
      return;
    }

    const request = this.isEditMode
      ? this.merchantTerminalService.updateMerchantTerminal(this.merchantTerminal)
      : this.merchantTerminalService.createMerchantTerminal(this.merchantTerminal);

    request.subscribe({
      next: (res: any) => {
        if (res.success === false) {
          this.showError(res.message);
        } else {
          this.showSuccess(`Merchant Terminal ${this.isEditMode ? 'updated' : 'created'} successfully!`)
            .then(() => this.router.navigate(['/layout/merchant-terminals']));
        }
      },
      error: (error) => {
        console.error('Submit error:', error);
        this.showError('An unexpected error occurred.');
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
      cancelButtonText: 'Stay here',
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#6c757d'
    }).then((result) => {
      if (result.isConfirmed) {
        this.router.navigate(['/layout/merchant-terminals']);
      }
    });
  }

  onPosSafetyToggle(event: any): void {
    this.merchantTerminal.posSafety = event.target.checked;
  }

  onStatusToggle(event: any): void {
    this.merchantTerminal.status = event.target.checked;
  }

  private showError(message: string): void {
    Swal.fire({
      title: 'Error!',
      text: message,
      icon: 'error',
      confirmButtonColor: '#dc3545'
    });
  }

  private showSuccess(message: string): Promise<any> {
    return Swal.fire({
      title: 'Success!',
      text: message,
      icon: 'success',
      confirmButtonColor: '#28a745'
    });
  }
}