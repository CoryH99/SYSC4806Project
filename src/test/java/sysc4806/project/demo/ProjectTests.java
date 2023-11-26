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
public class ProjectTests {

    @Autowired
    private MockMvc mockController;

    @Test
    public void testRemoveStudentFromProj() throws Exception {
        String studentRequestBody = "{\"name\": \"Bob\", \"program\": \"Software Engineering\", \"timeslot\": \"12:00\"}";
        String projRequestBody = "{\"name\": \"testProject\", \"description\": \"test project\", \"numStudents\": 3}";

        this.mockController.perform(post("/project/createProject").contentType(MediaType.APPLICATION_JSON).
                content(projRequestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(post("/student/createStudent").contentType(MediaType.APPLICATION_JSON).
                content(studentRequestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(put("/student/assignProject?studentID=1&projectID=2").contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(delete("/project/removeStudent?stuID=1&projID=2").contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }

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

    @Test
    public void testDeleteProj() throws Exception {
        String requestBody = "{\"name\": \"testProject\", \"description\": \"test project\"}";

        this.mockController.perform(post("/project/createProject").contentType(MediaType.APPLICATION_JSON).
                content(requestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(delete("/project/delete?id=1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testAddProjRestriction() throws Exception {
        String requestBody = "{\"name\": \"testProject\", \"description\": \"test project\"}";
        String expected = "cs";

        this.mockController.perform(post("/project/createProject").contentType(MediaType.APPLICATION_JSON).
                content(requestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(put("/project/addRestriction?id=1&restrict=cs").contentType(MediaType.APPLICATION_JSON)
                ).andDo(print()).andExpect(status().isOk()).
                andExpect(content().string(containsString(expected)));
    }

    @Test
    public void testSetProjDueDate() throws Exception {
        String requestBody = "{\"name\": \"testProject\", \"description\": \"test project\"}";
        String expected = "Dec12";

        this.mockController.perform(post("/project/createProject").contentType(MediaType.APPLICATION_JSON).
                content(requestBody)).andDo(print()).andExpect(status().isOk());

        this.mockController.perform(put("/project/setDueDate?date=Dec12&id=1").contentType(MediaType.APPLICATION_JSON)
                ).andDo(print()).andExpect(status().isOk()).
                andExpect(content().string(containsString(expected)));
    }

    @Test
    public void testListProj() throws Exception {
        String expected = "testProject";

        String requestBody = "{\"name\": \"testProject\", \"description\": \"test project\", \"status\": \"done\"}";

        this.mockController.perform(post("/project/createProject").contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).andDo(print()).andExpect(status().isOk()).
                andExpect(content().string(containsString(expected)));

        this.mockController.perform(get("/project/listByStatus?status=done")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().string(containsString("testProject")));
    }



}