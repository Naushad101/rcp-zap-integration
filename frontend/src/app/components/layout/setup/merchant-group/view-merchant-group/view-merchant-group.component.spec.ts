import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMerchantGroupComponent } from './view-merchant-group.component';

describe('ViewMerchantGroupComponent', () => {
  let component: ViewMerchantGroupComponent;
  let fixture: ComponentFixture<ViewMerchantGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewMerchantGroupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewMerchantGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
