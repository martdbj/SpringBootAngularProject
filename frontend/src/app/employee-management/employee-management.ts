import { Component, inject, output, signal } from '@angular/core';
import { Employee } from '../models/employee';
import { email, form, FormField, hidden, maxLength, minLength, pattern, required, submit } from '@angular/forms/signals';
import { EmployeeService } from '../services/employee.service';
import { CompanyService } from '../services/company.service';
import { DeviceService } from '../services/device.service';
import { Company } from '../models/company';
import { Device } from '../models/device';

@Component({
  selector: 'app-employee-management',
  imports: [FormField],
  templateUrl: './employee-management.html',
  styleUrl: './employee-management.css',
})
export class EmployeeManagement {

  employeeService = inject(EmployeeService);
  deviceService = inject(DeviceService);
  companyService = inject(CompanyService);

  devices = signal<Device[]>([]);
  employees = signal<Employee[]>([]);
  companies = signal<Company[]>([]);

  loadCompaniesAndEmployees(): void {
    this.companyService.getCompanies().subscribe(data => {
      this.companies.set(data);
    });

    this.employeeService.getEmployees().subscribe(data => {
      this.employees.set(data);
    });
    this.deviceService.getDevices().subscribe(data => {
      this.devices.set(data);
    });
  }

  ngOnInit(): void {
    this.loadCompaniesAndEmployees();
  }

  employeeModel = signal<Employee>({
    id: "",
    name: "",
    email: "",
    companyId: "",
    devicesId: []
  })

  cleanEmployeeModel = {
    id: "",
    name: "",
    email: "",
    companyId: "",
    devicesId: []
  }

  addForm = form(this.employeeModel, (fieldPath) => {
    maxLength(fieldPath.name, 255, { message: 'Name cannot be longer than 255 characters' });
    minLength(fieldPath.name, 3, { message: 'Name must be at least 3 characters long' });
    pattern(fieldPath.name, /^[a-zA-Z0-9 ]+$/, { message: 'Name cannot contain special characters' });
    required(fieldPath.name, { message: 'Name is required' });
    email(fieldPath.email, { message: 'Enter a valid email address' });
    required(fieldPath.email, { message: 'Email is required' });
  })

  updateForm = form(this.employeeModel, (fieldPath) => {
    maxLength(fieldPath.name, 255, { message: 'Name cannot be longer than 255 characters' });
    minLength(fieldPath.name, 3, { message: 'Name must be at least 3 characters long' });
    pattern(fieldPath.name, /^[a-zA-Z0-9 ]+$/, { message: 'Name cannot contain special characters' });
    required(fieldPath.name, { message: 'Name is required' });
    email(fieldPath.email, { message: 'Enter a valid email address' });
    required(fieldPath.email, { message: 'Email is required' });
  })

  addDeviceForm = form(this.employeeModel);

  addOnSubmit(event: Event) {
    event.preventDefault();
    submit(this.addForm, async () => {
      const employeeEmail = this.addForm.email().value();
      const employeeName = this.addForm.name().value();
      const companyId = this.addForm.companyId().value();

      let newEmployee: Employee = {
        id: "",
        email: employeeEmail,
        name: employeeName,
        companyId: companyId,
        devicesId: []
      }

      this.employeeService.addNewEmployee(newEmployee).subscribe(() => {
        this.employeeModel.set(this.cleanEmployeeModel);
        this.loadCompaniesAndEmployees();
      });

    })
  }

  deleteOnSubmit(id: string) {
    this.employeeService.deleteEmployeeById(id).subscribe(() => {
      this.loadCompaniesAndEmployees();
    });
  }

  updateId = "";
  updatePassIdAndFillForm(id: string) {
    this.updateId = id;

    this.employeeService.getEmployeeById(id).subscribe(employee => {
      if (employee) {
        this.employeeModel.set(employee)
      }
    })
  }

  updateOnSubmit(event: Event) {
    event.preventDefault()
    if (this.updateId) {
      this.employeeService.getEmployeeById(this.updateId)?.subscribe(employee => {
        if (employee) {
          if (this.updateForm.email().value()) employee.email = this.updateForm.email().value();
          if (this.updateForm.name().value()) employee.name = this.updateForm.name().value();
          if (this.updateForm.companyId().value()) employee.companyId = this.updateForm.companyId().value();

          this.employeeService.updateEmployeeById(this.updateId, employee).subscribe(() => {
            this.loadCompaniesAndEmployees();
            this.employeeModel.set(this.cleanEmployeeModel);
          })
        }
      })
    }
  }

  closeUpdate() {
    this.employeeModel.set(this.cleanEmployeeModel);
  }

  addDeviceOnClick(serialNumber: string) {
    if (this.updateId) {
      this.employeeService.addDeviceToEmployee(this.updateId, serialNumber).subscribe(() => {
        this.loadCompaniesAndEmployees();
      })
    }
  }

  deleteDeviceOnClick(serialNumber: string) {
    if (this.updateId) {
      this.employeeService.removeDelviceFromEmployee(this.updateId, serialNumber).subscribe(() => {
        this.loadCompaniesAndEmployees();
      })
    }
  }

  getCompanyName(id: string) {
    const company = this.companies().find(company => company.id === id);
    return company ? company.name : 'Empresa no encontrada';
  }


  displayAvailableDevices() {
    let resultList: Device[] = []
    for (let device of this.devices()) {
      if (device.employeeId === "" || device.employeeId == this.updateId) {
        resultList.push(device);
      }
    }
    return resultList;
  }


  // Improve repetition of code
  checkDeviceStatusAndReturnButtonColour(device: Device) {
    return this.checkEmployeeIdEqualsCurrentId(device.employeeId, this.updateId) ? "danger" : "success"
  }

  checkDeviceStatusAndExecuteMethod(device: Device) {
    return this.checkEmployeeIdEqualsCurrentId(device.employeeId, this.updateId) ? this.deleteDeviceOnClick(device.serialNumber)
      : this.addDeviceOnClick(device.serialNumber);
  }

  checkDeviceStatusAndReturnButtonText(device: Device) {
    return this.checkEmployeeIdEqualsCurrentId(device.employeeId, this.updateId) ? "Delete Device"
      : "Add Device"
  }

  checkEmployeeIdEqualsCurrentId(employeeId: string, updateId: string): Boolean {
    return employeeId === updateId ? true : false;
  }
}


