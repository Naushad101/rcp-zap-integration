import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { TerminalModelService } from 'src/app/services/terminal-model/terminal-model.service';
import { TerminalVendorService } from 'src/app/services/terminal-vendor/terminal-vendor.service';
import { TerminalTypeService } from 'src/app/services/terminal-type/terminal-type.service';
import { TerminalSitingService } from 'src/app/services/terminal-siting/terminal-siting.service';
import { TerminalModel } from 'src/app/models/TerminalModel';
import { TerminalVendor } from 'src/app/models/TerminalVendor';
import { TerminalType } from 'src/app/models/TerminalTypes';
import { TerminalSiting } from 'src/app/models/TerminalSiting';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-terminal-model',
  templateUrl: './create-terminal-model.component.html',
  styleUrls: ['./create-terminal-model.component.sass']
})
export class CreateTerminalModelComponent implements OnInit {
  terminalModelForm!: FormGroup;
  isSubmitting: boolean = false;
  vendors: TerminalVendor[] = [];
  types: TerminalType[] = [];
  sitings: TerminalSiting[] = [];

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly router: Router,
    private readonly location: Location,
    private readonly terminalModelService: TerminalModelService,
    private readonly terminalVendorService: TerminalVendorService,
    private readonly terminalTypeService: TerminalTypeService,
    private readonly terminalSitingService: TerminalSitingService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
    this.loadDropdownData();
  }

  initializeForm(): void {
    this.terminalModelForm = this.formBuilder.group({
      name: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(100)
      ]],
      terminalVendorId: ['', Validators.required],
      terminalTypeId: ['', Validators.required],
      terminalSitingId: ['']
    });
  }

  loadDropdownData(): void {
    // Load vendors with debugging
    this.terminalVendorService.getAllTerminalVendors().subscribe({
      next: (response) => {
        console.log('Vendor API Response:', response); // DEBUG
        this.vendors = response as unknown as TerminalVendor[];
        console.log('Parsed Vendors:', this.vendors); // DEBUG
      },
      error: (error) => {
        console.error('Error loading vendors:', error);
      }
    });

    // Load types with debugging
    this.terminalTypeService.getAllTerminalTypes().subscribe({
      next: (response) => {
        console.log('Type API Response:', response); // DEBUG
        this.types = response as unknown as TerminalType[];
        console.log('Parsed Types:', this.types); // DEBUG
      },
      error: (error) => {
        console.error('Error loading types:', error);
      }
    });

    // Load sitings with debugging
    this.terminalSitingService.getAllTerminalSitings().subscribe({
      next: (response) => {
        console.log('Siting API Response:', response); // DEBUG
        this.sitings = response as unknown as TerminalSiting[];
        console.log('Parsed Sitings:', this.sitings); // DEBUG
      },
      error: (error) => {
        console.error('Error loading sitings:', error);
      }
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.terminalModelForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  onSubmit(): void {
    if (this.terminalModelForm.valid) {
      this.isSubmitting = true;
      
      const formValue = this.terminalModelForm.value;
      console.log('Form Values:', formValue); // DEBUG
      
      // Get the selected names instead of IDs based on your model
      const selectedVendor = this.vendors.find(v => v.id == formValue.terminalVendorId);
      const selectedType = this.types.find(t => t.id == formValue.terminalTypeId);
      const selectedSiting = this.sitings.find(s => s.id == formValue.terminalSitingId);
      
      console.log('Selected Vendor:', selectedVendor); // DEBUG
      console.log('Selected Type:', selectedType); // DEBUG
      console.log('Selected Siting:', selectedSiting); // DEBUG
      
      const terminalModel: TerminalModel = {
        modelname: formValue.name.trim(),
        vendor: selectedVendor,
        type: selectedType, // Handle different property names
        siting: selectedSiting
      };

      console.log('Terminal Model to be sent:', terminalModel); // DEBUG

      this.terminalModelService.createTerminalModel(terminalModel).subscribe({
        next: (response) => {
          this.isSubmitting = false;
          console.log('Create Response:', response); // DEBUG
          Swal.fire({
            title: 'Success!',
            text: 'Terminal Model has been created successfully.',
            icon: 'success',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/layout/terminal-models']);
          });
        },
        error: (error) => {
          this.isSubmitting = false;
          console.error('Error creating terminal model:', error);
          
          let errorMessage = 'There was a problem creating the terminal model.';
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
    if (this.terminalModelForm.dirty) {
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
    this.router.navigate(['/layout/terminal-models']);
  }

  private markFormGroupTouched(): void {
    Object.keys(this.terminalModelForm.controls).forEach(key => {
      const control = this.terminalModelForm.get(key);
      if (control) {
        control.markAsTouched();
      }
    });
  }
}