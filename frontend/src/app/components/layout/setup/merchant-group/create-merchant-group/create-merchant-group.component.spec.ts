import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMerchantGroupComponent } from './create-merchant-group.component';

describe('CreateMerchantGroupComponent', () => {
  let component: CreateMerchantGroupComponent;
  let fixture: ComponentFixture<CreateMerchantGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMerchantGroupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMerchantGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
