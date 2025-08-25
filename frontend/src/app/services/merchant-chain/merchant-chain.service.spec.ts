import { TestBed } from '@angular/core/testing';

import { MerchantChainService } from './merchant-chain.service';

describe('MerchantChainService', () => {
  let service: MerchantChainService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MerchantChainService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
