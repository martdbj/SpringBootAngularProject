package com.martdbj.backend.service;

import com.martdbj.backend.DTOs.CompanyDTO;
import com.martdbj.backend.exception.EntityNotFoundException;
import com.martdbj.backend.mappers.CompanyMapper;
import com.martdbj.backend.models.Company;
import com.martdbj.backend.models.Employee;
import com.martdbj.backend.repository.CompanyRespository;
import com.martdbj.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@AllArgsConstructor
@Service
public class CompanyServiceImplementation implements CompanyService {

    private final MongoTemplate mongoTemplate;
    private final EmployeeService employeeService;
    CompanyRespository companyRepository;
    EmployeeRepository employeeRepository;
    CompanyMapper companyMapper;

    @Override
    public List<CompanyDTO> getCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(companyMapper::toDto)
                .toList();
    }

    @Override
    public CompanyDTO getCompanyById(String id) {
        return companyMapper.toDto(companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Company.class)));
    }

    @Override
    public CompanyDTO saveCompany(Company company) {
        if (company.getId().isBlank()) company.setId(null);
        return companyMapper.toDto(companyRepository.save(company));
    }

    @Override
    public CompanyDTO updateCompany(Company company, String id) {
        if (!companyRepository.existsById(id)) throw  new EntityNotFoundException(id, Company.class);
        company.setId(id);
        return companyMapper.toDto(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(String id) {
        if (!companyRepository.existsById(id)) throw  new EntityNotFoundException(id, Company.class);
        List<Employee> employees = employeeRepository.findAllByCompanyId(id);
        for (Employee employee : employees) {
            if (employee.getDevicesID() != null) {
                employee.getDevicesID().forEach(serialNumber -> employeeService.removeDevice(id, serialNumber));
            }
        }

        employeeRepository.deleteAll(employees);
        companyRepository.deleteById(id);
    }

    @Override
    public void addEmployee(String companyId, String employeeId) {
        Query queryEmployee = new Query(where("_id").is(new ObjectId(employeeId)));
        Update updateEmployee = new Update().push("companyId", companyId);
        mongoTemplate.updateFirst(queryEmployee, updateEmployee, "Employees");
    }
}
