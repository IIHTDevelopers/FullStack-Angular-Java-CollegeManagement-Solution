package com.collegemanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegemanagement.dto.TeacherDTO;
import com.collegemanagement.entity.Department;
import com.collegemanagement.entity.Teacher;
import com.collegemanagement.exception.DepartmentNotFoundException;
import com.collegemanagement.exception.TeacherNotFoundException;
import com.collegemanagement.repo.DepartmentRepository;
import com.collegemanagement.repo.TeacherRepository;

@Service
public class TeacherService {
	private final TeacherRepository teacherRepository;
	private final DepartmentRepository departmentRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	public TeacherService(TeacherRepository teacherRepository, DepartmentRepository departmentRepository) {
		this.teacherRepository = teacherRepository;
		this.departmentRepository = departmentRepository;
	}

	public List<TeacherDTO> getAllTeachers() {
		List<TeacherDTO> dtos = new ArrayList<>();
		List<Teacher> list = teacherRepository.findAll();
		modelMapper.map(list, dtos);
		return dtos;
	}

	public TeacherDTO getTeacherById(Long id) {
		Teacher teacher = teacherRepository.findById(id)
				.orElseThrow(() -> new TeacherNotFoundException("Teacher with Id - " + id + " not found!"));

		return modelMapper.map(teacher, TeacherDTO.class);
	}

	public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
		Teacher teacher = new Teacher();
		teacher.setName(teacherDTO.getName());

		Department department = departmentRepository.findById(teacherDTO.getDepartmentId())
				.orElseThrow(() -> new DepartmentNotFoundException(
						"Department with Id - " + teacherDTO.getDepartmentId() + " not found!"));
		teacher.setDepartment(department);
		Teacher teacher2 = teacherRepository.save(teacher);
		return modelMapper.map(teacher2, TeacherDTO.class);
	}

	public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
		Teacher teacher = teacherRepository.findById(id).orElse(null);
		if (teacher != null) {
			teacher.setName(teacherDTO.getName());

			Department department = departmentRepository.findById(teacherDTO.getDepartmentId())
					.orElseThrow(() -> new DepartmentNotFoundException(
							"Department with Id - " + teacherDTO.getDepartmentId() + " not found!"));
			teacher.setDepartment(department);
			Teacher teacher2 = teacherRepository.save(teacher);
			return modelMapper.map(teacher2, TeacherDTO.class);
		}
		throw new TeacherNotFoundException("Teacher with Id - " + id + " not found!");
	}

	public Boolean deleteTeacher(Long id) {
		teacherRepository.deleteById(id);
		return true;
	}

	public List<TeacherDTO> searchTeachersByName(String name) {
		List<TeacherDTO> dtos = new ArrayList<>();
		List<Teacher> list = teacherRepository.findByNameContainingIgnoreCase(name);
		modelMapper.map(list, dtos);
		return dtos;
	}
}
