import { TestBed } from '@angular/core/testing';

import { CompanyService } from './company.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { Company } from '../models/company';
import { provideHttpClient } from '@angular/common/http';
import { Employee } from '../models/employee';

const mockCompany: Company = {
  id: '1',
  name: 'Cyraco',
  employees: []
};

const mockCompanies = [mockCompany];

describe('CompanyService', () => {
  let service: CompanyService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        CompanyService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    });

    service = TestBed.inject(CompanyService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('(GET) should get all companies', () => {
    service.getCompanies().subscribe(companies => {
      expect(companies).toBeTruthy();
      expect(companies.length).toBe(1);
      expect(companies[0].name).toBe(mockCompany.name);
    });
    const mockReq = httpMock.expectOne(service.apiURL + "/companies");
    expect(mockReq.request.method).toBe("GET");
    mockReq.flush(mockCompanies);
  });

  it('(GET) should get company by id', () => {
    service.getCompanyById('1').subscribe(company => {
      expect(company).toBeTruthy();
      expect(company.name).toBe(mockCompany.name);
    });
    const mockReq = httpMock.expectOne(`${service.apiURL}/companies/${mockCompany.id}`);
    expect(mockReq.request.method).toBe("GET");
    mockReq.flush(mockCompany);
  });

  it('(POST) should add a new company', () => {
    const newCompany: Company = { ...mockCompany, id: '2', name: 'New Company' };
    service.addNewCompany(newCompany).subscribe();
    const mockReq = httpMock.expectOne(`${service.apiURL}/companies`);
    expect(mockReq.request.method).toBe("POST");
    expect(mockReq.request.body).toEqual(newCompany);
    mockReq.flush(null);
  });

  it('(DELETE) should delete company by id', () => {
    service.deleteCompanyById('1').subscribe();
    const mockReq = httpMock.expectOne(`${service.apiURL}/companies/1`);
    expect(mockReq.request.method).toBe("DELETE");
    mockReq.flush(null);
  });

  it('(PUT) should update company by id', () => {
    const updatedCompany = { ...mockCompany, name: 'Updated Name' };
    service.updateCompanyById('1', updatedCompany).subscribe();
    const mockReq = httpMock.expectOne(`${service.apiURL}/companies/1`);
    expect(mockReq.request.method).toBe("PUT");
    expect(mockReq.request.body).toEqual(updatedCompany);
    mockReq.flush(null);
  });

  it('(POST) should add employee to company', () => {
    const companyId = '1';
    const newEmployee: Employee = {
      id: '10',
      name: 'Pep',
      email: 'pep@gmail.com',
      companyId: '1',
      devicesId: []
    };

    service.addEmployeeToCompany(companyId, newEmployee).subscribe();

    const mockReq = httpMock.expectOne(`${service.apiURL}/companies/${companyId}/addEmployee`);
    expect(mockReq.request.method).toBe("POST");
    expect(mockReq.request.body).toEqual(newEmployee);
    mockReq.flush(null);
  });
});