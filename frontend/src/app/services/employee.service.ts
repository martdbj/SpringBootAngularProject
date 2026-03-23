import { inject, Injectable } from '@angular/core';
import { Employee } from '../models/employee';
import { Device } from '../models/device';
import { DeviceService } from './device.service';
import { CompanyService } from './company.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {

  public apiURL = "http://localhost:8080"
  private http = inject(HttpClient);

  deviceService = inject(DeviceService)
  companyService = inject(CompanyService)

  getEmployeeById(id: string): Observable<Employee> {
    return this.http.get<Employee>(this.apiURL + "/employees/"+id)
  }

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.apiURL + "/employees")
  }

  addNewEmployee(newEmployee: Employee): Observable<void> {
    return this.http.post<void>(this.apiURL + "/employees", newEmployee)
  }

  deleteEmployeeById(id: string): Observable<void> {
    return this.http.delete<void>(this.apiURL + "/employees/"+id)
  }

  updateEmployeeById(id: string, updatedEmployee: Employee): Observable<Employee> {
    return this.http.put<Employee>(this.apiURL + "/employees/"+id, updatedEmployee)
  }

  addDeviceToEmployee(employeeId: string, deviceSerialNumber: string): Observable<void> {
    return this.http.put<void>(this.apiURL + "/employees/"+employeeId + "/addDevice/"+deviceSerialNumber, {})
  }

  removeDelviceFromEmployee(employeeId: string, deviceSerialNumber: string): Observable<void> {
    return this.http.delete<void>(this.apiURL + "/employees/"+employeeId + "/deleteDevice/"+deviceSerialNumber)
  }
}
