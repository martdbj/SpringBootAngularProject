import { Component, inject, signal } from '@angular/core';
import { Company } from '../models/company';
import { email, form, FormField, maxLength, minLength, pattern, required, submit } from '@angular/forms/signals';
import { EmployeeService } from '../services/employee.service';
import { CompanyService } from '../services/company.service';
import { v4 as uuid } from 'uuid';
import { Employee } from '../models/employee';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Device } from '../models/device';

@Component({
  selector: 'app-company-management',
  imports: [FormField],
  templateUrl: './company-management.html',
  styleUrl: './company-management.css',
})
export class CompanyManagement {

  employeeService = inject(EmployeeService);
  companyService = inject(CompanyService);

  employees = signal<Employee[]>([]);
  companies = signal<Company[]>([]);

  loadCompanies(): void {
    this.companyService.getCompanies().subscribe(data => {
      this.companies.set(data);
    });
  }

  ngOnInit(): void {
    this.loadCompanies();
  }


  companyModel = signal<Company>({
    id: "",
    name: "",
    employees: []
  })

  cleanCompanyModel: Company = {
    id: "",
    name: "",
    employees: []
  }

  addForm = form(this.companyModel, (fieldPath) => {
    required(fieldPath.name, { message: 'Name is required' });
  })

  updateForm = form(this.companyModel, (fieldPath) => {
    required(fieldPath.name, { message: 'Name is required' });
  })

  addOnSubmit(event: Event) {
    event.preventDefault();
    submit(this.addForm, async () => {
      let companyName = this.addForm.name().value();
      let newCompany: Company = {
        id: "",
        name: companyName,
        employees: []
      }

      this.companyService.addNewCompany(newCompany).subscribe(() => {
        this.loadCompanies();
        this.companyModel.set(this.cleanCompanyModel)
      })
    })
  }

  deleteOnSubmit(id: string) {
    this.companyService.deleteCompanyById(id).subscribe(() => {
      this.loadCompanies();
    });
  }

  companyId = "";
  updatePassIdAndFillForm(id: string) {
    this.companyId = id;

    this.companyService.getCompanyById(id).subscribe(company => {
      if (company) {
        this.companyModel.set(company);
      }
    });
  }

  updateOnSubmit(event: Event) {
    event.preventDefault();

    if (this.companyId) {
      this.companyService.getCompanyById(this.companyId).subscribe(company => {
        if (company) {
          if (this.updateForm.name().value()) company.name = this.updateForm.name().value();

          this.companyService.updateCompanyById(this.companyId, company).subscribe(() => {
            this.companyId = "";
            this.loadCompanies()
            this.companyModel.set(this.cleanCompanyModel)
          });
        }
      });
    }
  }

  closeUpdate() {
    this.companyModel.set(this.cleanCompanyModel);
  }

  
}
