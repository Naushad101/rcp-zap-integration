import { TestBed } from '@angular/core/testing';

import { MerchantStoreService } from './merchant-store';

describe('MerchantStoreService', () => {
  let service: MerchantStoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MerchantStoreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});