import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViewMerchantTerminalComponent } from './view-merchant-terminal.component';
import { MerchantTerminalService } from 'src/app/services/merchant-terminal/merchant-terminal.service';

describe('ViewMerchantTerminalComponent', () => {
  let component: ViewMerchantTerminalComponent;
  let fixture: ComponentFixture<ViewMerchantTerminalComponent>;
  let mockMerchantTerminalService: jasmine.SpyObj<MerchantTerminalService>;

  const mockActivatedRoute = {
    snapshot: {
      paramMap: {
        get: (key: string) => '1'
      }
    }
  };

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('MerchantTerminalService', ['getMerchantTerminalById']);

    await TestBed.configureTestingModule({
      declarations: [ViewMerchantTerminalComponent],
      imports: [RouterTestingModule, HttpClientTestingModule],
      providers: [
        { provide: MerchantTerminalService, useValue: spy },
        { provide: ActivatedRoute, useValue: mockActivatedRoute }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ViewMerchantTerminalComponent);
    component = fixture.componentInstance;
    mockMerchantTerminalService = TestBed.inject(MerchantTerminalService) as jasmine.SpyObj<MerchantTerminalService>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load merchant terminal on init', () => {
    const mockTerminal = {
      id: 1,
      name: 'Test Terminal',
      terminalId: 'T001',
      pedId: 'P001',
      pedSerialNo: 'SN001',
      deviceType: 'POS',
      activateOn: '2024-01-01',
      posSafety: true,
      status: true,
      deviceModelType: 'Model1',
      merchantChain: 'Chain1',
      merchantStore: 'Store1'
    };

    mockMerchantTerminalService.getMerchantTerminalById.and.returnValue(of({ data: mockTerminal }));
    
    component.ngOnInit();
    
    expect(mockMerchantTerminalService.getMerchantTerminalById).toHaveBeenCalledWith(1);
    expect(component.merchantTerminal).toEqual(mockTerminal);
  });
});