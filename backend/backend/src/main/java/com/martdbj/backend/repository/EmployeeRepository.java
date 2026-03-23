package com.martdbj.backend.repository;

import com.martdbj.backend.models.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    List<Employee> findAllByCompanyId(String companyId);


}
