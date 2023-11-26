package sysc4806.project.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentTests {

    @Autowired
    private MockMvc mockController;

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

    @Test
    public void testStudentTimeslot() throws Exception {
        String requestBody = "{\"name\": \"Bob\", \"program\": \"Software Engineering\", \"timeslot\": \"12:00\"}";
        String expected = "11:00";

        this.mockController.perform(post("/student/createStudent").contentType(MediaType.APPLICATION_JSON).
                content(requestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(put("/student/timeslot?slot=11:00&id=1").contentType(MediaType.APPLICATION_JSON)
                ).andDo(print()).andExpect(status().isOk()).
                andExpect(content().string(containsString(expected)));
    }

    /**
     * Test for /assignProject controller method
     * @throws Exception
     */
    @Test
    public void testAssignProjectStudent() throws Exception {
        String expected = "Bob";

        String studentRequestBody = "{\"name\": \"Bob\", \"program\": \"Software Engineering\", \"timeslot\": \"12:00\"}";
        String projRequestBody = "{\"name\": \"testProject\", \"description\": \"test project\", \"numStudents\": 3}";

        this.mockController.perform(post("/project/createProject").contentType(MediaType.APPLICATION_JSON).
                content(projRequestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(post("/student/createStudent").contentType(MediaType.APPLICATION_JSON).
                content(studentRequestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(put("/student/assignProject?studentID=1&projectID=1").contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(expected)));
    }



}
