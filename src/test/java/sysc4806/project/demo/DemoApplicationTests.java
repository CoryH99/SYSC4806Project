package sysc4806.project.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for TestController class using MockMvc
 */
@Import(StudentController.class)
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockController;

	/**
	 * Test for /createProject and /getProjects controller methods
	 * @throws Exception
	 */
	@Test
	public void testCreateAndGetProj() throws Exception {
		String expected = "testProject";

		String requestBody = "{\"name\": \"testProject\", \"description\": \"test project\"}";

		this.mockController.perform(post("/project/createProject").contentType(MediaType.APPLICATION_JSON).
						content(requestBody)).andDo(print()).andExpect(status().isOk()).
						andExpect(content().string(containsString(expected)));

		this.mockController.perform(get("/project/getProjects")).andDo(print()).andExpect(status().isOk()).andExpect(
				content().string(containsString("testProject")));
	}

	/**
	 * Test for /createStudent and /getStudents controller methods
	 * @throws Exception
	 */
	@Test
	public void testCreateAndGetStudent() throws Exception {
		String expected = "Bob";

		String requestBody = "{\"name\": \"Bob\", \"program\": \"Software Engineering\", \"timeslot\": \"12:00\"}";

		this.mockController.perform(post("/student/createStudent").contentType(MediaType.APPLICATION_JSON).
						content(requestBody)).andDo(print()).andExpect(status().isOk()).
				andExpect(content().string(containsString(expected)));

		this.mockController.perform(get("/student/getStudents")).andDo(print()).andExpect(status().isOk()).andExpect(
				content().string(containsString("Bob")));
	}

	/**
	 * Test for /assignProject controller method
	 * @throws Exception
	 */
	@Test
	public void testAssignProject() throws Exception {
		String expected = "Bob";

		String studentRequestBody = "{\"name\": \"Bob\", \"program\": \"Software Engineering\", \"timeslot\": \"12:00\"}";
		String projRequestBody = "{\"name\": \"testProject\", \"description\": \"test project\", \"numStudents\": 3}";

		this.mockController.perform(post("/project/createProject").contentType(MediaType.APPLICATION_JSON).
						content(projRequestBody)).andDo(print()).andExpect(status().isOk());

		this.mockController.perform(post("/student/createStudent").contentType(MediaType.APPLICATION_JSON).
						content(studentRequestBody)).andDo(print()).andExpect(status().isOk());

		this.mockController.perform(put("/student/assignProject?studentID=1&projectID=1").contentType(MediaType.APPLICATION_JSON)
				).andDo(print()).andExpect(status().isOk()).
				andExpect(content().string(containsString(expected)));
	}
}
