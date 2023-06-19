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
import com.collegemanagement.controller.TeacherController;
import com.collegemanagement.dto.StudentDTO;
import com.collegemanagement.dto.TeacherDTO;
import com.collegemanagement.exception.DepartmentNotFoundException;
import com.collegemanagement.exception.ErrorResponse;
import com.collegemanagement.exception.StudentNotFoundException;
import com.collegemanagement.exception.TeacherNotFoundException;
import com.collegemanagement.service.DepartmentService;
import com.collegemanagement.service.StudentService;
import com.collegemanagement.service.TeacherService;
import com.collegemanagement.test.utils.MasterData;

@WebMvcTest(controllers = { DepartmentController.class, TeacherController.class })
@AutoConfigureMockMvc
@ContextConfiguration(classes = CollegeManagementApplication.class)
public class TestTeacherExceptions {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService departmentService;
	@MockBean
	private StudentService studentService;

	@MockBean
	private TeacherService teacherService;

	@Test
	public void testDataValidationCheckIfTeacherNameIsAddedInController() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		teacherDTO.setName("A");

		Mockito.when(teacherService.createTeacher(teacherDTO)).thenReturn(teacherDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/teachers")
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testAbleToWorkWithCustomExceptionsForCreatingTeacherRecord() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		teacherDTO.setName(null);

		Mockito.when(teacherService.createTeacher(teacherDTO)).thenReturn(teacherDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/teachers")
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testExceptionIsThrownAndHandledInCaseOfInvalidDataForAddingTeacherForDepartmentId() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		teacherDTO.setDepartmentId(null);

		Mockito.when(teacherService.createTeacher(teacherDTO)).thenReturn(teacherDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/teachers")
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	void testExceptionIsThrownAndHandledIfTeacherIdIsNotValidWhileDeleting() throws Exception {

		Long id = 200L;

		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				"Teacher with Id - " + id + " not found!");
		Mockito.when(teacherService.deleteTeacher(id))
				.thenThrow(new TeacherNotFoundException("Teacher with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teachers/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);
	}

	@Test
	void testExceptionIsThrownAndHandledIfDepartmentIdIsNotValidWhileGettingTeacherDetails() throws Exception {
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
	public void testDataValidationCheckForTeacherNameWhileUpdatingInController() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		teacherDTO.setName("A");

		Mockito.when(teacherService.updateTeacher(teacherDTO.getId(), teacherDTO)).thenReturn(teacherDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/teachers/" + teacherDTO.getId())
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testDeleteTeacherByIdToCheckForStudentNotFoundException() throws Exception {
		Long id = 201l;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				"Teacher with Id - " + id + " not found!");

		Mockito.when(teacherService.deleteTeacher(id))
				.thenThrow(new TeacherNotFoundException("Teacher with Id - " + id + " not found!"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teachers/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);

	}

}
