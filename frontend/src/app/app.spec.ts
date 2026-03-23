import { TestBed } from '@angular/core/testing';
import { App } from './app';
import { EmployeeService } from './services/employee.service';
import { inject } from 'vitest';
import { Employee } from './models/employee';
import { HttpClientTestingModule, HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { DeviceService } from './services/device.service';
import { CompanyService } from './services/company.service';
import { provideHttpClient } from '@angular/common/http';
import { Device } from './models/device';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should be 3', () => {

    const num1 = 1;
    const num2 = 2;

    const result = num1 + num2;

    expect(result).toBe(3);
  })
});



