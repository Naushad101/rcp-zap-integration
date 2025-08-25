import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAcquirerComponent } from './create-acquirer.component';

describe('CreateAcquirerComponent', () => {
  let component: CreateAcquirerComponent;
  let fixture: ComponentFixture<CreateAcquirerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateAcquirerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateAcquirerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
