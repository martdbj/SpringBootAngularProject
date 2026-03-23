package com.martdbj.backend.service;

import com.martdbj.backend.DTOs.CompanyDTO;
import com.martdbj.backend.models.Company;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> getCompanies();
    CompanyDTO getCompanyById(String id);
    CompanyDTO saveCompany(Company company);
    CompanyDTO updateCompany(Company company, String id);
    void deleteCompany(String id);
    void addEmployee(String companyId, String employeeId);
}
