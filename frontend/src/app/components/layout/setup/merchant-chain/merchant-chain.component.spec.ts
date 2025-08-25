import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MerchantChainComponent } from './merchant-chain.component';

describe('MerchantChainComponent', () => {
  let component: MerchantChainComponent;
  let fixture: ComponentFixture<MerchantChainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MerchantChainComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MerchantChainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
