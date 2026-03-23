import { inject, Injectable, signal } from '@angular/core';
import { Device } from '../models/device';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DeviceService {

  public apiURL = "http://localhost:8080"
  private http = inject(HttpClient);

  getDeviceBySerialNumber(serlalNumber: string): Observable<Device> {
    return this.http.get<Device>(this.apiURL + "/devices/" + serlalNumber);
  }

  getDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.apiURL + "/devices");
  }

  addNewDevice(newDevice: Device): Observable<void> {
    return this.http.post<void>(this.apiURL + "/devices", newDevice);
  }

  deleteDeviceById(serialNumber: string): Observable<void> {
    return this.http.delete<void>(this.apiURL + "/devices/" + serialNumber);
  }

  updateDeviceBySerialNumber(serialNumber: string, updatedDevice: Device): Observable<void> {
    return this.http.put<void>(this.apiURL + "/devices/" + serialNumber, updatedDevice);
  }
}