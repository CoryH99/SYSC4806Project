package sysc4806.project.demo;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sysc4806.project.demo.forms.TimeslotForm;
import sysc4806.project.demo.presentationHandling.TimeSlotHandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sysc4806.project.demo.security.HandleUsers;

@Controller
@EnableHystrix
public class StudentViewController {

    Logger logger = LoggerFactory.getLogger(CoordinatorViewController.class);

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping("/studentView/{id}")
    @HystrixCommand(fallbackMethod="studFallbackView")
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
        model.addAttribute("timeslotForm", new TimeslotForm());

        if (studentRepo.findById(studId).isPresent()) {
            Student student = studentRepo.findById(studId).get();
            model.addAttribute("student", student);
            model.addAttribute("timeslot", TimeSlotHandling.fromTimeslotToMap(student.getTimeslot()));

        } else {
            return "redirect:/";
        }
        model.addAttribute("projects", projectRepo.findByStatus(Project.ACTIVE_PROJ));

        return "StudentUI";
    }

    @PostMapping("/studentView/createTimeSlot/{id}")
    public String createTimeSlot(@ModelAttribute TimeslotForm timeslot, @PathVariable("id") Long studId, Model model){
        String new_timeslot = TimeSlotHandling.convertToTimeslot(TimeSlotHandling.createTimeList(timeslot));

        if (studentRepo.findById(studId).isPresent()) {
            Student student = studentRepo.findById(studId).get();
            student.setTimeslot(new_timeslot);
            studentRepo.save(student);
        }

        return "redirect:/studentView/" + studId;
    }

    private String studFallbackView(@CookieValue(value = "role", defaultValue = "noRole") String role,
                                    @CookieValue(value = "id", defaultValue = "-1") String givenId,
                                    @PathVariable("id") Long studId, Model model, HttpServletResponse response){
        return "ErrorUI";
    }

}
