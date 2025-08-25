import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { TerminalVendorService } from 'src/app/services/terminal-vendor/terminal-vendor.service';
import { TerminalVendor } from 'src/app/models/TerminalVendor';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-terminal-vendor',
  templateUrl: './create-terminal-vendor.component.html',
  styleUrls: ['./create-terminal-vendor.component.sass']
})
export class CreateTerminalVendorComponent implements OnInit {
  terminalVendorForm!: FormGroup;
  isSubmitting: boolean = false;

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly router: Router,
    private readonly location: Location,
    private readonly terminalVendorService: TerminalVendorService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.terminalVendorForm = this.formBuilder.group({
      name: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(100)
      ]],
      active: [] // '0' = DISABLED, '1' = ENABLED
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.terminalVendorForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  onSubmit(): void {
    if (this.terminalVendorForm.valid) {
      this.isSubmitting = true;
      
      const formValue = this.terminalVendorForm.value;
      const terminalVendor: TerminalVendor = {
        name: formValue.name.trim(),
        status: formValue.active ? 1 : 0, // Convert to number for API,
        deleted: 0 // Default to not deleted
      };

      this.terminalVendorService.createTerminalVendor(terminalVendor).subscribe({
        next: (response) => {
          this.isSubmitting = false;
          Swal.fire({
            title: 'Success!',
            text: 'Terminal Vendor has been created successfully.',
            icon: 'success',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/layout/terminal-vendor']);
          });
        },
        error: (error) => {
          this.isSubmitting = false;
          console.error('Error creating terminal vendor:', error);
          
          let errorMessage = 'There was a problem creating the terminal vendor.';
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
    if (this.terminalVendorForm.dirty) {
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
    this.router.navigate(['/layout/terminal-vendor']);
  }

  private markFormGroupTouched(): void {
    Object.keys(this.terminalVendorForm.controls).forEach(key => {
      const control = this.terminalVendorForm.get(key);
      if (control) {
        control.markAsTouched();
      }
    });
  }
}