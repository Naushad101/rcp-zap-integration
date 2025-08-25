import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { of, throwError } from 'rxjs';
import Swal from 'sweetalert2';

import { CreateTerminalModelComponent } from './create-terminal-model.component';
import { TerminalModelService } from 'src/app/services/terminal-model/terminal-model.service';
import { TerminalVendorService } from 'src/app/services/terminal-vendor/terminal-vendor.service';
import { TerminalTypeService } from 'src/app/services/terminal-type/terminal-type.service';
import { TerminalSitingService } from 'src/app/services/terminal-siting/terminal-siting.service';

describe('CreateTerminalModelComponent', () => {
  let component: CreateTerminalModelComponent;
  let fixture: ComponentFixture<CreateTerminalModelComponent>;
  let mockTerminalModelService: jasmine.SpyObj<TerminalModelService>;
  let mockTerminalVendorService: jasmine.SpyObj<TerminalVendorService>;
  let mockTerminalTypeService: jasmine.SpyObj<TerminalTypeService>;
  let mockTerminalSitingService: jasmine.SpyObj<TerminalSitingService>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockLocation: jasmine.SpyObj<Location>;

  beforeEach(async () => {
    const terminalModelServiceSpy = jasmine.createSpyObj('TerminalModelService', ['createTerminalModel']);
    const terminalVendorServiceSpy = jasmine.createSpyObj('TerminalVendorService', ['getAllTerminalVendorsIdAndName']);
    const terminalTypeServiceSpy = jasmine.createSpyObj('TerminalTypeService', ['getAllTerminalTypesIdAndName']);
    const terminalSitingServiceSpy = jasmine.createSpyObj('TerminalSitingService', ['getAllTerminalSitingsIdAndName']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const locationSpy = jasmine.createSpyObj('Location', ['back']);

    await TestBed.configureTestingModule({
      declarations: [CreateTerminalModelComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: TerminalModelService, useValue: terminalModelServiceSpy },
        { provide: TerminalVendorService, useValue: terminalVendorServiceSpy },
        { provide: TerminalTypeService, useValue: terminalTypeServiceSpy },
        { provide: TerminalSitingService, useValue: terminalSitingServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: Location, useValue: locationSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateTerminalModelComponent);
    component = fixture.componentInstance;
    mockTerminalModelService = TestBed.inject(TerminalModelService) as jasmine.SpyObj<TerminalModelService>;
    mockTerminalVendorService = TestBed.inject(TerminalVendorService) as jasmine.SpyObj<TerminalVendorService>;
    mockTerminalTypeService = TestBed.inject(TerminalTypeService) as jasmine.SpyObj<TerminalTypeService>;
    mockTerminalSitingService = TestBed.inject(TerminalSitingService) as jasmine.SpyObj<TerminalSitingService>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    mockLocation = TestBed.inject(Location) as jasmine.SpyObj<Location>;

    // Setup mock returns
    mockTerminalVendorService.getAllTerminalVendorsIdAndName.and.returnValue(of());
    mockTerminalTypeService.getAllTerminalTypesIdAndName.and.returnValue(of());
    mockTerminalSitingService.getAllTerminalSitingsIdAndName.and.returnValue(of());

    spyOn(Swal, 'fire').and.returnValue(Promise.resolve({ isConfirmed: true } as any));

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with default values', () => {
    expect(component.terminalModelForm).toBeDefined();
    expect(component.terminalModelForm.get('name')?.value).toBe('');
    expect(component.terminalModelForm.get('terminalVendorId')?.value).toBe('');
    expect(component.terminalModelForm.get('terminalTypeId')?.value).toBe('');
    expect(component.terminalModelForm.get('terminalSitingId')?.value).toBe('');
  });

  it('should validate required name field', () => {
    const nameControl = component.terminalModelForm.get('name');

    expect(nameControl?.valid).toBeFalsy();
    expect(nameControl?.errors?.['required']).toBeTruthy();

    nameControl?.setValue('Test Model');
    expect(nameControl?.valid).toBeTruthy();
  });

  it('should validate required terminal vendor field', () => {
    const vendorControl = component.terminalModelForm.get('terminalVendorId');

    expect(vendorControl?.valid).toBeFalsy();
    expect(vendorControl?.errors?.['required']).toBeTruthy();

    vendorControl?.setValue('1');
    expect(vendorControl?.valid).toBeTruthy();
  });

  it('should validate required terminal type field', () => {
    const typeControl = component.terminalModelForm.get('terminalTypeId');

    expect(typeControl?.valid).toBeFalsy();
    expect(typeControl?.errors?.['required']).toBeTruthy();

    typeControl?.setValue('1');
    expect(typeControl?.valid).toBeTruthy();
  });

  it('should create terminal model successfully', async () => {
    mockTerminalModelService.createTerminalModel.and.returnValue(of());

    component.terminalModelForm.patchValue({
      name: 'Test Model',
      terminalVendorId: '1',
      terminalTypeId: '1',
      terminalSitingId: '1'
    });

    component.onSubmit();
    await fixture.whenStable();

    expect(mockTerminalModelService.createTerminalModel).toHaveBeenCalledWith({
      name: 'Test Model',
      terminalVendorId: '1',
      terminalTypeId: '1',
      terminalSitingId: '1'
    });
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-models']);
  });

  it('should handle create terminal model error', async () => {
    const errorResponse = { error: { message: 'Model already exists' } };
    mockTerminalModelService.createTerminalModel.and.returnValue(throwError(() => errorResponse));

    component.terminalModelForm.patchValue({
      name: 'Test Model',
      terminalVendorId: '1',
      terminalTypeId: '1',
      terminalSitingId: ''
    });

    component.onSubmit();
    await fixture.whenStable();

    expect(mockTerminalModelService.createTerminalModel).toHaveBeenCalled();
    expect(component.isSubmitting).toBeFalsy();
  });

  it('should not submit if form is invalid', () => {
    spyOn(component as any, 'markFormGroupTouched');

    component.terminalModelForm.patchValue({
      name: '', // Invalid - required field
      terminalVendorId: '', // Invalid - required field
      terminalTypeId: '', // Invalid - required field
      terminalSitingId: ''
    });

    component.onSubmit();

    expect(mockTerminalModelService.createTerminalModel).not.toHaveBeenCalled();
    expect((component as any).markFormGroupTouched).toHaveBeenCalled();
  });

  it('should show confirmation dialog when canceling with unsaved changes', async () => {
    component.terminalModelForm.patchValue({ name: 'Test' });
    component.terminalModelForm.markAsDirty();

    component.onCancel();
    await fixture.whenStable();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-models']);
  });

  it('should navigate back without confirmation when form is clean', () => {
    component.onCancel();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-models']);
  });

  it('should call onCancel when goBack is called', () => {
    spyOn(component, 'onCancel');

    component.goBack();

    expect(component.onCancel).toHaveBeenCalled();
  });

  it('should mark all form fields as touched', () => {
    const nameControl = component.terminalModelForm.get('name');
    const vendorControl = component.terminalModelForm.get('terminalVendorId');
    const typeControl = component.terminalModelForm.get('terminalTypeId');
    const sitingControl = component.terminalModelForm.get('terminalSitingId');

    expect(nameControl?.touched).toBeFalsy();
    expect(vendorControl?.touched).toBeFalsy();
    expect(typeControl?.touched).toBeFalsy();
    expect(sitingControl?.touched).toBeFalsy();

    (component as any).markFormGroupTouched();

    expect(nameControl?.touched).toBeTruthy();
    expect(vendorControl?.touched).toBeTruthy();
    expect(typeControl?.touched).toBeTruthy();
    expect(sitingControl?.touched).toBeTruthy();
  });
});
