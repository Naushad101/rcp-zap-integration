import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAcquirerComponent } from './view-acquirer.component';

describe('ViewAcquirerComponent', () => {
  let component: ViewAcquirerComponent;
  let fixture: ComponentFixture<ViewAcquirerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewAcquirerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAcquirerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
