import { Component, inject, signal } from '@angular/core';
import { Device } from '../models/device';
import { form, FormField, maxLength, pattern, required, submit } from '@angular/forms/signals';
import { DeviceService } from '../services/device.service';
import { EmployeeService } from '../services/employee.service';
import { Employee } from '../models/employee';

@Component({
  selector: 'app-device-management',
  imports: [FormField],
  templateUrl: './device-management.html',
  styleUrl: './device-management.css',
})
export class DeviceManagement {

  employeeService = inject(EmployeeService);
  deviceService = inject(DeviceService);

  devices = signal<Device[]>([])
  employees = signal<Employee[]>([]);

  loadDevicesAndEmployees(): void {
    this.deviceService.getDevices().subscribe(data => {
      this.devices.set(data);
    });

    this.employeeService.getEmployees().subscribe(data => {
      this.employees.set(data);
    })
  }

  ngOnInit(): void {
    this.loadDevicesAndEmployees();
  }

  deviceModel = signal<Device>({
    serialNumber: '',
    name: '',
    type: NaN,
    employeeId: ""
  })

  cleanDeviceModel = {
    serialNumber: '',
    name: '',
    type: NaN,
    employeeId: ""
  }


  addForm = form(this.deviceModel, (fieldPath) => {
    maxLength(fieldPath.name, 255, { message: 'Name cannot be longer than 255 characters'});
    pattern(fieldPath.name, /^[a-zA-Z0-9 ]+$/, { message: 'Name cannot contain special characters'});
    required(fieldPath.serialNumber, { message: 'Serial Number is required' });
    required(fieldPath.name, { message: 'Description is required' });
    required(fieldPath.type, { message: 'Type is required' });
  })

  updateForm = form(this.deviceModel, (fieldPath) => {
    maxLength(fieldPath.name, 255, { message: 'Name cannot be longer than 255 characters'});
    pattern(fieldPath.name, /^[a-zA-Z0-9 ]+$/, { message: 'Name cannot contain special characters'});
    required(fieldPath.serialNumber, { message: 'Serial Number is required' });
    required(fieldPath.name, { message: 'Description is required' });
    required(fieldPath.type, { message: 'Type is required' });
  })

  addOnSubmit(event: Event) {
    event.preventDefault();
    submit(this.addForm, async () => {
      const deviceSerialNumber = this.addForm.serialNumber().value();
      const deviceName = this.addForm.name().value();
      const deviceType = this.addForm.type().value();
      const deviceOwner = this.addForm.employeeId().value();

      let newDevice: Device = {
        serialNumber: deviceSerialNumber,
        name: deviceName,
        type: deviceType,
        employeeId: deviceOwner
      }

      this.deviceService.addNewDevice(newDevice).subscribe(() => {
        this.loadDevicesAndEmployees();
        this.deviceModel.set(this.cleanDeviceModel);

      });

    })
  }

  deleteOnSubmit(serialNumber: string) {
    this.deviceService.deleteDeviceById(serialNumber).subscribe(() => {
      this.loadDevicesAndEmployees();
    });
  }

  updateSerialNumber = "";
  updatePassSerialNumberAndFillForm(serialNumber: string) {
    this.updateSerialNumber = serialNumber;

    this.deviceService.getDeviceBySerialNumber(serialNumber).subscribe(device => {
      if (device) {
        this.deviceModel.set(device);
      }
    });
  }

  updateOnSubmit(event: Event) {
    event.preventDefault()

    if (this.updateSerialNumber) {
      this.deviceService.getDeviceBySerialNumber(this.updateSerialNumber).subscribe(device => {
        if (device) {
          if (this.addForm.serialNumber().value()) device.serialNumber = this.addForm.serialNumber().value();
          if (this.addForm.name().value()) device.name = this.addForm.name().value();
          if (this.addForm.type().value()) device.type = this.addForm.type().value();
          if (this.addForm.employeeId().value()) device.employeeId = this.addForm.employeeId().value();


          this.deviceService.updateDeviceBySerialNumber(this.updateSerialNumber, device).subscribe(() => {
            this.updateSerialNumber = "";
            this.loadDevicesAndEmployees();
            this.deviceModel.set(this.cleanDeviceModel);
          })
        }
      });
    }
  }

  closeUpdate() {
    this.deviceModel.set(this.cleanDeviceModel);
  }

  getEmployeeName(id: string) {
    const employee = this.employees().find(employee => employee.id === id);
    return employee ? `${employee.name} | ${employee.email}` : 'Employee not found';
  }
}
