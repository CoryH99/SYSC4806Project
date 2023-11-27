package sysc4806.project.demo;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootTest
@AutoConfigureMockMvc
@Retention(RetentionPolicy.RUNTIME)
public @interface MockMvcTest {
}
