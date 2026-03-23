package com.martdbj.backend.repository;

import com.martdbj.backend.models.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRespository extends MongoRepository<Company, String> {
}
