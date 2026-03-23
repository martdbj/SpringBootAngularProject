package com.martdbj.backend;

import com.martdbj.backend.models.Company;
import com.martdbj.backend.models.Device;
import com.martdbj.backend.models.Employee;
import com.martdbj.backend.repository.CompanyRespository;
import com.martdbj.backend.repository.DeviceRepository;
import com.martdbj.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {

	CompanyRespository companyRespository;

	@Override
	public void run(String... args) throws Exception {
		/* Company[] companies = new Company[] {
				new Company("Cyraco", "Calle las algas", new ArrayList<>()),
				new Company("Nvidia", "北京", new ArrayList<>())
		};
        companyRespository.saveAll(Arrays.asList(companies)); */
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
