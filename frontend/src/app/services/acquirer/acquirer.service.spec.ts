import { TestBed } from '@angular/core/testing';

import { AcquirerService } from './acquirer.service';

describe('AcquirerService', () => {
  let service: AcquirerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AcquirerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
