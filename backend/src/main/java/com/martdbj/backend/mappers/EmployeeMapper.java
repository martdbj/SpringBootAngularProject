package com.martdbj.backend.mappers;

import com.martdbj.backend.DTOs.EmployeeDTO;
import com.martdbj.backend.models.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getCompanyId(),
                employee.getDevicesID()
        );
    }
}
