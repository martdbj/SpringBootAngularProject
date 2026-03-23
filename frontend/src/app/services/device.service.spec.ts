import { TestBed } from '@angular/core/testing';

import { DeviceService } from './device.service';
import { Device } from '../models/device';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

const mockDevice: Device = {
  serialNumber: 'a1',
  name: 'Laptop',
  type: 1, 
  employeeId: '1'
};

const mockDevices: Device[] = [mockDevice];

describe('DeviceService', () => {
  
  let service: DeviceService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        DeviceService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    });

    service = TestBed.inject(DeviceService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('(GET) should get all devices', () => {
    service.getDevices().subscribe(devices => {
      expect(devices).toBeTruthy();
      expect(devices.length).toBe(1);
      expect(devices[0].serialNumber).toBe(mockDevice.serialNumber);
    });

    const mockReq = httpMock.expectOne(`${service.apiURL}/devices`);
    expect(mockReq.request.method).toBe("GET");
    mockReq.flush(mockDevices);
  });

  it('(GET) should get device by serial number', () => {
    service.getDeviceBySerialNumber('SN123').subscribe(device => {
      expect(device).toBeTruthy();
      expect(device.serialNumber).toBe(mockDevice.serialNumber);
    });

    const mockReq = httpMock.expectOne(`${service.apiURL}/devices/SN123`);
    expect(mockReq.request.method).toBe("GET");
    mockReq.flush(mockDevice);
  });

  it('(POST) should add a new device', () => {
    const newDevice: Device = { ...mockDevice, serialNumber: 'SN456', name: 'Teclado' };

    service.addNewDevice(newDevice).subscribe();

    const mockReq = httpMock.expectOne(`${service.apiURL}/devices`);
    expect(mockReq.request.method).toBe("POST");
    expect(mockReq.request.body).toEqual(newDevice);
    mockReq.flush(null);
  });

  it('(PUT) should update device by serial number', () => {
    const updatedDevice = { ...mockDevice, name: 'Laptop Pro' };

    service.updateDeviceBySerialNumber('SN123', updatedDevice).subscribe();

    const mockReq = httpMock.expectOne(`${service.apiURL}/devices/SN123`);
    expect(mockReq.request.method).toBe("PUT");
    expect(mockReq.request.body).toEqual(updatedDevice);
    mockReq.flush(null);
  });

  it('(DELETE) should delete device by serial number', () => {
    service.deleteDeviceById('SN123').subscribe();

    const mockReq = httpMock.expectOne(`${service.apiURL}/devices/SN123`);
    expect(mockReq.request.method).toBe("DELETE");
    mockReq.flush(null);
  });
});
