package com.collegemanagement.test.exception;

import static com.collegemanagement.test.utils.TestUtils.currentTest;
import static com.collegemanagement.test.utils.TestUtils.exceptionTestFile;
import static com.collegemanagement.test.utils.TestUtils.yakshaAssert;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.collegemanagement.CollegeManagementApplication;
import com.collegemanagement.controller.DepartmentController;
import com.collegemanagement.controller.StudentController;
import com.collegemanagement.dto.DepartmentDTO;
import com.collegemanagement.dto.StudentDTO;
import com.collegemanagement.exception.DepartmentNotFoundException;
import com.collegemanagement.exception.ErrorResponse;
import com.collegemanagement.exception.StudentNotFoundException;
import com.collegemanagement.service.DepartmentService;
import com.collegemanagement.service.StudentService;
import com.collegemanagement.service.TeacherService;
import com.collegemanagement.test.utils.MasterData;

@WebMvcTest(controllers = { DepartmentController.class, StudentController.class })
@AutoConfigureMockMvc
@ContextConfiguration(classes = CollegeManagementApplication.class)
public class TestStudentExceptions {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService departmentService;
	@MockBean
	private StudentService studentService;

	@MockBean
	private TeacherService teacherService;

	@Test
	public void testDataValidationCheckStudentNameIsAddedInController() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		studentDTO.setName("A");

		Mockito.when(studentService.createStudent(studentDTO)).thenReturn(studentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/students")
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testAbleToWorkWithCustomExceptionsForCreatingStudentRecord() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		studentDTO.setName(null);

		Mockito.when(studentService.createStudent(studentDTO)).thenReturn(studentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/students")
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testExceptionIsThrownAndHandledInCaseOfInvalidDataForAddingStudentForDepartmentId() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		studentDTO.setDepartmentId(null);

		Mockito.when(studentService.createStudent(studentDTO)).thenReturn(studentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/students")
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	void testExceptionIsThrownAndHandledIfStudentIdIsNotValidWhileDeleting() throws Exception {

		Long id = 200L;

		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				"Student with Id - " + id + " not found!");
		Mockito.when(studentService.deleteStudent(id))
				.thenThrow(new DepartmentNotFoundException("Student with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/students/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);
	}

	@Test
	void testExceptionIsThrownAndHandledIfDepartmentIdIsNotValidWhileGettingStudents() throws Exception {
		Long id = 211L;

		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				"Department with Id - " + id + " not found!");
		Mockito.when(departmentService.getDepartmentById(id))
				.thenThrow(new DepartmentNotFoundException("Department with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);

	}

	@Test
	public void testDataValidationCheckForStudentNameWhileUpdatingInController() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		studentDTO.setName("A");

		Mockito.when(studentService.updateStudent(studentDTO.getId(), studentDTO)).thenReturn(studentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/students/" + studentDTO.getId())
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testDeleteStudentByIdToCheckForStudentNotFoundException() throws Exception {
		Long id = 201l;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				"Student with Id - " + id + " not found!");

		Mockito.when(studentService.deleteStudent(id))
				.thenThrow(new StudentNotFoundException("Student with Id - " + id + " not found!"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/students/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);

	}

}
