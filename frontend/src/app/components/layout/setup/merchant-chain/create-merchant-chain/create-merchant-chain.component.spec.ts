import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMerchantChainComponent } from './create-merchant-chain.component';

describe('CreateMerchantChainComponent', () => {
  let component: CreateMerchantChainComponent;
  let fixture: ComponentFixture<CreateMerchantChainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMerchantChainComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMerchantChainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
