import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCountryStateComponent } from './create-country-state.component';

describe('CreateCountryStateComponent', () => {
  let component: CreateCountryStateComponent;
  let fixture: ComponentFixture<CreateCountryStateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateCountryStateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateCountryStateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
