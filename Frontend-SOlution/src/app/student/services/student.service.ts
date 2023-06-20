import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Student } from "../models/student";

@Injectable({
  providedIn: "root",
})
export class StudentService {
  private baseUrl = "http://localhost:8081/collegemanagement/students";

  constructor(private http: HttpClient) {}

  getAllStudents(): any {
    return this.http.get<Student[]>(this.baseUrl);
  }

  getStudentById(id: number): any {
    return this.http.get<Student>(`${this.baseUrl}/${id}`);
  }

  createStudent(student: Student): any {
    const studentData = {
      name: student.name,
      departmentId: student.department.id,
    };

    return this.http.post<Student>(this.baseUrl, studentData);
  }

  updateStudent(student: Student): any {
    const url = `${this.baseUrl}/${student.id}`;
    const studentData = {
      name: student.name,
      departmentId: student.department.id,
    };

    return this.http.put<Student>(url, studentData);
  }

  deleteStudent(id: number): any {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  searchStudents(name: string): any {
    return this.http.get<Student[]>(`${this.baseUrl}/search?name=${name}`);
  }
}
