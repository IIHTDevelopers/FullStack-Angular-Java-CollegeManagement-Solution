package com.collegemanagement.test.functional;

import static com.collegemanagement.test.utils.TestUtils.asJsonString;
import static com.collegemanagement.test.utils.TestUtils.businessTestFile;
import static com.collegemanagement.test.utils.TestUtils.currentTest;
import static com.collegemanagement.test.utils.TestUtils.yakshaAssert;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.collegemanagement.CollegeManagementApplication;
import com.collegemanagement.controller.StudentController;
import com.collegemanagement.dto.StudentDTO;
import com.collegemanagement.service.StudentService;
import com.collegemanagement.test.utils.MasterData;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = CollegeManagementApplication.class)
class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private StudentService studentService;

	/************* Get All Students *******************/

	@Test
	void testRestEndpointForFindAllStudentsIsExposedAndWorking() throws Exception {
		List<StudentDTO> list = MasterData.getStudentDTOs();

		Mockito.when(studentService.getAllStudents()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students")
				.content(MasterData.asJsonString(MasterData.getStudentDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToDefineAppropriateClassesAndObjectsForGettingAllStudentsInAGivenScenario() throws Exception {
		List<StudentDTO> list = MasterData.getStudentDTOs();

		Mockito.when(studentService.getAllStudents()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students")
				.content(MasterData.asJsonString(MasterData.getStudentDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToConfigureAndConnectToDatabaseForStudents() throws Exception {
		List<StudentDTO> list = MasterData.getStudentDTOs();

		Mockito.when(studentService.getAllStudents()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students")
				.content(MasterData.asJsonString(MasterData.getStudentDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);

	}

	@Test
	void testRestEndpointForFindAllStudentsIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<StudentDTO> list = MasterData.getStudentDTOs();
		Mockito.when(studentService.getAllStudents()).then(new Answer<List<StudentDTO>>() {

			@Override
			public List<StudentDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students")
				.content(MasterData.asJsonString(MasterData.getStudentDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/*************** Add Student ***********************/

	@Test
	void testRestEndpointForAddingNewStudentIsExposedAndWorking() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		Mockito.when(studentService.createStudent(studentDTO)).thenReturn(studentDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/students")
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(studentDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForAddingNewStudentIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		StudentDTO studentDTO = MasterData.getStudentDTO();
		Mockito.when(studentService.createStudent(studentDTO)).then(new Answer<StudentDTO>() {
			@Override
			public StudentDTO answer(InvocationOnMock invocation) throws Throwable {
				count[0]++;
				return studentDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/students")
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************************ Delete Student ****************/

	@Test
	void testRestEndpointForDeletingStudentIsExposedAndWorking() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		Long id = studentDTO.getId();
		Mockito.when(studentService.deleteStudent(id)).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/students/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().contains("true") ? true : false,
				businessTestFile);

	}

	@Test
	void testRestEndpointForDeletingStudentIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		StudentDTO studentDTO = MasterData.getStudentDTO();
		Long id = studentDTO.getId();
		Mockito.when(studentService.deleteStudent(id)).then(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return true;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/students/" + id)
				.content(MasterData.asJsonString(true)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************* Get Single Student ******************/

	@Test
	void testRestEndpointForFindingStudentByIdIsExposedAndWorking() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		Long id = studentDTO.getId();
		Mockito.when(studentService.getStudentById(id)).thenReturn(studentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(studentDTO)) ? true : false),
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingStudentByIdIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		StudentDTO studentDTO = MasterData.getStudentDTO();
		Long id = studentDTO.getId();
		Mockito.when(studentService.getStudentById(id)).then(new Answer<StudentDTO>() {

			@Override
			public StudentDTO answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return studentDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students/" + id)
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	/************* Update Student details *************************/

	@Test
	public void testRestEndpointForUpdatingStudentIsExposedAndWorking() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		Long id = studentDTO.getId();
		studentDTO.setName("Ravi");
		Mockito.when(studentService.updateStudent(id, studentDTO)).thenReturn(studentDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/students/" + id)
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(studentDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	public void testAbleToUseCorrectAnnotationsForCreatingRestEndPointsForUpdatingStudent() throws Exception {
		StudentDTO studentDTO = MasterData.getStudentDTO();
		Long id = studentDTO.getId();
		studentDTO.setName("Ravi");
		Mockito.when(studentService.updateStudent(id, studentDTO)).thenReturn(studentDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/students/" + id)
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(studentDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForUpdatingStudentIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		StudentDTO studentDTO = MasterData.getStudentDTO();
		Long id = studentDTO.getId();
		Mockito.when(studentService.updateStudent(id, studentDTO)).then(new Answer<StudentDTO>() {

			@Override
			public StudentDTO answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return studentDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/students/" + id)
				.content(MasterData.asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	/************ Search By Student Name *********************/

	@Test
	void testRestEndpointForFindingStudentByNameIsExposedAndWorking() throws Exception {
		List<StudentDTO> list = MasterData.getStudentDTOs();
		Mockito.when(studentService.searchStudentsByName(list.get(0).getName())).thenReturn(list);
		MvcResult result = mockMvc
				.perform(get("/students/search?name=" + list.get(0).getName()).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingStudentByNameIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<StudentDTO> list = MasterData.getStudentDTOs();
		Mockito.when(studentService.searchStudentsByName(list.get(0).getName())).then(new Answer<List<StudentDTO>>() {

			@Override
			public List<StudentDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students/search?name=" + list.get(0).getName())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

}
