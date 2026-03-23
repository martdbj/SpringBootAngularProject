import { inject, Injectable, signal } from '@angular/core';
import { Company } from '../models/company';
import { Employee } from '../models/employee';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {

  public apiURL = "http://localhost:8080"
  private http = inject(HttpClient);

  getCompanyById(id: string): Observable<Company> {
    return this.http.get<Company>(this.apiURL + "/companies/" + id);
  }

  getCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(this.apiURL + "/companies");
  }

  addNewCompany(newCompany: Company): Observable<void> {
    return this.http.post<void>(this.apiURL + "/companies", newCompany);
  }

  deleteCompanyById(id: string): Observable<void> {
    return this.http.delete<void>(this.apiURL + "/companies/"+id);
  }

  updateCompanyById(id: string, updatedCompany: Company): Observable<void> {
    return this.http.put<void>(this.apiURL + "/companies/"+id, updatedCompany)
  }

  addEmployeeToCompany(companyId: string, employee: Employee) {
    return this.http.post<void>(this.apiURL + "/companies/"+companyId + "/addEmployee", employee)
  }
}
