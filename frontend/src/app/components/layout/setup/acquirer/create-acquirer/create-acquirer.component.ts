import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Acquirer } from 'src/app/models/Acquirer';
import { AcquirerService } from 'src/app/services/acquirer/acquirer.service';
import { CountryService } from 'src/app/services/country/country.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-acquirer',
  templateUrl: './create-acquirer.component.html',
  styleUrls: ['./create-acquirer.component.sass']
})
export class CreateAcquirerComponent {

  isEditMode = false;

  pageTitle = 'Create';

  acquirer: Acquirer = {
    id: 0,
    name: '',
    code: '',
    onusValidate: '1',
    refundOffline: '1',
    active: '1',
    adviceMatch: true,
    countryId: 0,
    description: '',
    totalMerchantGroup: 0,
    deleted: '0',
    posSms: '',
    posDms: '',
    txtnypeSms: '',
    txtnypeDms: '',
    accounttypeSms: '',
    accounttypeDms: ''
  };

  activeCountries: { id: number; countryName: string }[] = [];

  constructor(
    private readonly acquirerService: AcquirerService,
    private readonly countryService: CountryService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.loadActiveCountries();

    const id = this.activatedRoute.snapshot.paramMap.get('id');
    this.isEditMode = !!id;
    this.pageTitle = this.isEditMode ? 'Edit' : 'Create';

    if (this.isEditMode) {
      this.getAcquirer(Number(id));
    }
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

  getAcquirer(id: number): void {
    if (id) {
      this.acquirerService.getAcquirerById(id).subscribe(response => {
        this.acquirer = response.data;
      });
    }
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      Swal.fire({
        title: 'Missing Fields',
        text: 'Please fill in all required fields.',
        icon: 'warning',
        timer: 1500,
        showConfirmButton: false
      });
      return;
    }

    const request = this.isEditMode
      ? this.acquirerService.updateAcquirer(this.acquirer.id!, this.acquirer)
      : this.acquirerService.createAcquirer(this.acquirer);

    request.subscribe({
      next: (res) => {
        if (res.status === 'failure') {
          Swal.fire('Error!', res.message, 'error');
        } else {
          Swal.fire(
            'Success!',
            `Acquirer ${this.isEditMode ? 'updated' : 'created'} successfully!`,
            'success'
          ).then(() => this.router.navigate(['/layout/acquirer']));
        }
      },
      error: () => {
        Swal.fire('Error!', 'An unexpected error occurred.', 'error');
      }
    });
  }

  isFormValid(): boolean {
    const requiredFields = [
      this.acquirer.name,
      this.acquirer.code,
      this.acquirer.countryId,
      this.acquirer.description
    ];

    return requiredFields.every(val => String((val ?? '').toString()).trim() !== '');
  }


  onCancel(): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'This will clear all entered data.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, clear it',
      cancelButtonText: 'Stay here'
    }).then((result) => {
      if (result.isConfirmed) {
        this.resetForm();
      }
    });
  }

  onStatusToggle(event: any): void {
    this.acquirer.active = event.target.checked;
  }

  onRefundOfflineToggle(event: any): void {
    this.acquirer.refundOffline = event.target.checked ? '1' : '0';
  }

  onOnusValidateToggle(event: any): void {
    this.acquirer.onusValidate = event.target.checked ? '1' : '0';
  }

  private resetForm(): void {
    this.acquirer = {
      id: 0,
      name: '',
      code: '',
      onusValidate: '0',
      refundOffline: '0',
      active: '1',
      adviceMatch: true,
      countryId: 0,
      description: '',
      totalMerchantGroup: 0,
      deleted: '0',
      posSms: '',
      posDms: '',
      txtnypeSms: '',
      txtnypeDms: '',
      accounttypeSms: '',
      accounttypeDms: ''
    };
  }
}
