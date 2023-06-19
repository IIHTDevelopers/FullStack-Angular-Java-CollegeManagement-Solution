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
import com.collegemanagement.dto.DepartmentDTO;
import com.collegemanagement.exception.DepartmentNotFoundException;
import com.collegemanagement.exception.ErrorResponse;
import com.collegemanagement.service.DepartmentService;
import com.collegemanagement.service.StudentService;
import com.collegemanagement.service.TeacherService;
import com.collegemanagement.test.utils.MasterData;

@WebMvcTest(DepartmentController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = CollegeManagementApplication.class)
public class TestDepartmentExceptions {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService departmentService;
	@MockBean
	private StudentService studentService;

	@MockBean
	private TeacherService teacherService;

	@Test
	public void testDataValidationCheckDepartmentNameIsAddedInController() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		departmentDTO.setName("A");

		Mockito.when(departmentService.createDepartment(departmentDTO)).thenReturn(departmentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/departments")
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testAbleToWorkWithCustomExceptionsForAddingDepartment() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		departmentDTO.setName(null);

		Mockito.when(departmentService.createDepartment(departmentDTO)).thenReturn(departmentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/departments")
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);
	}

	@Test
	public void testExceptionIsThrownAndHandledInCaseOfInvalidDataForAddingDepartment() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		departmentDTO.setName(null);

		Mockito.when(departmentService.createDepartment(departmentDTO)).thenReturn(departmentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/departments")
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	void testExceptionIsThrownAndHandledIfDepartmentIdIsNotValidWhileDeleting() throws Exception {

		Long id = 200L;

		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				"Department with Id - " + id + " not found!");
		Mockito.when(departmentService.deleteDepartment(id))
				.thenThrow(new DepartmentNotFoundException("Department with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/departments/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);
	}

	@Test
	void testExceptionIsThrownAndHandledIfDepartmentIdIsNotValidWhileGettingDoctorById() throws Exception {
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
	public void testDataValidationCheckDepartmentNameWhileUpdatingInController() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		departmentDTO.setName("A");

		Mockito.when(departmentService.updateDepartment(departmentDTO.getId(), departmentDTO))
				.thenReturn(departmentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/departments/" + departmentDTO.getId())
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testDeleteDepartmentByIdToCheckDepartmentNotFoundException() throws Exception {
		Long id = 201l;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				"Department with Id - " + id + " not found!");

		Mockito.when(departmentService.deleteDepartment(id))
				.thenThrow(new DepartmentNotFoundException("Department with Id - " + id + " not found!"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/departments/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);

	}

}
