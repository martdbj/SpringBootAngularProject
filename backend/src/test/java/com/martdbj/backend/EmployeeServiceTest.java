package com.martdbj.backend;

import com.martdbj.backend.DTOs.EmployeeDTO;
import com.martdbj.backend.mappers.EmployeeMapper;
import com.martdbj.backend.models.Device;
import com.martdbj.backend.models.Employee;
import com.martdbj.backend.repository.DeviceRepository;
import com.martdbj.backend.repository.EmployeeRepository;
import com.martdbj.backend.service.EmployeeServiceImplementation;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Spy
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImplementation employeeService;

    @Test
    public void getEmployeesFromRepoTest() {
        List<Employee> mockEmployees = Arrays.asList(
                new Employee("TestEmployee1", "testEmployee1@gmail.com", "", new ArrayList<>()),
                new Employee("TestEmployee2", "testEmployee2@gmail.com", "", new ArrayList<>())
        );
        
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        List<EmployeeDTO> result = employeeService.getEmployees();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("TestEmployee1", result.get(0).name());
        Assertions.assertEquals("testEmployee1@gmail.com", result.get(0).email());
        Assertions.assertEquals("TestEmployee2", result.get(1).name());
        verify(employeeRepository).findAll();
    }

    @Test
    public void getEmployeeByIdFromRepoTest() {
        Employee mockEmployee = new Employee("TestEmployee1", "testEmployee1@gmail.com", "", new ArrayList<>());
        mockEmployee.setId("Naranjas");

        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));

        EmployeeDTO result = employeeService.getEmployeeById(mockEmployee.getId());

        Assertions.assertEquals("TestEmployee1", result.name());
        Assertions.assertEquals("Naranjas", result.id());
        verify(employeeRepository).findById(mockEmployee.getId());
    }

    @Test
    public void saveEmployeeInRepoTest() {
        Employee mockEmployee = new Employee("TestEmployee1", "testEmployee1@gmail.com", "", new ArrayList<>());
        mockEmployee.setId("Naranjas");

        when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);

        EmployeeDTO result = employeeService.saveEmployee(mockEmployee);

        Assertions.assertEquals("TestEmployee1", result.name());
        Assertions.assertEquals("Naranjas", result.id());
        verify(employeeRepository).save(mockEmployee);
    }

    @Test
    public void updateEmployeeFromRepoTest() {
        Employee mockEmployee = new Employee("NonUpdatedEmployee", "nonupdatedemployee@gmail.com", "", new ArrayList<>());
        mockEmployee.setId("Naranjas");

        Employee updatedEmployee = new Employee("UpdatedEmployee", "updatedemployee@gmail.com", "company id", new ArrayList<>());

        when(employeeRepository.existsById(mockEmployee.getId())).thenReturn(true);
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        EmployeeDTO result = employeeService.updateEmployee(updatedEmployee, mockEmployee.getId());

        Assertions.assertEquals(mockEmployee.getId(), result.id());
        Assertions.assertEquals("company id", result.companyId());
        verify(employeeRepository).existsById(mockEmployee.getId());
        verify(employeeRepository).save(updatedEmployee);
    }

    @Test
    public void deleteEmployeeFromRepoTest() {
        List<String> deviceSerialNumbers = List.of("dev1", "dev2");
        Employee mockEmployee = new Employee("NonUpdatedEmployee", "nonupdatedemployee@gmail.com", "", deviceSerialNumbers);
        mockEmployee.setId("Naranjas");

        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));

        employeeService.deleteEmployee(mockEmployee.getId());

        verify(deviceRepository).deleteById("dev1");
        verify(deviceRepository).deleteById("dev2");
        verify(employeeRepository).delete(mockEmployee);
    }

    @Test
    public void addDeviceToEmployeeInRepoTest() {
        String deviceSerialNumber = "dev1";
        Device mockDevice = new Device("DeviceTest", 1, "");
        mockDevice.setSerialNumber(deviceSerialNumber);

        Employee mockEmployee = new Employee("NonUpdatedEmployee", "nonupdatedemployee@gmail.com", "", new ArrayList<>());
        mockEmployee.setId("Naranjas");

        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));
        when(employeeRepository.existsById(mockEmployee.getId())).thenReturn(true);
        when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);
        when(deviceRepository.findById(mockDevice.getSerialNumber())).thenReturn(Optional.of(mockDevice));
        when(deviceRepository.save(mockDevice)).thenReturn(mockDevice);

        employeeService.addDevice(mockEmployee.getId(), mockDevice.getSerialNumber());

        Assertions.assertTrue(mockEmployee.getDevicesID().contains(mockDevice.getSerialNumber()));
        Assertions.assertEquals(mockEmployee.getId(), mockDevice.getEmployeeId());
        verify(deviceRepository).save(mockDevice);
        verify(employeeRepository).save(mockEmployee);
    }

    @Test
    public void deleteDeviceFromEmployeeInRepo() {
        List<String> devicesSerialNumber = new ArrayList<>(List.of("dev1"));
        Device mockDevice = new Device("DeviceTest", 1, "");
        mockDevice.setSerialNumber(devicesSerialNumber.getFirst());

        Employee mockEmployee = new Employee("NonUpdatedEmployee", "nonupdatedemployee@gmail.com", "", devicesSerialNumber);
        mockEmployee.setId("Naranjas");

        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));
        when(employeeRepository.existsById(mockEmployee.getId())).thenReturn(true);
        when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);
        when(deviceRepository.findById(mockDevice.getSerialNumber())).thenReturn(Optional.of(mockDevice));
        when(deviceRepository.save(mockDevice)).thenReturn(mockDevice);

        employeeService.removeDevice(mockEmployee.getId(), mockDevice.getSerialNumber());

        Assertions.assertFalse(mockEmployee.getDevicesID().contains(mockDevice.getSerialNumber()));
        verify(deviceRepository).save(mockDevice);
        verify(employeeRepository).save(mockEmployee);

    }
}