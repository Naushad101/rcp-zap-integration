import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { of, throwError } from 'rxjs';
import Swal, { SweetAlertOptions } from 'sweetalert2'; // Import SweetAlertOptions

import { CreateTerminalTypeComponent } from './create-terminal-type.component';
import { TerminalTypeService } from 'src/app/services/terminal-type/terminal-type.service';

describe('CreateTerminalTypeComponent', () => {
  let component: CreateTerminalTypeComponent;
  let fixture: ComponentFixture<CreateTerminalTypeComponent>;
  let mockTerminalTypeService: jasmine.SpyObj<TerminalTypeService>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockLocation: jasmine.SpyObj<Location>;

  beforeEach(async () => {
    const terminalTypeServiceSpy = jasmine.createSpyObj('TerminalTypeService', ['createTerminalType']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const locationSpy = jasmine.createSpyObj('Location', ['back']);

    await TestBed.configureTestingModule({
      declarations: [CreateTerminalTypeComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: TerminalTypeService, useValue: terminalTypeServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: Location, useValue: locationSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateTerminalTypeComponent);
    component = fixture.componentInstance;
    mockTerminalTypeService = TestBed.inject(TerminalTypeService) as jasmine.SpyObj<TerminalTypeService>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    mockLocation = TestBed.inject(Location) as jasmine.SpyObj<Location>;

    // Mock Swal.fire
    spyOn(Swal, 'fire').and.returnValue(Promise.resolve({ isConfirmed: true } as any));

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with default values', () => {
    expect(component.terminalTypeForm).toBeDefined();
    expect(component.terminalTypeForm.get('name')?.value).toBe('');
    expect(component.terminalTypeForm.get('active')?.value).toBe(false);
  });

  it('should validate required name field', () => {
    const nameControl = component.terminalTypeForm.get('name');

    expect(nameControl?.valid).toBeFalsy();
    expect(nameControl?.errors?.['required']).toBeTruthy();

    nameControl?.setValue('Test Terminal Type');
    expect(nameControl?.valid).toBeTruthy();
  });

  it('should validate name minimum length', () => {
    const nameControl = component.terminalTypeForm.get('name');

    nameControl?.setValue('A');
    expect(nameControl?.errors?.['minlength']).toBeTruthy();

    nameControl?.setValue('AB');
    expect(nameControl?.errors?.['minlength']).toBeFalsy();
  });

  it('should validate name maximum length', () => {
    const nameControl = component.terminalTypeForm.get('name');
    const longName = 'A'.repeat(101);

    nameControl?.setValue(longName);
    expect(nameControl?.errors?.['maxlength']).toBeTruthy();

    nameControl?.setValue('A'.repeat(100));
    expect(nameControl?.errors?.['maxlength']).toBeFalsy();
  });

  it('should return true for invalid field when field is invalid and touched', () => {
    const nameControl = component.terminalTypeForm.get('name');
    nameControl?.markAsTouched();

    expect(component.isFieldInvalid('name')).toBeTruthy();

    nameControl?.setValue('Valid Name');
    expect(component.isFieldInvalid('name')).toBeFalsy();
  });

  it('should create terminal type successfully', async () => {
    mockTerminalTypeService.createTerminalType.and.returnValue(of());
    
    const successOptions: SweetAlertOptions = {
      title: 'Success!',
      text: 'Terminal Type has been created successfully.',
      icon: 'success',
      confirmButtonText: 'OK'
    };

    component.terminalTypeForm.patchValue({
      name: 'Test Terminal Type',
      active: true
    });

    component.onSubmit();
    await fixture.whenStable();

    expect(mockTerminalTypeService.createTerminalType).toHaveBeenCalledWith({
      name: 'Test Terminal Type',
      active: '1'
    });
    expect(Swal.fire).toHaveBeenCalledWith();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-type']);
  });

  it('should handle create terminal type error', async () => {
    const errorResponse = { error: { message: 'Name already exists' } };
    mockTerminalTypeService.createTerminalType.and.returnValue(throwError(() => errorResponse));

    const errorOptions: SweetAlertOptions = {
      title: 'Error!',
      text: 'Name already exists',
      icon: 'error',
      confirmButtonText: 'OK'
    };

    component.terminalTypeForm.patchValue({
      name: 'Test Terminal Type Ascertainable',
      active: false
    });

    component.onSubmit();
    await fixture.whenStable();

    expect(Swal.fire).toHaveBeenCalledWith();
    expect(component.isSubmitting).toBeFalsy();
  });

  it('should not submit if form is invalid', () => {
    spyOn(component as any, 'markFormGroupTouched');

    component.terminalTypeForm.patchValue({
      name: '', // Invalid - required field
      active: false
    });

    component.onSubmit();

    expect(mockTerminalTypeService.createTerminalType).not.toHaveBeenCalled();
    expect((component as any).markFormGroupTouched).toHaveBeenCalled();
  });

  it('should show confirmation dialog when canceling with unsaved changes', async () => {
    const cancelOptions: SweetAlertOptions = {
      title: 'Are you sure?',
      text: 'You have unsaved changes. Do you want to discard them?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, discard changes',
      cancelButtonText: 'Cancel'
    };

    component.terminalTypeForm.patchValue({ name: 'Test' });
    component.terminalTypeForm.markAsDirty();

    component.onCancel();
    await fixture.whenStable();

    expect(Swal.fire).toHaveBeenCalledWith();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-type']);
  });

  it('should navigate back without confirmation when form is clean', () => {
    component.onCancel();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-type']);
  });

  it('should call onCancel when goBack is called', () => {
    spyOn(component, 'onCancel');

    component.goBack();

    expect(component.onCancel).toHaveBeenCalled();
  });

  it('should mark all form fields as touched', () => {
    const nameControl = component.terminalTypeForm.get('name');
    const activeControl = component.terminalTypeForm.get('active');

    expect(nameControl?.touched).toBeFalsy();
    expect(activeControl?.touched).toBeFalsy();

    (component as any).markFormGroupTouched();

    expect(nameControl?.touched).toBeTruthy();
    expect(activeControl?.touched).toBeTruthy();
  });
});