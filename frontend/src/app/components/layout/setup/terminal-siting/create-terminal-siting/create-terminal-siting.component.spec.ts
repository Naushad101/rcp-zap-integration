import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { of, throwError } from 'rxjs';
import Swal from 'sweetalert2';

import { CreateTerminalSitingComponent } from './create-terminal-siting.component';
import { TerminalSitingService } from 'src/app/services/terminal-siting/terminal-siting.service';

describe('CreateTerminalSitingComponent', () => {
  let component: CreateTerminalSitingComponent;
  let fixture: ComponentFixture<CreateTerminalSitingComponent>;
  let mockTerminalSitingService: jasmine.SpyObj<TerminalSitingService>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockLocation: jasmine.SpyObj<Location>;

  beforeEach(async () => {
    const terminalSitingServiceSpy = jasmine.createSpyObj('TerminalSitingService', ['createTerminalSiting']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const locationSpy = jasmine.createSpyObj('Location', ['back']);

    await TestBed.configureTestingModule({
      declarations: [CreateTerminalSitingComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: TerminalSitingService, useValue: terminalSitingServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: Location, useValue: locationSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateTerminalSitingComponent);
    component = fixture.componentInstance;
    mockTerminalSitingService = TestBed.inject(TerminalSitingService) as jasmine.SpyObj<TerminalSitingService>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    mockLocation = TestBed.inject(Location) as jasmine.SpyObj<Location>;

    spyOn(Swal, 'fire').and.returnValue(Promise.resolve({ isConfirmed: true } as any));

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with default values', () => {
    expect(component.terminalSitingForm).toBeDefined();
    expect(component.terminalSitingForm.get('name')?.value).toBe('');
    expect(component.terminalSitingForm.get('active')?.value).toBe('0');
  });

  it('should validate required name field', () => {
    const nameControl = component.terminalSitingForm.get('name');

    expect(nameControl?.valid).toBeFalsy();
    expect(nameControl?.errors?.['required']).toBeTruthy();

    nameControl?.setValue('Test Siting');
    expect(nameControl?.valid).toBeTruthy();
  });

  it('should validate name minimum length', () => {
    const nameControl = component.terminalSitingForm.get('name');

    nameControl?.setValue('A');
    expect(nameControl?.errors?.['minlength']).toBeTruthy();

    nameControl?.setValue('AB');
    expect(nameControl?.errors?.['minlength']).toBeFalsy();
  });

  it('should validate name maximum length', () => {
    const nameControl = component.terminalSitingForm.get('name');
    const longName = 'A'.repeat(101);

    nameControl?.setValue(longName);
    expect(nameControl?.errors?.['maxlength']).toBeTruthy();

    nameControl?.setValue('A'.repeat(100));
    expect(nameControl?.errors?.['maxlength']).toBeFalsy();
  });

  it('should return true for invalid field when field is invalid and touched', () => {
    const nameControl = component.terminalSitingForm.get('name');
    nameControl?.markAsTouched();

    expect(component.isFieldInvalid('name')).toBeTruthy();

    nameControl?.setValue('Valid Siting');
    expect(component.isFieldInvalid('name')).toBeFalsy();
  });

  it('should create terminal siting successfully', async () => {
    mockTerminalSitingService.createTerminalSiting.and.returnValue(of());

    component.terminalSitingForm.patchValue({
      name: 'Test Siting',
      active: '0'
    });

    component.onSubmit();
    await fixture.whenStable();

    expect(mockTerminalSitingService.createTerminalSiting).toHaveBeenCalledWith({
      name: 'Test Siting',
      status: '',
      active: '0'
    });
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-sitings']);
  });

  it('should handle create terminal siting error', async () => {
    const errorResponse = { error: { message: 'Name already exists' } };
    mockTerminalSitingService.createTerminalSiting.and.returnValue(throwError(() => errorResponse));

    component.terminalSitingForm.patchValue({
      name: 'Test Siting',
      active: '0'
    });

    component.onSubmit();
    await fixture.whenStable();

    expect(mockTerminalSitingService.createTerminalSiting).toHaveBeenCalled();
    expect(component.isSubmitting).toBeFalsy();
  });

  it('should not submit if form is invalid', () => {
    spyOn(component as any, 'markFormGroupTouched');

    component.terminalSitingForm.patchValue({
      name: '', // Invalid - required field
      active: '0'
    });

    component.onSubmit();

    expect(mockTerminalSitingService.createTerminalSiting).not.toHaveBeenCalled();
    expect((component as any).markFormGroupTouched).toHaveBeenCalled();
  });

  it('should show confirmation dialog when canceling with unsaved changes', async () => {
    component.terminalSitingForm.patchValue({ name: 'Test' });
    component.terminalSitingForm.markAsDirty();

    component.onCancel();
    await fixture.whenStable();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-sitings']);
  });

  it('should navigate back without confirmation when form is clean', () => {
    component.onCancel();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-sitings']);
  });

  it('should call onCancel when goBack is called', () => {
    spyOn(component, 'onCancel');

    component.goBack();

    expect(component.onCancel).toHaveBeenCalled();
  });

  it('should mark all form fields as touched', () => {
    const nameControl = component.terminalSitingForm.get('name');
    const activeControl = component.terminalSitingForm.get('active');

    expect(nameControl?.touched).toBeFalsy();
    expect(activeControl?.touched).toBeFalsy();

    (component as any).markFormGroupTouched();

    expect(nameControl?.touched).toBeTruthy();
    expect(activeControl?.touched).toBeTruthy();
  });
});
