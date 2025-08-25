import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MerchantTerminalComponent } from './merchant-terminal.component';

describe('MerchantTerminalComponent', () => {
  let component: MerchantTerminalComponent;
  let fixture: ComponentFixture<MerchantTerminalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MerchantTerminalComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(MerchantTerminalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});