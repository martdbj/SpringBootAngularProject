package com.martdbj.backend;

import com.martdbj.backend.DTOs.CompanyDTO;
import com.martdbj.backend.mappers.CompanyMapper;
import com.martdbj.backend.models.Company;
import com.martdbj.backend.models.Employee;
import com.martdbj.backend.repository.CompanyRespository;
import com.martdbj.backend.repository.EmployeeRepository;
import com.martdbj.backend.service.CompanyServiceImplementation;

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
public class CompanyServiceTest {

    @Mock
    private CompanyRespository companyRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyServiceImplementation companyService;

    @Test
    public void getCompaniesFromRepoTest() {
        List<Company> mockCompanies = Arrays.asList(
                new Company("TestCompany1", "Address 1", new ArrayList<>()),
                new Company("TestCompany2", "Address 2", new ArrayList<>())
        );

        when(companyRepository.findAll()).thenReturn(mockCompanies);

        List<CompanyDTO> result = companyService.getCompanies();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("TestCompany1", result.get(0).name());
        Assertions.assertEquals("TestCompany2", result.get(1).name());
        verify(companyRepository).findAll();
    }

    @Test
    public void getCompanyByIdFromRepoTest() {
        Company mockCompany = new Company("TestCompany1", "Address 1", new ArrayList<>());
        mockCompany.setId("Naranjas");

        when(companyRepository.findById(mockCompany.getId())).thenReturn(Optional.of(mockCompany));

        CompanyDTO result = companyService.getCompanyById(mockCompany.getId());

        Assertions.assertEquals("TestCompany1", result.name());
        Assertions.assertEquals("Naranjas", result.id());
        verify(companyRepository).findById(mockCompany.getId());
    }

    @Test
    public void saveCompanyInRepoTest() {
        Company mockCompany = new Company("TestCompany1", "Address 1", new ArrayList<>());
        mockCompany.setId("Naranjas");

        when(companyRepository.save(mockCompany)).thenReturn(mockCompany);

        CompanyDTO result = companyService.saveCompany(mockCompany);

        Assertions.assertEquals("TestCompany1", result.name());
        Assertions.assertEquals("Naranjas", result.id());
        verify(companyRepository).save(mockCompany);
    }

    @Test
    public void updateCompanyFromRepoTest() {
        Company mockCompany = new Company("NonUpdatedCompany", "Old Address", new ArrayList<>());
        mockCompany.setId("Naranjas");

        Company updatedCompany = new Company("UpdatedCompany", "New Address", new ArrayList<>());

        when(companyRepository.existsById(mockCompany.getId())).thenReturn(true);
        when(companyRepository.save(updatedCompany)).thenReturn(updatedCompany);

        CompanyDTO result = companyService.updateCompany(updatedCompany, mockCompany.getId());

        Assertions.assertEquals(mockCompany.getId(), result.id());
        Assertions.assertEquals("UpdatedCompany", result.name());
        verify(companyRepository).existsById(mockCompany.getId());
        verify(companyRepository).save(updatedCompany);
    }

    @Test
    public void deleteCompanyFromRepoTest() {
        List<Employee> mockEmployees = new ArrayList<>();
        Company mockCompany = new Company("NonUpdatedCompany", "Address", new ArrayList<>());
        mockCompany.setId("Naranjas");

        when(companyRepository.existsById(mockCompany.getId())).thenReturn(true);
        when(employeeRepository.findAllByCompanyId(mockCompany.getId())).thenReturn(mockEmployees);

        companyService.deleteCompany(mockCompany.getId());

        verify(employeeRepository).findAllByCompanyId(mockCompany.getId());
        verify(employeeRepository).deleteAll(mockEmployees);
        verify(companyRepository).deleteById(mockCompany.getId());
    }

    @Test
    public void addEmployeeToCompanyInRepoTest() {
        String employeeId = "emp1";
        Employee mockEmployee = new Employee("EmployeeTest", "test@gmail.com", "", new ArrayList<>());
        mockEmployee.setId(employeeId);

        Company mockCompany = new Company("NonUpdatedCompany", "Address", new ArrayList<>());
        mockCompany.setId("Naranjas");

        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));
        when(companyRepository.findById(mockCompany.getId())).thenReturn(Optional.of(mockCompany));
        when(companyRepository.existsById(mockCompany.getId())).thenReturn(true);
        when(companyRepository.save(mockCompany)).thenReturn(mockCompany);
        when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);

        companyService.addEmployee(mockCompany.getId(), mockEmployee.getId());

        Assertions.assertTrue(mockCompany.getEmployeesId().contains(mockEmployee.getId()));
        Assertions.assertEquals(mockCompany.getId(), mockEmployee.getCompanyId());
        verify(employeeRepository).save(mockEmployee);
        verify(companyRepository).save(mockCompany);
    }
}