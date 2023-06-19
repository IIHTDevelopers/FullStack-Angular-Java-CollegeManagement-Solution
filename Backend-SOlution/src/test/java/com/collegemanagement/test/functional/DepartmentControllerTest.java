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
import com.collegemanagement.controller.DepartmentController;
import com.collegemanagement.dto.DepartmentDTO;
import com.collegemanagement.service.DepartmentService;
import com.collegemanagement.test.utils.MasterData;

@WebMvcTest(DepartmentController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = CollegeManagementApplication.class)
class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DepartmentService departmentService;

	/************* Get All Departments *******************/

	@Test
	void testRestEndpointForFindAllDepartmentsIsExposedAndWorking() throws Exception {
		List<DepartmentDTO> list = MasterData.getDepartmentDTOs();

		Mockito.when(departmentService.getAllDepartments()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments")
				.content(MasterData.asJsonString(MasterData.getDepartmentDTOs()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToDefineAppropriateClassesAndObjectsForGettingAllDepartmentsInAGivenScenario() throws Exception {
		List<DepartmentDTO> list = MasterData.getDepartmentDTOs();

		Mockito.when(departmentService.getAllDepartments()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments")
				.content(MasterData.asJsonString(MasterData.getDepartmentDTOs()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToConfigureAndConnectToDatabaseForDepartment() throws Exception {
		List<DepartmentDTO> list = MasterData.getDepartmentDTOs();

		Mockito.when(departmentService.getAllDepartments()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments")
				.content(MasterData.asJsonString(MasterData.getDepartmentDTOs()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);

	}

	@Test
	void testRestEndpointForFindAllDepartmentsIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<DepartmentDTO> list = MasterData.getDepartmentDTOs();
		Mockito.when(departmentService.getAllDepartments()).then(new Answer<List<DepartmentDTO>>() {

			@Override
			public List<DepartmentDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments")
				.content(MasterData.asJsonString(MasterData.getDepartmentDTOs()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/*************** Add Department ***********************/

	@Test
	void testRestEndpointForAddingNewDepartmentIsExposedAndWorking() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		Mockito.when(departmentService.createDepartment(departmentDTO)).thenReturn(departmentDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/departments")
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(departmentDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForAddingNewDepartmentIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		Mockito.when(departmentService.createDepartment(departmentDTO)).then(new Answer<DepartmentDTO>() {
			@Override
			public DepartmentDTO answer(InvocationOnMock invocation) throws Throwable {
				count[0]++;
				return departmentDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/departments")
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************************ Delete Department ****************/

	@Test
	void testRestEndpointForDeletingDoctorIsExposedAndWorking() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		Long id = departmentDTO.getId();
		Mockito.when(departmentService.deleteDepartment(id)).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/departments/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().contains("true") ? true : false,
				businessTestFile);

	}

	@Test
	void testRestEndpointForDeletingDepartmentIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		Long id = departmentDTO.getId();
		Mockito.when(departmentService.deleteDepartment(id)).then(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return true;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/departments/" + id)
				.content(MasterData.asJsonString(true)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************* Get Single Department ******************/

	@Test
	void testRestEndpointForFindingDepartmentByIdIsExposedAndWorking() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		Long id = departmentDTO.getId();
		Mockito.when(departmentService.getDepartmentById(id)).thenReturn(departmentDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(departmentDTO)) ? true : false),
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingDepartmentByIdIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		Long id = departmentDTO.getId();
		Mockito.when(departmentService.getDepartmentById(id)).then(new Answer<DepartmentDTO>() {

			@Override
			public DepartmentDTO answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return departmentDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments/" + id)
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	/************* Update Department *************************/

	@Test
	public void testRestEndpointForUpdatingDepartmentIsExposedAndWorking() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		Long id = departmentDTO.getId();
		departmentDTO.setName("Ravi");
		Mockito.when(departmentService.updateDepartment(id, departmentDTO)).thenReturn(departmentDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/departments/" + id)
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(departmentDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	public void testAbleToUseCorrectAnnotationsForCreatingRestEndPointsForUpdatingDepartment() throws Exception {
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		Long id = departmentDTO.getId();
		departmentDTO.setName("Krish");
		Mockito.when(departmentService.updateDepartment(id, departmentDTO)).thenReturn(departmentDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/departments/" + id)
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(departmentDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForUpdatingDepartmentIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		DepartmentDTO departmentDTO = MasterData.getDepartmentDTO();
		Long id = departmentDTO.getId();
		Mockito.when(departmentService.updateDepartment(id, departmentDTO)).then(new Answer<DepartmentDTO>() {

			@Override
			public DepartmentDTO answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return departmentDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/departments/" + id)
				.content(MasterData.asJsonString(departmentDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	/************ Search By Department Name *********************/

	@Test
	void testRestEndpointForFindingDepartmentByNameIsExposedAndWorking() throws Exception {
		List<DepartmentDTO> list = MasterData.getDepartmentDTOs();
		Mockito.when(departmentService.searchDepartmentsByName(list.get(0).getName())).thenReturn(list);
		MvcResult result = mockMvc.perform(
				get("/departments/search?name=" + list.get(0).getName()).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingDepartmentByNameIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<DepartmentDTO> list = MasterData.getDepartmentDTOs();
		Mockito.when(departmentService.searchDepartmentsByName(list.get(0).getName()))
				.then(new Answer<List<DepartmentDTO>>() {

					@Override
					public List<DepartmentDTO> answer(InvocationOnMock invocation) throws Throwable {
						// TODO Auto-generated method stub

						count[0]++;
						return list;
					}
				});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments/search?name=" + list.get(0).getName())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

}
