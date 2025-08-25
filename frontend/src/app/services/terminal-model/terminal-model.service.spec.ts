import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TerminalModelService } from './terminal-model.service';
import { TerminalModel } from '../../models/TerminalModel';
import { ResponseEntityData } from '../../models/ResponseEntityData';

describe('TerminalModelService', () => {
  let service: TerminalModelService;
  let httpMock: HttpTestingController;
  const baseURL = 'http://localhost:8081/api/terminal-models';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TerminalModelService]
    });
    service = TestBed.inject(TerminalModelService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create terminal model', () => {
    const mockModel: TerminalModel = {
      name: 'Test Model',
      terminalVendorId: '1',
      terminalTypeId: '1',
      terminalSitingId: '1'
    };

    const mockResponse: ResponseEntityData = {
      data: mockModel,
      message: 'Success',
      status: ''
    };

    service.createTerminalModel(mockModel).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(baseURL);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockModel);
    req.flush(mockResponse);
  });

  it('should get all terminal models', () => {
    const mockModels: TerminalModel[] = [
      { id: 1, name: 'Model 1', terminalVendorId: '1', terminalTypeId: '1', terminalSitingId: '1' },
      { id: 2, name: 'Model 2', terminalVendorId: '2', terminalTypeId: '2', terminalSitingId: '2' }
    ];

    const mockResponse: ResponseEntityData = {
      data: mockModels,
      message: 'Success',
      status: ''
    };

    service.getAllTerminalModels().subscribe(response => {
      expect(response).toEqual(mockResponse);
      expect(response.data).toEqual(mockModels);
    });

    const req = httpMock.expectOne(baseURL);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should get terminal models id and name only', () => {
    const mockModels = [
      { id: 1, name: 'Model 1' },
      { id: 2, name: 'Model 2' }
    ];

    const mockResponse: ResponseEntityData = {
      data: mockModels,
      message: 'Success',
      status: ''
    };

    service.getAllTerminalModelsIdAndName().subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/filter`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should get terminal model by id', () => {
    const mockModel: TerminalModel = {
      id: 1,
      name: 'Test Model',
      terminalVendorId: '1',
      terminalTypeId: '1',
      terminalSitingId: '1'
    };

    const mockResponse: ResponseEntityData = {
      data: mockModel,
      message: 'Success',
      status: ''
    };

    service.getTerminalModelById(1).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should update terminal model', () => {
    const mockModel: TerminalModel = {
      id: 1,
      name: 'Updated Model',
      terminalVendorId: '1',
      terminalTypeId: '1',
      terminalSitingId: '1'
    };

    const mockResponse: ResponseEntityData = {
      data: mockModel,
      message: 'Success',
      status: ''
    };

    service.updateTerminalModel(1, mockModel).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockModel);
    req.flush(mockResponse);
  });

  it('should delete terminal model', () => {
    const mockResponse: ResponseEntityData = {
      data: null,
      message: 'Deleted successfully',
      status: ''
    };

    service.deleteTerminalModel(1).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(mockResponse);
  });
});