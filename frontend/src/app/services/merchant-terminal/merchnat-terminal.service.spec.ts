import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MerchantTerminalService } from './merchant-terminal.service';
import { MerchantTerminal } from 'src/app/models/merchant-terminal';

describe('MerchantTerminalService', () => {
  let service: MerchantTerminalService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MerchantTerminalService]
    });
    service = TestBed.inject(MerchantTerminalService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all merchant terminals', () => {
    const dummyTerminals: MerchantTerminal[] = [{ id: 1, name: 'Terminal 1', deviceType: 'Type1', deviceModelType: 'Model1', merchantChain: 'Chain1', merchantStore: 'Store1', terminalId: 'T001', pedId: 'P001', pedSerialNo: 'S001', activateOn: '2025-07-29', posSafety: true, status: true }];
    
    service.getAllMerchantTerminals().subscribe(terminals => {
      expect(terminals.data.length).toBe(1);
      expect(terminals.data).toEqual(dummyTerminals);
    });

    const req = httpMock.expectOne('https://api.example.com/merchant-terminals');
    expect(req.request.method).toBe('GET');
    req.flush({ data: dummyTerminals });
  });

  it('should retrieve a merchant terminal by id', () => {
    const dummyTerminal: MerchantTerminal = { id: 1, name: 'Terminal 1', deviceType: 'Type1', deviceModelType: 'Model1', merchantChain: 'Chain1', merchantStore: 'Store1', terminalId: 'T001', pedId: 'P001', pedSerialNo: 'S001', activateOn: '2025-07-29', posSafety: true, status: true };
    
    service.getMerchantTerminalById(1).subscribe(terminal => {
      expect(terminal.data).toEqual(dummyTerminal);
    });

    const req = httpMock.expectOne('https://api.example.com/merchant-terminals/1');
    expect(req.request.method).toBe('GET');
    req.flush({ data: dummyTerminal });
  });

  it('should create a merchant terminal', () => {
    const newTerminal: MerchantTerminal = { name: 'Terminal 2', deviceType: 'Type2', deviceModelType: 'Model2', merchantChain: 'Chain2', merchantStore: 'Store2', terminalId: 'T002', pedId: 'P002', pedSerialNo: 'S002', activateOn: '2025-07-30', posSafety: false, status: true };
    
    service.createMerchantTerminal(newTerminal).subscribe(response => {
      expect(response.success).toBeTrue();
    });

    const req = httpMock.expectOne('https://api.example.com/merchant-terminals');
    expect(req.request.method).toBe('POST');
    req.flush({ success: true });
  });

  it('should update a merchant terminal', () => {
    const updatedTerminal: MerchantTerminal = { id: 1, name: 'Terminal 1 Updated', deviceType: 'Type1', deviceModelType: 'Model1', merchantChain: 'Chain1', merchantStore: 'Store1', terminalId: 'T001', pedId: 'P001', pedSerialNo: 'S001', activateOn: '2025-07-29', posSafety: true, status: true };
    
    service.updateMerchantTerminal(updatedTerminal).subscribe(response => {
      expect(response.success).toBeTrue();
    });

    const req = httpMock.expectOne('https://api.example.com/merchant-terminals/1');
    expect(req.request.method).toBe('PUT');
    req.flush({ success: true });
  });

  it('should delete a merchant terminal', () => {
    service.deleteMerchantTerminal(1).subscribe(response => {
      expect(response.status).toBe('success');
    });

    const req = httpMock.expectOne('https://api.example.com/merchant-terminals/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({ status: 'success' });
  });
});