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
public class ProfessorTests {

    @Autowired
    private MockMvc mockController;

    @Test
    public void testCreateAndGetProf() throws Exception {
        String expected = "testProf";

        String requestBody = "{\"name\": \"testProf\", \"availability\": \"monday\"}";

        this.mockController.perform(post("/professor/createProfessor").contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).andDo(print()).andExpect(status().isOk()).
                andExpect(content().string(containsString(expected)));

        this.mockController.perform(get("/professor/getProfessors")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().string(containsString("testProf")));
    }

    @Test
    public void testSetAvailability() throws Exception {
        String requestBody = "{\"name\": \"testProf\", \"availability\": \"monday\"}";
        String expected = "friday";

        this.mockController.perform(post("/professor/createProfessor").contentType(MediaType.APPLICATION_JSON).
                content(requestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(put("/professor/setAvailability?available=friday&id=1").contentType(MediaType.APPLICATION_JSON)
                ).andDo(print()).andExpect(status().isOk()).
                andExpect(content().string(containsString(expected)));
    }

    @Test
    public void testAssignProjectProf() throws Exception {
        String expected = "testProject";

        String profRequestBody = "{\"name\": \"testProf\", \"availability\": \"monday\"}";
        String projRequestBody = "{\"name\": \"testProject\", \"description\": \"test project\", \"numStudents\": 3}";

        this.mockController.perform(post("/project/createProject").contentType(MediaType.APPLICATION_JSON).
                content(projRequestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(post("/professor/createProfessor").contentType(MediaType.APPLICATION_JSON).
                content(profRequestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(put("/professor/assignProject?profID=1&projectID=1").contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().is(404));
    }

}