package com.martdbj.backend.web;


import com.martdbj.backend.DTOs.CompanyDTO;
import com.martdbj.backend.models.Company;
import com.martdbj.backend.service.CompanyService;
import com.martdbj.backend.service.CompanyServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/companies")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

    CompanyServiceImplementation companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        return new ResponseEntity<>(companyService.getCompanies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable String id) {
        return new ResponseEntity<>(companyService.getCompanyById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> saveCompany(@RequestBody Company company) {
        return new ResponseEntity<>(companyService.saveCompany(company), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody Company company, @PathVariable String id) {
        return new ResponseEntity<>(companyService.updateCompany(company, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCompany(@PathVariable String id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{companyId}/{employeeId}")
    public ResponseEntity<HttpStatus> addEmployeeToCompany(@PathVariable String companyId, @PathVariable String employeeId) {
        companyService.addEmployee(companyId, employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
