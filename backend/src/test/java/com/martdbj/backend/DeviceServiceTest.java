package com.martdbj.backend;

import com.martdbj.backend.DTOs.DeviceDTO;
import com.martdbj.backend.mappers.DeviceMapper;
import com.martdbj.backend.models.Device;
import com.martdbj.backend.models.Employee;
import com.martdbj.backend.repository.DeviceRepository;
import com.martdbj.backend.repository.EmployeeRepository;
import com.martdbj.backend.service.DeviceServiceImplementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private DeviceMapper deviceMapper;

    @InjectMocks
    private DeviceServiceImplementation deviceService;

    @Test
    public void getDevicesFromRepoTest() {
        List<Device> mockDevices = Arrays.asList(
                new Device("Laptop", 1, "emp1"),
                new Device("Phone", 2, "emp2")
        );
        mockDevices.get(0).setSerialNumber("SN1");
        mockDevices.get(1).setSerialNumber("SN2");

        when(deviceRepository.findAll()).thenReturn(mockDevices);

        List<DeviceDTO> result = deviceService.getDevices();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("SN1", result.getFirst().serialNumber());
        verify(deviceRepository).findAll();
    }

    @Test
    public void getDeviceBySerialNumberFromRepoTest() {
        Device mockDevice = new Device("Laptop", 1, "emp1");
        mockDevice.setSerialNumber("SN1");

        when(deviceRepository.findById("SN1")).thenReturn(Optional.of(mockDevice));

        DeviceDTO result = deviceService.getDeviceBySerialNumber("SN1");

        Assertions.assertEquals("SN1", result.serialNumber());
        verify(deviceRepository).findById("SN1");
    }

    @Test
    public void saveDeviceInRepoTest() {
        Device mockDevice = new Device("Laptop", 1, "emp1");
        mockDevice.setSerialNumber("SN1");

        when(deviceRepository.save(mockDevice)).thenReturn(mockDevice);

        DeviceDTO result = deviceService.saveDevice(mockDevice);

        Assertions.assertEquals("SN1", result.serialNumber());
        verify(deviceRepository).save(mockDevice);
    }

    @Test
    public void updateDeviceFromRepoTest() {
        String serialNumber = "SN1";
        Device updatedDevice = new Device("NewModel", 2, "emp1");

        when(deviceRepository.existsById(serialNumber)).thenReturn(true);
        when(deviceRepository.save(updatedDevice)).thenReturn(updatedDevice);

        DeviceDTO result = deviceService.updateDevice(updatedDevice, serialNumber);

        Assertions.assertEquals(serialNumber, result.serialNumber());
        verify(deviceRepository).existsById(serialNumber);
        verify(deviceRepository).save(updatedDevice);
    }

    @Test
    public void deleteDeviceFromRepoTest() {
        String serialNumber = "SN1";
        String employeeId = "emp1";

        Device mockDevice = new Device("Laptop", 1, employeeId);
        mockDevice.setSerialNumber(serialNumber);

        List<String> deviceIds = new ArrayList<>(List.of(serialNumber, "SN2"));
        Employee mockEmployee = new Employee("TestEmployee", "test@gmail.com", "comp1", deviceIds);
        mockEmployee.setId(employeeId);

        when(deviceRepository.findById(serialNumber)).thenReturn(Optional.of(mockDevice));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockEmployee));

        deviceService.deleteDevice(serialNumber);

        Assertions.assertFalse(mockEmployee.getDevicesID().contains(serialNumber));
        verify(employeeRepository).save(mockEmployee);
        verify(deviceRepository).deleteById(serialNumber);
    }
}