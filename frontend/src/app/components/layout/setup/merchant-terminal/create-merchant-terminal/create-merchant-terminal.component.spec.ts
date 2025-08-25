import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMerchantTerminalComponent } from './create-merchant-terminal.component';

describe('CreateMerchantTerminalComponent', () => {
  let component: CreateMerchantTerminalComponent;
  let fixture: ComponentFixture<CreateMerchantTerminalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateMerchantTerminalComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateMerchantTerminalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});