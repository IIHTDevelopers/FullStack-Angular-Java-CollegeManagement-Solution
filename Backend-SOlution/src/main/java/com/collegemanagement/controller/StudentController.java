package com.collegemanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collegemanagement.dto.StudentDTO;
import com.collegemanagement.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
	private final StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<List<StudentDTO>> getAllStudents() {
		List<StudentDTO> students = studentService.getAllStudents();
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
		StudentDTO student = studentService.getStudentById(id);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
		StudentDTO createdStudent = studentService.createStudent(studentDTO);
		return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
		StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
		return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteStudent(@PathVariable Long id) {
		Boolean student = studentService.deleteStudent(id);
		return new ResponseEntity<Boolean>(student, HttpStatus.FOUND);
	}

	@GetMapping("/search")
	public ResponseEntity<List<StudentDTO>> searchStudentsByName(@RequestParam("name") String name) {
		List<StudentDTO> students = studentService.searchStudentsByName(name);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}
}
