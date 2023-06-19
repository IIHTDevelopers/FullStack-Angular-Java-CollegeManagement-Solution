package com.collegemanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegemanagement.dto.DepartmentDTO;
import com.collegemanagement.entity.Department;
import com.collegemanagement.exception.DepartmentNotFoundException;
import com.collegemanagement.repo.DepartmentRepository;

@Service
public class DepartmentService {
	private final DepartmentRepository departmentRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	public List<DepartmentDTO> getAllDepartments() {
		List<DepartmentDTO> dtos = new ArrayList<>();
		List<Department> list = departmentRepository.findAll();
		modelMapper.map(list, dtos);
		return dtos;
	}

	public DepartmentDTO getDepartmentById(Long id) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException("Department with Id - " + id + " not found!"));

		return modelMapper.map(department, DepartmentDTO.class);

	}

	public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
		Department department = modelMapper.map(departmentDTO, Department.class);
		Department department2 = departmentRepository.save(department);
		return modelMapper.map(department2, DepartmentDTO.class);
	}

	public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException("Department with Id - " + id + " not found!"));
		if (department != null) {
			Department department1 = modelMapper.map(departmentDTO, Department.class);
			Department department2 = departmentRepository.save(department1);
			return modelMapper.map(department2, DepartmentDTO.class);
		}
		throw new DepartmentNotFoundException("Department with Id - " + id + " not found!");
	}

	public Boolean deleteDepartment(Long id) {
		departmentRepository.deleteById(id);
		return true;
	}

	public List<DepartmentDTO> searchDepartmentsByName(String name) {
		List<DepartmentDTO> dtos = new ArrayList<>();
		List<Department> list = departmentRepository.findByNameContainingIgnoreCase(name);
		modelMapper.map(list, dtos);
		return dtos;
	}
}
