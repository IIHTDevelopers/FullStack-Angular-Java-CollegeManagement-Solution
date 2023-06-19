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
import com.collegemanagement.controller.TeacherController;
import com.collegemanagement.dto.StudentDTO;
import com.collegemanagement.dto.TeacherDTO;
import com.collegemanagement.service.StudentService;
import com.collegemanagement.service.TeacherService;
import com.collegemanagement.test.utils.MasterData;

@WebMvcTest(TeacherController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = CollegeManagementApplication.class)
class TeacherControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TeacherService teacherService;

	/************* Get All Teachers *******************/

	@Test
	void testRestEndpointForFindAllTeachersIsExposedAndWorking() throws Exception {
		List<TeacherDTO> list = MasterData.getTeacherDTOs();

		Mockito.when(teacherService.getAllTeachers()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers")
				.content(MasterData.asJsonString(MasterData.getTeacherDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToDefineAppropriateClassesAndObjectsForGettingAllTeachersInAGivenScenario() throws Exception {
		List<TeacherDTO> list = MasterData.getTeacherDTOs();

		Mockito.when(teacherService.getAllTeachers()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers")
				.content(MasterData.asJsonString(MasterData.getTeacherDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToConfigureAndConnectToDatabaseForTeachers() throws Exception {
		List<TeacherDTO> list = MasterData.getTeacherDTOs();

		Mockito.when(teacherService.getAllTeachers()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers")
				.content(MasterData.asJsonString(MasterData.getTeacherDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);

	}

	@Test
	void testRestEndpointForFindAllTeachersIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<TeacherDTO> list = MasterData.getTeacherDTOs();
		Mockito.when(teacherService.getAllTeachers()).then(new Answer<List<TeacherDTO>>() {

			@Override
			public List<TeacherDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers")
				.content(MasterData.asJsonString(MasterData.getTeacherDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/*************** Add Teacher ***********************/

	@Test
	void testRestEndpointForAddingNewTeacherIsExposedAndWorking() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		Mockito.when(teacherService.createTeacher(teacherDTO)).thenReturn(teacherDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/teachers")
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(teacherDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForAddingNewTeacherIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		Mockito.when(teacherService.createTeacher(teacherDTO)).then(new Answer<TeacherDTO>() {
			@Override
			public TeacherDTO answer(InvocationOnMock invocation) throws Throwable {
				count[0]++;
				return teacherDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/teachers")
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************************ Delete Teacher ****************/

	@Test
	void testRestEndpointForDeletingTeacherIsExposedAndWorking() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		Long id = teacherDTO.getId();
		Mockito.when(teacherService.deleteTeacher(id)).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teachers/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().contains("true") ? true : false,
				businessTestFile);

	}

	@Test
	void testRestEndpointForDeletingTeacherIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		Long id = teacherDTO.getId();
		Mockito.when(teacherService.deleteTeacher(id)).then(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return true;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teachers/" + id)
				.content(MasterData.asJsonString(true)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************* Get Single Teacher ******************/

	@Test
	void testRestEndpointForFindingTeacherByIdIsExposedAndWorking() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		Long id = teacherDTO.getId();
		Mockito.when(teacherService.getTeacherById(id)).thenReturn(teacherDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(teacherDTO)) ? true : false),
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingTeacherByIdIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		Long id = teacherDTO.getId();
		Mockito.when(teacherService.getTeacherById(id)).then(new Answer<TeacherDTO>() {

			@Override
			public TeacherDTO answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return teacherDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers/" + id)
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	/************* Update Teacher details *************************/

	@Test
	public void testRestEndpointForUpdatingTeacherIsExposedAndWorking() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		Long id = teacherDTO.getId();
		teacherDTO.setName("Ravi");
		Mockito.when(teacherService.updateTeacher(id, teacherDTO)).thenReturn(teacherDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/teachers/" + id)
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(teacherDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	public void testAbleToUseCorrectAnnotationsForCreatingRestEndPointsForUpdatingTeacher() throws Exception {
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		Long id = teacherDTO.getId();
		teacherDTO.setName("Rahul");
		Mockito.when(teacherService.updateTeacher(id, teacherDTO)).thenReturn(teacherDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/teachers/" + id)
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(teacherDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForUpdatingTeacherIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		TeacherDTO teacherDTO = MasterData.getTeacherDTO();
		Long id = teacherDTO.getId();
		Mockito.when(teacherService.updateTeacher(id, teacherDTO)).then(new Answer<TeacherDTO>() {

			@Override
			public TeacherDTO answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return teacherDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/teachers/" + id)
				.content(MasterData.asJsonString(teacherDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	/************ Search By Teacher Name *********************/

	@Test
	void testRestEndpointForFindingTeacherByNameIsExposedAndWorking() throws Exception {
		List<TeacherDTO> list = MasterData.getTeacherDTOs();
		Mockito.when(teacherService.searchTeachersByName(list.get(0).getName())).thenReturn(list);
		MvcResult result = mockMvc
				.perform(get("/teachers/search?name=" + list.get(0).getName()).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingTeacherByNameIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<TeacherDTO> list = MasterData.getTeacherDTOs();
		Mockito.when(teacherService.searchTeachersByName(list.get(0).getName())).then(new Answer<List<TeacherDTO>>() {

			@Override
			public List<TeacherDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teachers/search?name=" + list.get(0).getName())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

}
