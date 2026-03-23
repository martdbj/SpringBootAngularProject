import { TestBed } from '@angular/core/testing';

import { EmployeeService } from './employee.service';
import { Employee } from '../models/employee';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

const mockEmployee: Employee = {
  id: '1',
  name: 'Pep',
  email: 'pep@gmail.com',
  companyId: '',
  devicesId: []
};

const mockEmployees = [mockEmployee];

describe('EmployeeService', () => {
  let service: EmployeeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        EmployeeService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    });

    service = TestBed.inject(EmployeeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy;
  })

  it('(GET) should get all employees', () => {
    service.getEmployees().subscribe(employees => {
      expect(employees).toBeTruthy();
      expect(employees.length).toBe(1);
      const firstEmployee = employees[0];
      expect(firstEmployee.name).toBe(mockEmployee.name);
    })
    const mockReq = httpMock.expectOne(service.apiURL + "/employees");
    expect(mockReq.request.method).toBe("GET");
    mockReq.flush(Object.values(mockEmployees));
  });

  it('(GET) should get employee by id', () => {
    service.getEmployeeById('1').subscribe(employee => {
      expect(employee).toBeTruthy();
      expect(employee.name).toBe(mockEmployee.name);
    })
    const mockReq = httpMock.expectOne(`${service.apiURL}/employees/${mockEmployee.id}`);
    expect(mockReq.request.method).toBe("GET");
    mockReq.flush(mockEmployee);
  });

  it('(PUT) should update employee by id', () => {
    let modifiedEmployee = mockEmployee;
    modifiedEmployee.name = "pepUpdated";
    service.updateEmployeeById('1', modifiedEmployee).subscribe(employee => {
      expect(employee).toBeTruthy();
      expect(employee.name).toBe(modifiedEmployee.name);
    })
    const mockReq = httpMock.expectOne(`${service.apiURL}/employees/${mockEmployee.id}`);
    expect(mockReq.request.method).toBe("PUT");
    mockReq.flush(modifiedEmployee);
  });

  it('(POST) should add a new employee', () => {
    const newEmployee = { ...mockEmployee, id: '2', name: 'New User' };
    service.addNewEmployee(newEmployee).subscribe();
    const mockReq = httpMock.expectOne(`${service.apiURL}/employees`);
    expect(mockReq.request.method).toBe("POST");
    expect(mockReq.request.body).toEqual(newEmployee);
    mockReq.flush(null);
  });

  it('(DELETE) should delete employee by id', () => {
    service.deleteEmployeeById('1').subscribe();
    const mockReq = httpMock.expectOne(`${service.apiURL}/employees/1`);
    expect(mockReq.request.method).toBe("DELETE");
    mockReq.flush(null);
  });

  it('(PUT) should add device to employee', () => {
    const empId = '1';
    const serial = 'SN123';
    service.addDeviceToEmployee(empId, serial).subscribe();
    const mockReq = httpMock.expectOne(`${service.apiURL}/employees/${empId}/addDevice/${serial}`);
    expect(mockReq.request.method).toBe("PUT");
    mockReq.flush(null);
  });

  it('(DELETE) should remove device from employee', () => {
    const empId = '1';
    const serial = 'SN123';
    service.removeDelviceFromEmployee(empId, serial).subscribe();
    const mockReq = httpMock.expectOne(`${service.apiURL}/employees/${empId}/deleteDevice/${serial}`);
    expect(mockReq.request.method).toBe("DELETE");
    mockReq.flush(null);
  });
});
