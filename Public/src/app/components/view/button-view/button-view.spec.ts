import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonViewComponent } from './button-view';

describe('ButtonView', () => {
  let component: ButtonViewComponent;
  let fixture: ComponentFixture<ButtonViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ButtonViewComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
