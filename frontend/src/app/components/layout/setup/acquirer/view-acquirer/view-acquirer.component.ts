import { Component } from '@angular/core';
import { Acquirer } from 'src/app/models/Acquirer';
import { ActivatedRoute, Router } from '@angular/router';
import { AcquirerService } from 'src/app/services/acquirer/acquirer.service';
import { CountryService } from 'src/app/services/country/country.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-acquirer',
  templateUrl: './view-acquirer.component.html',
  styleUrls: ['./view-acquirer.component.sass']
})
export class ViewAcquirerComponent {
  acquirer: Acquirer = {
    id: 0,
    name: '',
    code: '',
    onusValidate: '0',
    refundOffline: '0',
    active: '1',
    adviceMatch: true,
    countryId: 0,
    description: '',
    deleted: '0',
    totalMerchantGroup: 0,
    posSms: '',
    posDms: '',
    txtnypeSms: '',
    txtnypeDms: '',
    accounttypeSms: '',
    accounttypeDms: ''
  };

  constructor(
      private readonly acquirerService: AcquirerService,
      private readonly countryService: CountryService,
      private readonly router: Router,
      private readonly activatedRoute: ActivatedRoute
    ) { }

  ngOnInit(): void {
    const id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.getAcquirer(id);
  }
  
  getAcquirer(id:number): void {
    if (id) {
      this.acquirerService.getAcquirerById(id).subscribe(response => {
        this.acquirer = response.data;
      });
    }
  }

  navigateToEditAcquirer(id: number): void {
    this.router.navigate(['/layout/acquirer/edit', id]);
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
              Swal.fire('Deleted!', `${name} has been deleted.`, 'success');
              this.router.navigate(['/layout/acquirer']);
            }
          });
        }
      });
    }

}
