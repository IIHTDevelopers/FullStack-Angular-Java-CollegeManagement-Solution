package com.collegemanagement.test.boundary;

import static com.collegemanagement.test.utils.TestUtils.boundaryTestFile;
import static com.collegemanagement.test.utils.TestUtils.currentTest;
import static com.collegemanagement.test.utils.TestUtils.yakshaAssert;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.collegemanagement.dto.DepartmentDTO;
import com.collegemanagement.dto.StudentDTO;
import com.collegemanagement.dto.TeacherDTO;
import com.collegemanagement.test.utils.MasterData;

public class BoundaryTests {

	private static Validator validator;

	// ----------------------------------------------------------------------------------------------
	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfDepartmentNameIsNotNull() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		departmentDTO.setName(null);
		Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfDepartmentNameLengthIsMinThree() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		departmentDTO.setName("A");
		Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfDepartmentNameIsNotMoreThanHunderdChars() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		String name = "";
		for (int i = 0; i < 105; i++) {
			name.concat("A");
		}
		departmentDTO.setName(name);
		Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfStudentNameIsNotNull() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		studentDTO.setName(null);
		Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfHospitalNameIsNotLessThanThreeChars() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		studentDTO.setName("A");
		Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfHospitalNameIsNotMoreThanHunderedChars() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		String name = "";
		for (int i = 0; i < 105; i++) {
			name.concat("A");
		}
		studentDTO.setName(name);
		Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfStudentDepartmentIdIsNotNull() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		studentDTO.setDepartmentId(null);
		Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfTeacherNameIsNotNull() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		teacherDTO.setName(null);
		Set<ConstraintViolation<TeacherDTO>> violations = validator.validate(teacherDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfTeacherNameIsMinThreeCharacters() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		teacherDTO.setName("A");
		Set<ConstraintViolation<TeacherDTO>> violations = validator.validate(teacherDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfTeacherNameIsNotMoreThanHunderedChars() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		String name = "";
		for (int i = 0; i < 105; i++) {
			name.concat("A");
		}
		teacherDTO.setName(name);
		Set<ConstraintViolation<TeacherDTO>> violations = validator.validate(teacherDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfTeacherDepartmentIdIsNotNull() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		teacherDTO.setDepartmentId(null);
		Set<ConstraintViolation<TeacherDTO>> violations = validator.validate(teacherDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

}
