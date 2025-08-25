import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Acquirer } from 'src/app/models/Acquirer';
import { AcquirerService } from 'src/app/services/acquirer/acquirer.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-acquirer',
  templateUrl: './acquirer.component.html',
  styleUrls: ['./acquirer.component.sass']
})
export class AcquirerComponent implements OnInit {

  acquirers: Acquirer[] = [];
  filteredAcquirers: Acquirer[] = [];
  filterValue: string = '';
  selectedStatusLabel: string = 'All Status';

  constructor(
    private readonly router: Router,
    private readonly acquirerService: AcquirerService
  ) { }

  ngOnInit(): void {
    this.loadAllAcquirers();
  }

  loadAllAcquirers(): void {
    this.acquirerService.getAllAcquirers().subscribe({
      next: (res) => {
        this.acquirers = res.data;
        this.filteredAcquirers = [...this.acquirers];
      }
    });
  }

  onEnterKey(event: Event): void {
    if (this.filterValue.trim()) {
      this.filteredAcquirers = this.acquirers.filter((acquirer: Acquirer) =>
        acquirer.name.toLowerCase().includes(this.filterValue.toLowerCase())
      );
    } else {
      this.filteredAcquirers = [...this.acquirers];
    }
  }

  onReset(): void {
    this.filterValue = '';
    this.selectedStatusLabel = 'All Status';
    this.filteredAcquirers = [...this.acquirers];
  }

  updateActiveStatus(event: Event, acquirer: Acquirer): void {
    const checkbox = event.target as HTMLInputElement;
    acquirer.active = checkbox.checked ? '1' : '0';
    this.updateAcquirerStatus(acquirer);
  }

  updateOnusValidateStatus(event: Event, acquirer: Acquirer): void {
    const checkbox = event.target as HTMLInputElement;
    acquirer.onusValidate = checkbox.checked ? '1' : '0';
    this.updateAcquirerStatus(acquirer);
  }

  updateRefundOfflineStatus(event: Event, acquirer: Acquirer): void {
    const checkbox = event.target as HTMLInputElement;
    acquirer.refundOffline = checkbox.checked ? '1' : '0';
    this.updateAcquirerStatus(acquirer);
  }

  updateAcquirerStatus(acquirer: Acquirer): void {
    if (acquirer.id != undefined) {
      this.acquirerService.updateAcquirer(acquirer.id, acquirer).subscribe();
    }
  }

  navigateToCreateAcquirer(): void {
    this.router.navigate(['/layout/acquirer/create']);
  }

  navigateToEditAcquirer(id: number): void {
    this.router.navigate(['/layout/acquirer/edit', id]);
  }

  navigateToViewAcquirer(id: number): void {
    this.router.navigate(['/layout/acquirer/view', id]);
  }

  deleteAcquirer(id: number, name: string): void {
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
        this.acquirerService.deleteAcquirer(id).subscribe({
          next: () => {
            this.acquirers = this.acquirers.filter(acq => acq.id !== id);
            this.filteredAcquirers = this.filteredAcquirers.filter(acq => acq.id !== id);
            Swal.fire('Deleted!', `${name} has been deleted.`, 'success');
          },
          error: () => {
            Swal.fire('Error!', `There was a problem deleting the ${name}.`, 'error');
          }
        });
      }
    });
  }

}
