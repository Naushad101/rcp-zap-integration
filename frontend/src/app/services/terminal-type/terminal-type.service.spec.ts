import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TerminalTypeService } from './terminal-type.service';
import { TerminalType } from '../../models/TerminalTypes';
import { ResponseEntityData } from '../../models/ResponseEntityData';

describe('TerminalTypeService', () => {
  let service: TerminalTypeService;
  let httpMock: HttpTestingController;

  const baseURL = 'http://localhost:8081/api/terminal-types';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TerminalTypeService]
    });
    service = TestBed.inject(TerminalTypeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create terminal type', () => {
    const mockTerminalType: TerminalType = { name: 'POS Terminal', active: '1' };
    const mockResponse: ResponseEntityData = {
        data: mockTerminalType, message: 'Success',
        status: ''
    };

    service.createTerminalType(mockTerminalType).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(baseURL);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockTerminalType);
    req.flush(mockResponse);
  });

  it('should get all terminal types', () => {
    const mockTerminalTypes: TerminalType[] = [
      { id: 1, name: 'POS Terminal', active: '1' },
      { id: 2, name: 'ATM Terminal', active: '0' }
    ];
    const mockResponse: ResponseEntityData = {
        data: mockTerminalTypes, message: 'Success',
        status: ''
    };

    service.getAllTerminalTypes().subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(baseURL);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should get all terminal types id and name', () => {
    const mockTerminalTypes = [
      { id: 1, name: 'POS Terminal' },
      { id: 2, name: 'ATM Terminal' }
    ];
    const mockResponse: ResponseEntityData = {
        data: mockTerminalTypes, message: 'Success',
        status: ''
    };

    service.getAllTerminalTypesIdAndName().subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/filter`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should get terminal type by id', () => {
    const terminalTypeId = 1;
    const mockTerminalType: TerminalType = { id: 1, name: 'POS Terminal', active: '1' };
    const mockResponse: ResponseEntityData = {
        data: mockTerminalType, message: 'Success',
        status: ''
    };

    service.getTerminalTypeById(terminalTypeId).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/${terminalTypeId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should update terminal type', () => {
    const terminalTypeId = 1;
    const mockTerminalType: TerminalType = { id: 1, name: 'Updated POS Terminal', active: '1' };
    const mockResponse: ResponseEntityData = {
        data: mockTerminalType, message: 'Success',
        status: ''
    };

    service.updateTerminalType(terminalTypeId, mockTerminalType).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/${terminalTypeId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockTerminalType);
    req.flush(mockResponse);
  });

  it('should delete terminal type', () => {
    const terminalTypeId = 1;
    const mockResponse: ResponseEntityData = {
        data: null, message: 'Deleted successfully',
        status: ''
    };

    service.deleteTerminalType(terminalTypeId).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/${terminalTypeId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(mockResponse);
  });
});