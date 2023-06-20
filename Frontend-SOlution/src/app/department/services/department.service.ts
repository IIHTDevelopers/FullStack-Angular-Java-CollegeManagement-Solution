import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { Department } from "../models/department";

@Injectable({
  providedIn: "root",
})
export class DepartmentService {
  private baseUrl = "http://localhost:8081/collegemanagement/departments";

  constructor(private http: HttpClient) {}

  getAllDepartments(): any {
    return this.http.get<Department[]>(this.baseUrl);
  }

  getDepartmentById(id: number): any {
    return this.http.get<Department>(`${this.baseUrl}/${id}`);
  }

  createDepartment(department: Department): any {
    return this.http.post<Department>(this.baseUrl, department);
  }

  updateDepartment(id: number, department: Department): any {
    return this.http.put<Department>(`${this.baseUrl}/${id}`, department);
  }

  deleteDepartment(id: number): any {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  searchDepartmentsByName(name: string): any {
    const params = new HttpParams().set("name", name);
    return this.http.get<Department[]>(`${this.baseUrl}/search`, { params });
  }
}
