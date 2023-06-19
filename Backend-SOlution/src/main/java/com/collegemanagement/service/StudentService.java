package com.collegemanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegemanagement.dto.StudentDTO;
import com.collegemanagement.entity.Department;
import com.collegemanagement.entity.Student;
import com.collegemanagement.exception.DepartmentNotFoundException;
import com.collegemanagement.exception.StudentNotFoundException;
import com.collegemanagement.repo.DepartmentRepository;
import com.collegemanagement.repo.StudentRepository;

@Service
public class StudentService {
	private final StudentRepository studentRepository;
	private final DepartmentRepository departmentRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository) {
		this.studentRepository = studentRepository;
		this.departmentRepository = departmentRepository;
	}

	public List<StudentDTO> getAllStudents() {
		List<StudentDTO> dtos = new ArrayList<>();
		List<Student> list = studentRepository.findAll();
		modelMapper.map(list, dtos);
		return dtos;
	}

	public StudentDTO getStudentById(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student with Id - " + id + " not found!"));

		return modelMapper.map(student, StudentDTO.class);
	}

	public StudentDTO createStudent(StudentDTO studentDTO) {

		Department department = departmentRepository.findById(studentDTO.getDepartmentId())
				.orElseThrow(() -> new DepartmentNotFoundException(
						"Department with Id - " + studentDTO.getDepartmentId() + " not found!"));
		Student student = modelMapper.map(studentDTO, Student.class);
		Student student2 = studentRepository.save(student);
		return modelMapper.map(student2, StudentDTO.class);
	}

	public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
		Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(
				"Student with Id - " + studentDTO.getDepartmentId() + " not found!"));
		if (student != null) {
			Department department = departmentRepository.findById(studentDTO.getDepartmentId())
					.orElseThrow(() -> new DepartmentNotFoundException(
							"Department with Id - " + studentDTO.getDepartmentId() + " not found!"));
			student.setDepartment(department);
			Student student2 = modelMapper.map(studentDTO, Student.class);

			return modelMapper.map(student2, StudentDTO.class);
		}
		throw new StudentNotFoundException("Student with Id - " + id + " not found!");
	}

	public Boolean deleteStudent(Long id) {
		studentRepository.deleteById(id);
		return true;
	}

	public List<StudentDTO> searchStudentsByName(String name) {
		List<StudentDTO> dtos = new ArrayList<>();
		List<Student> list = studentRepository.findByNameContainingIgnoreCase(name);
		modelMapper.map(list, dtos);
		return dtos;
	}
}
