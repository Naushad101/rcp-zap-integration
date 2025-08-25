import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMerchantStoreComponent } from './create-merchant-store.component';

describe('CreateMerchantStoreComponent', () => {
  let component: CreateMerchantStoreComponent;
  let fixture: ComponentFixture<CreateMerchantStoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMerchantStoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMerchantStoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});