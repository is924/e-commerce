import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Topnav } from './top-nav';

describe('Topnav', () => {
  let component: Topnav;
  let fixture: ComponentFixture<Topnav>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        Topnav,  // standalone component
        // RouterTestingModule  <-- remove if unused
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Topnav);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
