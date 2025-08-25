import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { TerminalSitingService } from 'src/app/services/terminal-siting/terminal-siting.service';
import { TerminalSiting } from 'src/app/models/TerminalSiting';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-terminal-siting',
  templateUrl: './create-terminal-siting.component.html',
  styleUrls: ['./create-terminal-siting.component.sass']
})
export class CreateTerminalSitingComponent implements OnInit {
  terminalSitingForm!: FormGroup;
  isSubmitting: boolean = false;

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly router: Router,
    private readonly location: Location,
    private readonly terminalSitingService: TerminalSitingService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.terminalSitingForm = this.formBuilder.group({
      name: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(100)
      ]],
      active: 1 // '0' = DISABLED, '1' = ENABLED
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.terminalSitingForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  onSubmit(): void {
    if (this.terminalSitingForm.valid) {
      this.isSubmitting = true;
      
      const formValue = this.terminalSitingForm.value;
      const terminalSiting: TerminalSiting = {
        name: formValue.name.trim(),
        status: formValue.active ? 1 : 0, // Convert to number for API
      };

      this.terminalSitingService.createTerminalSiting(terminalSiting).subscribe({
        next: (response) => {
          this.isSubmitting = false;
          Swal.fire({
            title: 'Success!',
            text: 'Terminal Siting has been created successfully.',
            icon: 'success',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/layout/terminal-sitings']);
          });
        },
        error: (error) => {
          this.isSubmitting = false;
          console.error('Error creating terminal siting:', error);
          
          let errorMessage = 'There was a problem creating the terminal siting.';
          if (error.error && error.error.message) {
            errorMessage = error.error.message;
          }
          
          Swal.fire({
            title: 'Error!',
            text: errorMessage,
            icon: 'error',
            confirmButtonText: 'OK'
          });
        }
      });
    } else {
      this.markFormGroupTouched();
    }
  }

  onCancel(): void {
    if (this.terminalSitingForm.dirty) {
      Swal.fire({
        title: 'Are you sure?',
        text: 'You have unsaved changes. Do you want to discard them?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, discard changes',
        cancelButtonText: 'Cancel'
      }).then((result) => {
        if (result.isConfirmed) {
          this.navigateBack();
        }
      });
    } else {
      this.navigateBack();
    }
  }

  goBack(): void {
    this.onCancel();
  }

  private navigateBack(): void {
    this.router.navigate(['/layout/terminal-sitings']);
  }

  private markFormGroupTouched(): void {
    Object.keys(this.terminalSitingForm.controls).forEach(key => {
      const control = this.terminalSitingForm.get(key);
      if (control) {
        control.markAsTouched();
      }
    });
  }
}