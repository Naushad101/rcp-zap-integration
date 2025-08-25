import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MerchantInstitution } from 'src/app/models/MerchantInstitution';
import { MerchantGroupService } from 'src/app/services/merchant-group/merchant-group.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-merchant-group',
  templateUrl: './merchant-group.component.html',
  styleUrls: ['./merchant-group.component.sass']
})
export class MerchantGroupComponent implements OnInit {

  merchantGroups: MerchantInstitution[] = [];
  filteredMerchantGroups: MerchantInstitution[] = [];
  filterValue: string = '';
  selectedStatusLabel: string = 'All Status';

  constructor(
    private readonly merchantGroupService: MerchantGroupService,
    private readonly router: Router
  ) { }

  ngOnInit(): void {
    this.loadAllMerchantGroups();
  }

  loadAllMerchantGroups(): void {
    this.merchantGroupService.getAllMerchantInstitutions().subscribe({
      next: (res) => {
        if (Array.isArray(res?.data)) {
          this.merchantGroups = res.data;
        } else {
          this.merchantGroups = Array.isArray(res) ? res : [];
        }
        this.filteredMerchantGroups = [...this.merchantGroups];
      },
      error: (err) => {
        this.merchantGroups = [];
        this.filteredMerchantGroups = [];
      }
    });
  }

  onEnterKey(event: Event): void {
    if (this.filterValue.trim()) {
      this.filteredMerchantGroups = this.merchantGroups.filter(merchantGroup =>
        merchantGroup.name.toLowerCase().includes(this.filterValue.toLowerCase())
      );
    } else {
      this.filteredMerchantGroups = [...this.merchantGroups];
    }
  }

  onReset(): void {
    this.filterValue = '';
    this.selectedStatusLabel = 'All Status';
    this.filteredMerchantGroups = [...this.merchantGroups];
  }

  filterByStatus(event: Event, status: string): void {
    event.preventDefault();

    if (status === 'all') {
      this.selectedStatusLabel = 'All Status';
      this.filteredMerchantGroups = [...this.merchantGroups];
    } else {
      this.selectedStatusLabel = status === '1' ? 'Active' : 'Inactive';
      this.filteredMerchantGroups = this.merchantGroups.filter(group => group.locked === status);
    }
  }

  updateStatus(event: Event, merchantGroup: MerchantInstitution): void {
    const checkbox = event.target as HTMLInputElement;
    merchantGroup.locked = checkbox.checked ? '1' : '0';
    this.updateMerchantGroup(merchantGroup);
  }

  updateMerchantGroup(merchantGroup: MerchantInstitution): void {
    if (merchantGroup.id !== undefined) {
      this.merchantGroupService.updateMerchantInstitution(merchantGroup.id, merchantGroup).subscribe();
    }
  }

  deleteMerchantGroup(id: number, name: string): void {
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
        this.merchantGroupService.deleteMerchantInstitution(id).subscribe({
          next: () => {
            this.merchantGroups = this.merchantGroups.filter(mg => mg.id !== id);
            this.filteredMerchantGroups = this.filteredMerchantGroups.filter(mg => mg.id !== id);
            Swal.fire('Deleted!', `${name} has been deleted.`, 'success');
          },
          error: () => {
            Swal.fire('Error!', `There was a problem deleting ${name}.`, 'error');
          }
        });
      }
    });
  }

  navigateToCreateMerchantGroup(): void {
    this.router.navigate(['/layout/merchant-group/create']);
  }

  navigateToEditMerchantGroup(id: number): void {
    this.router.navigate(['/layout/merchant-group/edit', id]);
  }

}
