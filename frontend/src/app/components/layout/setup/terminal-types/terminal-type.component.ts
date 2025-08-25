import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TerminalType } from 'src/app/models/TerminalTypes';
import { TerminalTypeService } from 'src/app/services/terminal-type/terminal-type.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-terminal-type',
  templateUrl: './terminal-type.component.html',
  styleUrls: ['./terminal-type.component.sass']
})
export class TerminalTypeComponent implements OnInit {

  terminalTypes: TerminalType[] = [];
  filteredTerminalTypes: TerminalType[] = [];
  filterValue: string = '';

  constructor(
    private readonly router: Router,
    private readonly terminalTypeService: TerminalTypeService
  ) { }

  ngOnInit(): void {
    this.loadAllTerminalTypes();
  }

  loadAllTerminalTypes(): void {
    this.terminalTypeService.getAllTerminalTypes().subscribe({
      next: (res) => {
        this.terminalTypes = res.data;
        this.filteredTerminalTypes = [...this.terminalTypes];
      },
      error: (err) => {
        console.error('Error loading terminal types:', err);
        Swal.fire('Error!', 'There was a problem loading terminal types.', 'error');
      }
    });
  }

  onEnterKey(event: Event): void {
    if (this.filterValue.trim()) {
      this.filteredTerminalTypes = this.terminalTypes.filter((terminalType: TerminalType) =>
        terminalType.type.toLowerCase().includes(this.filterValue.toLowerCase())
      );
    } else {
      this.filteredTerminalTypes = [...this.terminalTypes];
    }
  }

  onReset(): void {
    this.filterValue = '';
    this.filteredTerminalTypes = [...this.terminalTypes];
  }

  updateActiveStatus(event: Event, terminalType: TerminalType): void {
    const checkbox = event.target as HTMLInputElement;
    terminalType.status = checkbox.checked ? 1 : 0;
    this.updateTerminalTypeStatus(terminalType);
  }

  updateTerminalTypeStatus(terminalType: TerminalType): void {
    if (terminalType.id != undefined) {
      this.terminalTypeService.updateTerminalType(terminalType.id, terminalType).subscribe({
        next: () => {
          // Success feedback could be added here if needed
        },
        error: (err) => {
          console.error('Error updating terminal type:', err);
          Swal.fire('Error!', 'There was a problem updating the terminal type status.', 'error');
          // Revert the change
          terminalType.status = terminalType.status === 1 ? 0 : 0;
        }
      });
    }
  }

  navigateToCreateTerminalType(): void {
    this.router.navigate(['/layout/terminal-type/create']);
  }

  navigateToEditTerminalType(id: number): void {
    this.router.navigate(['/layout/terminal-type/edit', id]);
  }

  navigateToViewTerminalType(id: number): void {
    this.router.navigate(['/layout/terminal-type/view', id]);
  }

  deleteTerminalType(id: number, name: string): void {
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
        this.terminalTypeService.deleteTerminalType(id).subscribe({
          next: () => {
            this.terminalTypes = this.terminalTypes.filter(tt => tt.id !== id);
            this.filteredTerminalTypes = this.filteredTerminalTypes.filter(tt => tt.id !== id);
            Swal.fire('Deleted!', `${name} has been deleted.`, 'success');
          },
          error: (err) => {
            console.error('Error deleting terminal type:', err);
            Swal.fire('Error!', `There was a problem deleting the ${name}.`, 'error');
          }
        });
      }
    });
  }

}