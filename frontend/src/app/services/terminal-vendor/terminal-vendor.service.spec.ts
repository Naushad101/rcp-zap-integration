import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TerminalVendorService } from './terminal-vendor.service';
import { TerminalVendor } from '../../models/TerminalVendor';
import { ResponseEntityData } from '../../models/ResponseEntityData';

describe('TerminalVendorService', () => {
  let service: TerminalVendorService;
  let httpMock: HttpTestingController;

  const baseURL = 'http://localhost:8081/api/terminal-vendors';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TerminalVendorService]
    });
    service = TestBed.inject(TerminalVendorService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create terminal vendor', () => {
    const mockTerminalVendor: TerminalVendor = {
        name: 'Test Vendor', active: '0',
        status: ''
    };
    const mockResponse: ResponseEntityData = {
      data: mockTerminalVendor,
      message: 'Success',
      status: ''
    };

    service.createTerminalVendor(mockTerminalVendor).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(baseURL);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockTerminalVendor);
    req.flush(mockResponse);
  });

  it('should get all terminal vendors', () => {
    const mockTerminalVendors: TerminalVendor[] = [
      {
          id: 1, name: 'Vendor 1', active: '1',
          status: ''
      },
      {
          id: 2, name: 'Vendor 2', active: '0',
          status: ''
      }
    ];
    const mockResponse: ResponseEntityData = {
      data: mockTerminalVendors,
      message: 'Success',
      status: ''
    };

    service.getAllTerminalVendors().subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(baseURL);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should get all terminal vendors id and name', () => {
    const mockTerminalVendors = [
      { id: 1, name: 'Vendor 1' },
      { id: 2, name: 'Vendor 2' }
    ];
    const mockResponse: ResponseEntityData = {
      data: mockTerminalVendors,
      message: 'Success',
      status: ''
    };

    service.getAllTerminalVendorsIdAndName().subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/filter`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should get terminal vendor by id', () => {
    const vendorId = 1;
    const mockTerminalVendor: TerminalVendor = {
        id: 1, name: 'Vendor 1', active: '1',
        status: ''
    };
    const mockResponse: ResponseEntityData = {
      data: mockTerminalVendor,
      message: 'Success',
      status: ''
    };

    service.getTerminalVendorById(vendorId).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/${vendorId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should update terminal vendor', () => {
    const vendorId = 1;
    const mockTerminalVendor: TerminalVendor = {
        id: 1, name: 'Updated Vendor', active: '1',
        status: ''
    };
    const mockResponse: ResponseEntityData = {
      data: mockTerminalVendor,
      message: 'Success',
      status: ''
    };

    service.updateTerminalVendor(vendorId, mockTerminalVendor).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/${vendorId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockTerminalVendor);
    req.flush(mockResponse);
  });

  it('should delete terminal vendor', () => {
    const vendorId = 1;
    const mockResponse: ResponseEntityData = {
      data: null,
      message: 'Deleted successfully',
      status: ''
    };

    service.deleteTerminalVendor(vendorId).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/${vendorId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(mockResponse);
  });
}
)