import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { TerminalType } from 'src/app/models/TerminalTypes';
import { TerminalTypeService } from 'src/app/services/terminal-type/terminal-type.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-terminal-type',
  templateUrl: './create-terminal-type.component.html',
  styleUrls: ['./create-terminal-type.component.sass']
})
export class CreateTerminalTypeComponent implements OnInit {

  terminalTypeForm!: FormGroup;
  isSubmitting: boolean = false;

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly router: Router,
    private readonly location: Location,
    private readonly terminalTypeService: TerminalTypeService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.terminalTypeForm = this.formBuilder.group({
      name: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(100)
      ]],
      active: [false] // false = disabled, true = enabled
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.terminalTypeForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  onSubmit(): void {
    if (this.terminalTypeForm.valid) {
      this.isSubmitting = true;
      
      const formValue = this.terminalTypeForm.value;
      const terminalType: TerminalType = {
        type: formValue.name.trim(),
        status: formValue.active ? 1 : 0
      };

      this.terminalTypeService.createTerminalType(terminalType).subscribe({
        next: (response) => {
          this.isSubmitting = false;
          Swal.fire({
            title: 'Success!',
            text: 'Terminal Type has been created successfully.',
            icon: 'success',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/layout/terminal-type']);
          });
        },
        error: (error) => {
          this.isSubmitting = false;
          console.error('Error creating terminal type:', error);
          
          let errorMessage = 'There was a problem creating the terminal type.';
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
    if (this.terminalTypeForm.dirty) {
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
    this.router.navigate(['/layout/terminal-type']);
  }

  private markFormGroupTouched(): void {
    Object.keys(this.terminalTypeForm.controls).forEach(key => {
      const control = this.terminalTypeForm.get(key);
      if (control) {
        control.markAsTouched();
      }
    });
  }

}