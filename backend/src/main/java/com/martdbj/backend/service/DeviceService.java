package com.martdbj.backend.service;

import com.martdbj.backend.DTOs.DeviceDTO;
import com.martdbj.backend.models.Device;

import java.util.List;

public interface DeviceService {

    List<DeviceDTO> getDevices();
    DeviceDTO getDeviceBySerialNumber(String serialNumber);
    DeviceDTO saveDevice(Device device);
    DeviceDTO updateDevice(Device device, String serialNumber);
    void deleteDevice(String serialNumber);
}
