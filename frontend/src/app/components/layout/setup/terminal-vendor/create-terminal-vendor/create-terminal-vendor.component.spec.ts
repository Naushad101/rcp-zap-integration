import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { of, throwError } from 'rxjs';
import Swal from 'sweetalert2';

import { CreateTerminalVendorComponent } from './create-terminal-vendor.component';
import { TerminalVendorService } from 'src/app/services/terminal-vendor/terminal-vendor.service';

describe('CreateTerminalVendorComponent', () => {
  let component: CreateTerminalVendorComponent;
  let fixture: ComponentFixture<CreateTerminalVendorComponent>;
  let mockTerminalVendorService: jasmine.SpyObj<TerminalVendorService>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockLocation: jasmine.SpyObj<Location>;

  beforeEach(async () => {
    const terminalVendorServiceSpy = jasmine.createSpyObj('TerminalVendorService', ['createTerminalVendor']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const locationSpy = jasmine.createSpyObj('Location', ['back']);

    await TestBed.configureTestingModule({
      declarations: [CreateTerminalVendorComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: TerminalVendorService, useValue: terminalVendorServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: Location, useValue: locationSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateTerminalVendorComponent);
    component = fixture.componentInstance;
    mockTerminalVendorService = TestBed.inject(TerminalVendorService) as jasmine.SpyObj<TerminalVendorService>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    mockLocation = TestBed.inject(Location) as jasmine.SpyObj<Location>;

    spyOn(Swal, 'fire').and.returnValue(Promise.resolve({ isConfirmed: true } as any));

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with default values', () => {
    expect(component.terminalVendorForm).toBeDefined();
    expect(component.terminalVendorForm.get('name')?.value).toBe('');
    expect(component.terminalVendorForm.get('active')?.value).toBe('0');
  });

  it('should validate required name field', () => {
    const nameControl = component.terminalVendorForm.get('name');

    expect(nameControl?.valid).toBeFalsy();
    expect(nameControl?.errors?.['required']).toBeTruthy();

    nameControl?.setValue('Test Vendor');
    expect(nameControl?.valid).toBeTruthy();
  });

  it('should validate name minimum length', () => {
    const nameControl = component.terminalVendorForm.get('name');

    nameControl?.setValue('A');
    expect(nameControl?.errors?.['minlength']).toBeTruthy();

    nameControl?.setValue('AB');
    expect(nameControl?.errors?.['minlength']).toBeFalsy();
  });

  it('should validate name maximum length', () => {
    const nameControl = component.terminalVendorForm.get('name');
    const longName = 'A'.repeat(101);

    nameControl?.setValue(longName);
    expect(nameControl?.errors?.['maxlength']).toBeTruthy();

    nameControl?.setValue('A'.repeat(100));
    expect(nameControl?.errors?.['maxlength']).toBeFalsy();
  });

  it('should return true for invalid field when field is invalid and touched', () => {
    const nameControl = component.terminalVendorForm.get('name');
    nameControl?.markAsTouched();

    expect(component.isFieldInvalid('name')).toBeTruthy();

    nameControl?.setValue('Valid Vendor');
    expect(component.isFieldInvalid('name')).toBeFalsy();
  });

  it('should create terminal vendor successfully', async () => {
    mockTerminalVendorService.createTerminalVendor.and.returnValue(of());

    component.terminalVendorForm.patchValue({
      name: 'Test Vendor',
      active: '0'
    });

    component.onSubmit();
    await fixture.whenStable();

    expect(mockTerminalVendorService.createTerminalVendor).toHaveBeenCalledWith({
      name: 'Test Vendor',
      status:'',
      active: '0'
    });
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-vendor']);
  });

  it('should handle create terminal vendor error', async () => {
    const errorResponse = { error: { message: 'Name already exists' } };
    mockTerminalVendorService.createTerminalVendor.and.returnValue(throwError(() => errorResponse));

    component.terminalVendorForm.patchValue({
      name: 'Test Vendor',
      active: '0'
    });

    component.onSubmit();
    await fixture.whenStable();

    expect(mockTerminalVendorService.createTerminalVendor).toHaveBeenCalled();
    expect(component.isSubmitting).toBeFalsy();
  });

  it('should not submit if form is invalid', () => {
    spyOn(component as any, 'markFormGroupTouched');

    component.terminalVendorForm.patchValue({
      name: '', // Invalid - required field
      active: '0'
    });

    component.onSubmit();

    expect(mockTerminalVendorService.createTerminalVendor).not.toHaveBeenCalled();
    expect((component as any).markFormGroupTouched).toHaveBeenCalled();
  });

  it('should show confirmation dialog when canceling with unsaved changes', async () => {
    component.terminalVendorForm.patchValue({ name: 'Test' });
    component.terminalVendorForm.markAsDirty();

    component.onCancel();
    await fixture.whenStable();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-vendor']);
  });

  it('should navigate back without confirmation when form is clean', () => {
    component.onCancel();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-vendor']);
  });

  it('should call onCancel when goBack is called', () => {
    spyOn(component, 'onCancel');

    component.goBack();

    expect(component.onCancel).toHaveBeenCalled();
  });

  it('should mark all form fields as touched', () => {
    const nameControl = component.terminalVendorForm.get('name');
    const activeControl = component.terminalVendorForm.get('active');

    expect(nameControl?.touched).toBeFalsy();
    expect(activeControl?.touched).toBeFalsy();

    (component as any).markFormGroupTouched();

    expect(nameControl?.touched).toBeTruthy();
    expect(activeControl?.touched).toBeTruthy();
  });
}
)