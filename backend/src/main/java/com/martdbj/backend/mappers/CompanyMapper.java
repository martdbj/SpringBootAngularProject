package com.martdbj.backend.mappers;

import com.martdbj.backend.DTOs.CompanyDTO;
import com.martdbj.backend.models.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyDTO toDto(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getAddress(),
                company.getEmployeesId()
        );
    }
}
