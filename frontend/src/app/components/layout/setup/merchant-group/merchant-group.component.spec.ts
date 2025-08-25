import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MerchantGroupComponent } from './merchant-group.component';

describe('MerchantGroupComponent', () => {
  let component: MerchantGroupComponent;
  let fixture: ComponentFixture<MerchantGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MerchantGroupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MerchantGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
