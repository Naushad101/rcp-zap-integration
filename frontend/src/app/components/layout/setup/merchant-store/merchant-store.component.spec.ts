import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MerchantStoresComponent } from './merchant-store.component';

describe('MerchantStoresComponent', () => {
  let component: MerchantStoresComponent;
  let fixture: ComponentFixture<MerchantStoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MerchantStoresComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(MerchantStoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});