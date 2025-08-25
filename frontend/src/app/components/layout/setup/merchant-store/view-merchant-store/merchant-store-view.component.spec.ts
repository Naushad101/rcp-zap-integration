import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { of, throwError } from 'rxjs';
import { MerchantStoreViewComponent } from './merchant-store-view.component';
import { MerchantStoreService } from 'src/app/services/merchant-store/merchant-store';
import { MerchantStore } from 'src/app/models/merchant-store';
import { ResponseEntityData } from 'src/app/models/ResponseEntityData';

describe('MerchantStoreViewComponent', () => {
  let component: MerchantStoreViewComponent;
  let fixture: ComponentFixture<MerchantStoreViewComponent>;
  let mockMerchantStoreService: jasmine.SpyObj<MerchantStoreService>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockLocation: jasmine.SpyObj<Location>;
  let mockActivatedRoute: any;

  const mockMerchantStore: MerchantStore = {
    id: 1,
    code: 'LOC001',
    name: 'Main Branch',
    description: 'Primary location for retail operations',
    activateOn: '2025-07-29T12:00:00.000+00:00',
    expiryOn: '2026-07-29T12:00:00.000+00:00',
    deleted: 'N',
    locked: 'N',
    posSafetyFlag: 'Y',
    reversalTimeout: '300',
    additionalAttribute: "{\"storeType\": \"outlet\", \"manager\": \"Jane Smith\"}",
    latitude: 40.7128,
    longitude: -74.006,
    locationDetailId: 1,
    address1: '123 Main St',
    address2: 'Suite 100',
    city: 'New York',
    zip: '10001',
    phone: '555-123-4567',
    fax: '555-123-4568',
    website: 'https://example.com',
    email: 'contact@example.com',
    region: 1,
    locationId: 'LOC001',
    atmDeleted: 'N',
    atmLocked: 'N',
    sublocation: false,
    merchantChain: '',
    country: '',
    state: ''
  };

  const mockSuccessResponse: ResponseEntityData<MerchantStore> = {
    success: true,
    message: 'Success',
    data: mockMerchantStore,
    status: ''
  };

  beforeEach(async () => {
    const merchantStoreServiceSpy = jasmine.createSpyObj('MerchantStoreService', [
      'getMerchantStoreById',
      'deleteMerchantStore'
    ]);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const locationSpy = jasmine.createSpyObj('Location', ['back']);

    mockActivatedRoute = {
      params: of({ id: '1' })
    };

    await TestBed.configureTestingModule({
      declarations: [MerchantStoreViewComponent],
      providers: [
        { provide: MerchantStoreService, useValue: merchantStoreServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: Location, useValue: locationSpy },
        { provide: ActivatedRoute, useValue: mockActivatedRoute }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MerchantStoreViewComponent);
    component = fixture.componentInstance;
    mockMerchantStoreService = TestBed.inject(MerchantStoreService) as jasmine.SpyObj<MerchantStoreService>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    mockLocation = TestBed.inject(Location) as jasmine.SpyObj<Location>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load merchant store with direct response format', () => {
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(of(mockMerchantStore));

    fixture.detectChanges();

    expect(component.storeId).toBe(1);
    expect(mockMerchantStoreService.getMerchantStoreById).toHaveBeenCalledWith(1);
    expect(component.merchantStore).toEqual(mockMerchantStore);
    expect(component.loading).toBeFalse();
    expect(component.error).toBeNull();
  });

  it('should load merchant store with wrapped success response', () => {
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(of(mockSuccessResponse));

    fixture.detectChanges();

    expect(component.storeId).toBe(1);
    expect(mockMerchantStoreService.getMerchantStoreById).toHaveBeenCalledWith(1);
    expect(component.merchantStore).toEqual(mockMerchantStore);
    expect(component.loading).toBeFalse();
    expect(component.error).toBeNull();
  });

  it('should load merchant store from array response', () => {
    const arrayResponse = [mockMerchantStore];
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(of(arrayResponse));

    fixture.detectChanges();

    expect(component.merchantStore).toEqual(mockMerchantStore);
    expect(component.loading).toBeFalse();
    expect(component.error).toBeNull();
  });

  it('should handle error when loading merchant store', () => {
    const errorResponse = throwError(() => new Error('Network error'));
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(errorResponse);

    fixture.detectChanges();

    expect(component.loading).toBeFalse();
    expect(component.error).toBe('Failed to load merchant store details');
    expect(component.merchantStore).toBeTruthy();
    expect(component.merchantStore?.name).toBe('Downtown Main Branch');
  });

  it('should handle failed response when loading merchant store', () => {
    const failedResponse: ResponseEntityData<MerchantStore> = {
      success: false,
      message: 'Store not found',
      data: null,
      status: ''
    };
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(of(failedResponse));

    fixture.detectChanges();

    expect(component.loading).toBeFalse();
    expect(component.error).toBe('Store not found');
    expect(component.merchantStore).toBeTruthy();
    expect(component.merchantStore?.name).toBe('Downtown Main Branch');
  });

  it('should handle store not found in array response', () => {
    const arrayResponse = [{ ...mockMerchantStore, id: 999 }];
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(of(arrayResponse));

    fixture.detectChanges();

    expect(component.loading).toBeFalse();
    expect(component.error).toBe('Merchant store not found in response');
    expect(component.merchantStore).toBeTruthy();
    expect(component.merchantStore?.name).toBe('Downtown Main Branch');
  });

  it('should handle invalid response format', () => {
    const invalidResponse = "invalid response";
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(of());

    fixture.detectChanges();

    expect(component.loading).toBeFalse();
    expect(component.error).toBe('Invalid response format');
    expect(component.merchantStore).toBeTruthy();
    expect(component.merchantStore?.name).toBe('Downtown Main Branch');
  });

  it('should retry loading merchant store', () => {
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(of(mockMerchantStore));

    component.retry();

    expect(mockMerchantStoreService.getMerchantStoreById).toHaveBeenCalledWith(component.storeId);
  });

  it('should go back when goBack is called', () => {
    component.goBack();

    expect(mockLocation.back).toHaveBeenCalled();
  });

  it('should navigate to edit page when onEdit is called', () => {
    component.merchantStore = mockMerchantStore;

    component.onEdit();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/layout/merchant-stores/edit/1']);
  });

  it('should not navigate to edit page when merchantStore is null', () => {
    component.merchantStore = null;

    component.onEdit();

    expect(mockRouter.navigate).not.toHaveBeenCalled();
  });

  it('should delete merchant store when confirmed', () => {
    const deleteResponse: ResponseEntityData<null> = {
      success: true,
      message: 'Deleted successfully',
      data: null,
      status: ''
    };
    mockMerchantStoreService.deleteMerchantStore.and.returnValue(of(deleteResponse));
    component.merchantStore = mockMerchantStore;

    (component as any).performDelete();

    expect(mockMerchantStoreService.deleteMerchantStore).toHaveBeenCalledWith(1);
    expect(component.loading).toBeFalse();
  });

  it('should handle delete error', () => {
    const errorResponse = throwError(() => new Error('Delete failed'));
    mockMerchantStoreService.deleteMerchantStore.and.returnValue(errorResponse);
    component.merchantStore = mockMerchantStore;

    (component as any).performDelete();

    expect(mockMerchantStoreService.deleteMerchantStore).toHaveBeenCalledWith(1);
    expect(component.loading).toBeFalse();
  });

  it('should handle delete failure response', () => {
    const failedResponse: ResponseEntityData<null> = {
      success: false,
      message: 'Cannot delete store',
      data: null,
      status: ''
    };
    mockMerchantStoreService.deleteMerchantStore.and.returnValue(of(failedResponse));
    component.merchantStore = mockMerchantStore;

    (component as any).performDelete();

    expect(mockMerchantStoreService.deleteMerchantStore).toHaveBeenCalledWith(1);
    expect(component.loading).toBeFalse();
  });

  it('should return correct region name', () => {
    expect(component.getRegionName(1)).toBe('North');
    expect(component.getRegionName(2)).toBe('South');
    expect(component.getRegionName(3)).toBe('East');
    expect(component.getRegionName(4)).toBe('West');
    expect(component.getRegionName(5)).toBe('Central');
    expect(component.getRegionName(99)).toBe('Region 99');
  });

  it('should format date correctly', () => {
    const formattedDate = component.formatDate('2023-01-01');
    expect(formattedDate).toBe('January 1, 2023');
  });

  it('should return N/A for empty date string', () => {
    expect(component.formatDate('')).toBe('N/A');
    expect(component.formatDate(null as any)).toBe('N/A');
  });

  it('should return original string for invalid date', () => {
    const invalidDate = 'invalid-date';
    expect(component.formatDate(invalidDate)).toBe(invalidDate);
  });

  it('should handle invalid store ID', () => {
    mockActivatedRoute.params = of({ id: 'invalid' });

    component.ngOnInit();

    expect(component.storeId).toBeNaN();
    expect(component.error).toBe('Invalid store ID');
  });

  it('should handle missing store ID', () => {
    mockActivatedRoute.params = of({});

    component.ngOnInit();

    expect(component.error).toBe('Invalid store ID');
  });

  it('should load dummy data when API fails', () => {
    const errorResponse = throwError(() => new Error('API Error'));
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(errorResponse);

    fixture.detectChanges();

    expect(component.merchantStore).toBeTruthy();
    expect(component.merchantStore?.name).toBe('Downtown Main Branch');
  });

  it('should show error when dummy data not found', () => {
    const errorResponse = throwError(() => new Error('API Error'));
    mockMerchantStoreService.getMerchantStoreById.and.returnValue(errorResponse);
    component.storeId = 999;

    component.loadMerchantStore();

    expect(component.error).toBe('Merchant store not found');
    expect(component.merchantStore).toBeNull();
  });

  it('should not delete when merchantStore is null', () => {
    component.merchantStore = null;

    component.onDelete();

    expect(mockMerchantStoreService.deleteMerchantStore).not.toHaveBeenCalled();
  });

  it('should return correct status', () => {
    component.merchantStore = mockMerchantStore;
    expect(component.getStatus()).toBe('Enabled');

    component.merchantStore = { ...mockMerchantStore, locked: 'Y' };
    expect(component.getStatus()).toBe('Disabled');

    component.merchantStore = { ...mockMerchantStore, deleted: 'Y' };
    expect(component.getStatus()).toBe('Disabled');
  });

  it('should return correct POS status', () => {
    component.merchantStore = mockMerchantStore;
    expect(component.getPosStatus()).toBe('Enabled');

    component.merchantStore = { ...mockMerchantStore, posSafetyFlag: 'N' };
    expect(component.getPosStatus()).toBe('Disabled');
  });

  it('should correctly identify direct merchant store response', () => {
    expect((component as any).isDirectMerchantStore(mockMerchantStore)).toBeTrue();
    expect((component as any).isDirectMerchantStore(mockSuccessResponse)).toBeFalse();
    expect((component as any).isDirectMerchantStore([mockMerchantStore])).toBeFalse();
    expect((component as any).isDirectMerchantStore(null)).toBeFalse();
  });

  it('should correctly identify response with success property', () => {
    expect((component as any).hasSuccessProperty(mockSuccessResponse)).toBeTrue();
    expect((component as any).hasSuccessProperty(mockMerchantStore)).toBeFalse();
    expect((component as any).hasSuccessProperty({ data: mockMerchantStore })).toBeFalse();
  });
});