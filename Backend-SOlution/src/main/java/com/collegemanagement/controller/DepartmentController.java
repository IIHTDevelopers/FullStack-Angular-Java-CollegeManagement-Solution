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

import com.collegemanagement.dto.DepartmentDTO;
import com.collegemanagement.entity.Department;
import com.collegemanagement.exception.ResourceNotFoundException;
import com.collegemanagement.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
	private final DepartmentService departmentService;

	@Autowired
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
		List<DepartmentDTO> departments = departmentService.getAllDepartments();
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
		DepartmentDTO department = departmentService.getDepartmentById(id);

		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
		DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);
		return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id,
			@Valid @RequestBody DepartmentDTO departmentDTO) {
		DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);
		return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long id) {
		Boolean department = departmentService.deleteDepartment(id);
		return new ResponseEntity<Boolean>(department, HttpStatus.ACCEPTED);
	}

	@GetMapping("/search")
	public ResponseEntity<List<DepartmentDTO>> searchDepartmentsByName(@RequestParam("name") String name) {
		List<DepartmentDTO> departments = departmentService.searchDepartmentsByName(name);
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}
}
