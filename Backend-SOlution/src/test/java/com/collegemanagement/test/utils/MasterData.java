package com.collegemanagement.test.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.collegemanagement.dto.DepartmentDTO;
import com.collegemanagement.dto.StudentDTO;
import com.collegemanagement.dto.TeacherDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MasterData {

	public static TeacherDTO getTeacherDTO() {
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setId(1L);
		teacherDTO.setName("Rahul");
		teacherDTO.setDepartmentId(1L);
		return teacherDTO;

	}

	public static List<TeacherDTO> getTeacherDTOs() {
		List<TeacherDTO> list = new ArrayList<>();
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setId(1L);
		teacherDTO.setName("Rahul");
		teacherDTO.setDepartmentId(1L);
		list.add(teacherDTO);
		teacherDTO = new TeacherDTO();
		teacherDTO.setId(2L);
		teacherDTO.setName("John");
		teacherDTO.setDepartmentId(2L);
		list.add(teacherDTO);
		return list;
	}

	public static DepartmentDTO getDepartmentDTO() {
		DepartmentDTO dto = new DepartmentDTO();
		dto.setId(1L);
		dto.setName("Computer Science");
		return dto;

	}

	public static List<DepartmentDTO> getDepartmentDTOs() {
		List<DepartmentDTO> list = new ArrayList<>();
		DepartmentDTO dto = new DepartmentDTO();
		dto.setId(1L);
		dto.setName("Computer Science");
		list.add(dto);
		dto = new DepartmentDTO();
		dto.setId(2L);
		dto.setName("Electronics and Communications");
		list.add(dto);
		return list;
	}

	public static StudentDTO getStudentDTO() {
		StudentDTO dto = new StudentDTO();
		dto.setId(1L);
		dto.setName("Ravi");
		dto.setDepartmentId(1L);
		return dto;

	}

	public static List<StudentDTO> getStudentDTOs() {
		List<StudentDTO> list = new ArrayList<>();
		StudentDTO dto = new StudentDTO();
		dto.setId(1L);
		dto.setName("Ravi");
		dto.setDepartmentId(1L);
		list.add(dto);
		dto = new StudentDTO();
		dto.setId(2L);
		dto.setName("Krish");
		dto.setDepartmentId(2L);
		return list;
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
