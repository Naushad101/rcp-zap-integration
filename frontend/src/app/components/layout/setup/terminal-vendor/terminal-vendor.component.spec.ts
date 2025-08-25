import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TerminalVendorComponent } from './terminal-vendor.component';

describe('TerminalVendorComponent', () => {
  let component: TerminalVendorComponent;
  let fixture: ComponentFixture<TerminalVendorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TerminalVendorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TerminalVendorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});