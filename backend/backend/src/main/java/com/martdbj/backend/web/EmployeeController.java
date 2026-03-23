package com.martdbj.backend.web;

import com.martdbj.backend.DTOs.DeviceDTO;
import com.martdbj.backend.DTOs.EmployeeDTO;
import com.martdbj.backend.mappers.EmployeeMapper;
import com.martdbj.backend.models.Employee;
import com.martdbj.backend.service.EmployeeServiceImplementation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    EmployeeServiceImplementation employeeService;
    EmployeeMapper employeeMapper;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        return new ResponseEntity<>(employeeService.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable String id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody @Valid Employee employee) {
        EmployeeDTO employeeDTO = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody @Valid Employee employee, @PathVariable String id) {
        EmployeeDTO employeeDTO = employeeService.updateEmployee(employee, id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/addDevice/{serialNumber}")
    public ResponseEntity<HttpStatus> addDeviceToEmployee(@PathVariable String id, @PathVariable String serialNumber) {
        employeeService.addDevice(id, serialNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/deleteDevice/{serialNumber}")
    public ResponseEntity<HttpStatus> removeDeviceToEmployee(@PathVariable String id, @PathVariable String serialNumber) {
        employeeService.removeDevice(id, serialNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
