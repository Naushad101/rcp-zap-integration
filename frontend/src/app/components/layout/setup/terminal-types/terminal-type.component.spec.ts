import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import Swal from 'sweetalert2';

import { TerminalTypeComponent } from './terminal-type.component';
import { TerminalTypeService } from 'src/app/services/terminal-type/terminal-type.service';
import { TerminalType } from 'src/app/models/TerminalTypes';

describe('TerminalTypeComponent', () => {
  let component: TerminalTypeComponent;
  let fixture: ComponentFixture<TerminalTypeComponent>;
  let mockTerminalTypeService: jasmine.SpyObj<TerminalTypeService>;
  let mockRouter: jasmine.SpyObj<Router>;

  const mockTerminalTypes: TerminalType[] = [
    { id: 1, name: 'POS Terminal', active: '1' },
    { id: 2, name: 'ATM Terminal', active: '0' },
    { id: 3, name: 'Mobile Terminal', active: '1' }
  ];

  beforeEach(async () => {
    const terminalTypeServiceSpy = jasmine.createSpyObj('TerminalTypeService', 
      ['getAllTerminalTypes', 'updateTerminalType', 'deleteTerminalType']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [ TerminalTypeComponent ],
      providers: [
        { provide: TerminalTypeService, useValue: terminalTypeServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TerminalTypeComponent);
    component = fixture.componentInstance;
    mockTerminalTypeService = TestBed.inject(TerminalTypeService) as jasmine.SpyObj<TerminalTypeService>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;

    mockTerminalTypeService.getAllTerminalTypes.and.returnValue(of());
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load all terminal types on init', () => {
    component.ngOnInit();
    
    expect(mockTerminalTypeService.getAllTerminalTypes).toHaveBeenCalled();
    expect(component.terminalTypes).toEqual(mockTerminalTypes);
    expect(component.filteredTerminalTypes).toEqual(mockTerminalTypes);
  });

  it('should handle error when loading terminal types', () => {
    spyOn(Swal, 'fire');
    mockTerminalTypeService.getAllTerminalTypes.and.returnValue(throwError(() => new Error('Service error')));
    
    component.ngOnInit();
    
    expect(Swal.fire).toHaveBeenCalledWith('Error!', 'There was a problem loading terminal types.', 'error');
  });

  it('should filter terminal types by name on enter key', () => {
    component.terminalTypes = mockTerminalTypes;
    component.filterValue = 'pos';
    
    component.onEnterKey(new Event('keyup'));
    
    expect(component.filteredTerminalTypes).toEqual([mockTerminalTypes[0]]);
  });

  it('should show all terminal types when filter is empty', () => {
    component.terminalTypes = mockTerminalTypes;
    component.filterValue = '';
    
    component.onEnterKey(new Event('keyup'));
    
    expect(component.filteredTerminalTypes).toEqual(mockTerminalTypes);
  });

  it('should reset filter and show all terminal types', () => {
    component.terminalTypes = mockTerminalTypes;
    component.filterValue = 'test';
    component.filteredTerminalTypes = [];
    
    component.onReset();
    
    expect(component.filterValue).toBe('');
    expect(component.filteredTerminalTypes).toEqual(mockTerminalTypes);
  });

  it('should update terminal type active status', () => {
    const terminalType = mockTerminalTypes[0];
    const event = { target: { checked: false } } as any;
    mockTerminalTypeService.updateTerminalType.and.returnValue(of());
    
    component.updateActiveStatus(event, terminalType);
    
    expect(terminalType.active).toBe('0');
    expect(mockTerminalTypeService.updateTerminalType).toHaveBeenCalledWith(1, terminalType);
  });

  it('should handle error when updating terminal type status', () => {
    spyOn(Swal, 'fire');
    const terminalType = { ...mockTerminalTypes[0] };
    const event = { target: { checked: false } } as any;
    mockTerminalTypeService.updateTerminalType.and.returnValue(throwError(() => new Error('Update error')));
    
    component.updateActiveStatus(event, terminalType);
    
    expect(Swal.fire).toHaveBeenCalledWith('Error!', 'There was a problem updating the terminal type status.', 'error');
    expect(terminalType.active).toBe('1'); // Should revert
  });

  it('should navigate to create terminal type', () => {
    component.navigateToCreateTerminalType();
    
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-type/create']);
  });

  it('should navigate to edit terminal type', () => {
    component.navigateToEditTerminalType(1);
    
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-type/edit', 1]);
  });

  it('should navigate to view terminal type', () => {
    component.navigateToViewTerminalType(1);
    
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/terminal-type/view', 1]);
  });

  it('should delete terminal type when confirmed', async () => {
    spyOn(Swal, 'fire').and.returnValue(Promise.resolve({ isConfirmed: true } as any));
    mockTerminalTypeService.deleteTerminalType.and.returnValue(of());
    component.terminalTypes = [...mockTerminalTypes];
    component.filteredTerminalTypes = [...mockTerminalTypes];
    
    component.deleteTerminalType(1, 'POS Terminal');
    await fixture.whenStable();
    
    expect(mockTerminalTypeService.deleteTerminalType).toHaveBeenCalledWith(1);
    expect(component.terminalTypes.length).toBe(2);
    expect(component.filteredTerminalTypes.length).toBe(2);
  });

  it('should handle error when deleting terminal type', async () => {
    spyOn(Swal, 'fire').and.returnValues(
      Promise.resolve({ isConfirmed: true } as any),
      Promise.resolve({} as any)
    );
    mockTerminalTypeService.deleteTerminalType.and.returnValue(throwError(() => new Error('Delete error')));
    
    component.deleteTerminalType(1, 'POS Terminal');
    await fixture.whenStable();
    
    expect(Swal.fire).toHaveBeenCalledWith('Error!', 'There was a problem deleting the POS Terminal.', 'error');
  });

  it('should not delete terminal type when not confirmed', async () => {
    spyOn(Swal, 'fire').and.returnValue(Promise.resolve({ isConfirmed: false } as any));
    
    component.deleteTerminalType(1, 'POS Terminal');
    await fixture.whenStable();
    
    expect(mockTerminalTypeService.deleteTerminalType).not.toHaveBeenCalled();
  });
});