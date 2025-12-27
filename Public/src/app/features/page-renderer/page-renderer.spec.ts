import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageRenderer } from './page-renderer';

describe('PageRenderer', () => {
  let component: PageRenderer;
  let fixture: ComponentFixture<PageRenderer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PageRenderer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageRenderer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
