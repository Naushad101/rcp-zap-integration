import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TerminalSitingComponent } from './terminal-siting.component';

describe('TerminalSitingComponent', () => {
  let component: TerminalSitingComponent;
  let fixture: ComponentFixture<TerminalSitingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TerminalSitingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TerminalSitingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});