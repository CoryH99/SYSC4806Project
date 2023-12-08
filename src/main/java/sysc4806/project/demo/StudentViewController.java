package sysc4806.project.demo;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sysc4806.project.demo.security.HandleUsers;

@Controller
public class StudentViewController {

    Logger logger = LoggerFactory.getLogger(CoordinatorViewController.class);

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping("/studentView/{id}")
    public String specificStudentView(@CookieValue(value = "role", defaultValue = "noRole") String role,
                                      @CookieValue(value = "id", defaultValue = "-1") String givenId,
                                      @PathVariable("id") Long studId, Model model, HttpServletResponse response){

        logger.info("found cookies: " + role + " and " + givenId);
        if (HandleUsers.checkIfStudent(givenId, role, studId)){
            logger.warn("ID: " + givenId + " attempted to login to student " + studId);
            return "redirect:/";
        }

        // Constants
        model.addAttribute("URGENT_LEVEL", "Urgent");

        if (studentRepo.findById(studId).isPresent()) {
            Student student = studentRepo.findById(studId).get();
            model.addAttribute("student", student);
        } else {
            return "redirect:/";
        }
        model.addAttribute("projects", projectRepo.findByStatus(Project.ACTIVE_PROJ));

        return "StudentUI";
    }

}
