import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TerminalSitingService } from './terminal-siting.service';
import { TerminalSiting } from '../../models/TerminalSiting';
import { ResponseEntityData } from '../../models/ResponseEntityData';

describe('TerminalSitingService', () => {
  let service: TerminalSitingService;
  let httpMock: HttpTestingController;
  const baseURL = 'http://localhost:8081/api/terminal-sitings';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TerminalSitingService]
    });
    service = TestBed.inject(TerminalSitingService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create terminal siting', () => {
    const mockSiting: TerminalSiting = {
      name: 'Test Siting',
      status: '',
      active: '0'
    };

    const mockResponse: ResponseEntityData = {
      data: mockSiting,
      message: 'Success',
      status: ''
    };

    service.createTerminalSiting(mockSiting).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(baseURL);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockSiting);
    req.flush(mockResponse);
  });

  it('should get all terminal sitings', () => {
    const mockSitings: TerminalSiting[] = [
      { id: 1, name: 'Siting 1', status: '', active: '1' },
      { id: 2, name: 'Siting 2', status: '', active: '0' }
    ];

    const mockResponse: ResponseEntityData = {
      data: mockSitings,
      message: 'Success',
      status: ''
    };

    service.getAllTerminalSitings().subscribe(response => {
      expect(response).toEqual(mockResponse);
      expect(response.data)
    });

    const req = httpMock.expectOne(baseURL);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should get terminal sitings id and name only', () => {
    const mockSitings = [
      { id: 1, name: 'Siting 1' },
      { id: 2, name: 'Siting 2' }
    ];

    const mockResponse: ResponseEntityData = {
      data: mockSitings,
      message: 'Success',
      status: ''
    };

    service.getAllTerminalSitingsIdAndName().subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/filter`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should get terminal siting by id', () => {
    const mockSiting: TerminalSiting = {
      id: 1,
      name: 'Test Siting',
      status: '',
      active: '1'
    };

    const mockResponse: ResponseEntityData = {
      data: mockSiting,
      message: 'Success',
      status: ''
    };

    service.getTerminalSitingById(1).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should update terminal siting', () => {
    const mockSiting: TerminalSiting = {
      id: 1,
      name: 'Updated Siting',
      status: '',
      active: '1'
    };

    const mockResponse: ResponseEntityData = {
      data: mockSiting,
      message: 'Success',
      status: ''
    };

    service.updateTerminalSiting(1, mockSiting).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockSiting);
    req.flush(mockResponse);
  });

  it('should delete terminal siting', () => {
    const mockResponse: ResponseEntityData = {
      data: null,
      message: 'Deleted successfully',
      status: ''
    };

    service.deleteTerminalSiting(1).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${baseURL}/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(mockResponse);
  });
});