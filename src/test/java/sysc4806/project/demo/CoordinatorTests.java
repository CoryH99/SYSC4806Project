package sysc4806.project.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class CoordinatorTests {

    @Autowired
    private MockMvc mockController;

    @Test
    public void testSendMessage() throws Exception {
        String expected = "ThisIsATest";
        String requestBody = "{\"name\": \"Bob\", \"program\": \"Software Engineering\", \"timeslot\": \"12:00\"}";

        this.mockController.perform(post("/student/createStudent").contentType(MediaType.APPLICATION_JSON).
                content(requestBody)).andDo(print());

        String formBody = URLEncoder.encode("id", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8);
        formBody += "&";
        formBody += URLEncoder.encode("content", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(expected, StandardCharsets.UTF_8);
        this.mockController.perform(post("/coordinator/sendMessage").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formBody)).andDo(print()).andExpect(status().isFound());

        this.mockController.perform(get("/student/getStudents")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().string(containsString(expected)));

    }

}
