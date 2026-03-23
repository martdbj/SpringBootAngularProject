package com.martdbj.backend.service;



import com.martdbj.backend.DTOs.EmployeeDTO;
import com.martdbj.backend.models.Employee;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getEmployees();
    EmployeeDTO getEmployeeById(String id);
    EmployeeDTO saveEmployee(Employee employee);
    EmployeeDTO updateEmployee(Employee employee, String id);
    void deleteEmployee(String id);
    void addDevice(String id, String serialNumber);
    void removeDevice(String id, String serialNumber);
}
