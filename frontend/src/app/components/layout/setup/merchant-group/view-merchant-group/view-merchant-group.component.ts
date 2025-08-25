import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

import { MerchantInstitution } from 'src/app/models/MerchantInstitution';
import { MerchantGroupService } from 'src/app/services/merchant-group/merchant-group.service';

@Component({
  selector: 'app-view-merchant-group',
  templateUrl: './view-merchant-group.component.html',
  styleUrls: ['./view-merchant-group.component.sass']
})
export class ViewMerchantGroupComponent {

  merchantGroup: MerchantInstitution = {
    name: '',
    description: '',
    code: '',
    activateOn: '',
    expiryOn: '',
    totalMerchant: 0,
    totalLocation: 0,
    totalDevice: 0,
    expried: false,
    locked: '0',
    deleted: '0',
  };

  constructor(
    private readonly merchantGroupService: MerchantGroupService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.getMerchantGroup(id);
  }

  getMerchantGroup(id: number): void {
    if (id) {
      this.merchantGroupService.getMerchantInstitutionById(id).subscribe(response => {
        this.merchantGroup = response.data;
      });
    }
  }

  navigateToEditMerchantGroup(id: number): void {
    this.router.navigate(['/layout/merchant-group/edit', id]);
  }

  deleteMerchantGroup(id: number, name: string): void {
    Swal.fire({
      title: 'Are you sure?',
      text: `Do you want to delete ${name} record?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.merchantGroupService.deleteMerchantInstitution(id).subscribe({
          next: () => {
            Swal.fire('Deleted!', `${name} has been deleted.`, 'success');
            this.router.navigate(['/layout/merchant-group']);
          }
        });
      }
    });
  }
}
