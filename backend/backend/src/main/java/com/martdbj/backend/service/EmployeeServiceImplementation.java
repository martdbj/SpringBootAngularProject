package com.martdbj.backend.service;

import com.martdbj.backend.DTOs.EmployeeDTO;
import com.martdbj.backend.exception.EntityNotFoundException;
import com.martdbj.backend.mappers.EmployeeMapper;
import com.martdbj.backend.models.Device;
import com.martdbj.backend.models.Employee;
import com.martdbj.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@AllArgsConstructor
@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private final MongoTemplate mongoTemplate;
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDTO> getEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(String id) {
        return employeeMapper.toDTO(employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Employee.class)));
    }

    @Override
    public EmployeeDTO saveEmployee(Employee employee) {
        if (employee.getId().isBlank()) employee.setId(null);
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO updateEmployee(Employee employee, String id) {
        Employee employee1 = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Employee.class));

        // Checks if the frontend sends an older employee object while the backend is still updating the information
        // It avoids the rewriting of the employee devices
        if (employee.getDevicesID().isEmpty() && !employee1.getDevicesID().isEmpty()) {
            employee.setDevicesID(employee1.getDevicesID());
        }

        employee.setId(id);
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Employee.class));

        if (employee.getDevicesID() != null) {
            employee.getDevicesID().forEach(serialNumber -> removeDevice(id, serialNumber));
        }
        employeeRepository.delete(employee);
    }

    @Override
    public void addDevice(String id, String serialNumber) {
        // Not letting duplicates
        Query queryCheckDevice = new Query(where("_id").is(new ObjectId(id)).and("devicesID").is(serialNumber));
        boolean deviceExist = mongoTemplate.exists(queryCheckDevice, Employee.class);

        if (!deviceExist) {
            Query queryEmployee = new Query(where("_id").is(new ObjectId(id)));
            Update updateEmployee = new Update().push("devicesID", serialNumber);
            mongoTemplate.updateFirst(queryEmployee, updateEmployee, "Employees");

            Query queryDevice = Query.query(where("_id").is(serialNumber));
            Update updateDevice = new Update().set("employeeId", id);
            mongoTemplate.updateFirst(queryDevice, updateDevice, Device.class);
        }
    }

    @Override
    public void removeDevice(String id, String serialNumber) {
        Query queryEmployee = new Query(where("_id").is(new ObjectId(id)));
        Update updateEmployee = new Update().pull("devicesID", serialNumber);
        mongoTemplate.updateFirst(queryEmployee, updateEmployee, Employee.class);

        Query queryDevice = Query.query(where("_id").is(serialNumber));
        Update updateDevice = new Update().set("employeeId", "");
        mongoTemplate.updateFirst(queryDevice, updateDevice, Device.class);
    }
}
