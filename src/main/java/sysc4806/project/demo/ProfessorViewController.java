package sysc4806.project.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sysc4806.project.demo.security.HandleUsers;
import sysc4806.project.demo.forms.TimeslotForm;
import sysc4806.project.demo.presentationHandling.TimeSlotHandling;


@Controller
@EnableHystrix
public class ProfessorViewController {

    Logger logger = LoggerFactory.getLogger(CoordinatorViewController.class);

    @Autowired
    private ProfessorRepository profRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping("/professorView/{id}")
    @HystrixCommand(fallbackMethod="profFallbackView")
    public String specificProfessorView(@CookieValue(value = "role", defaultValue = "noRole") String role,
                                        @CookieValue(value = "id", defaultValue = "-1") String givenId,
                                        @PathVariable("id") Long profId, Model model){

        logger.info("found cookies: " + role + " and " + givenId);
        if (HandleUsers.checkIfProf(givenId, role, profId)){
            logger.warn("ID: " + givenId + " attempted to login to professor " + profId);
            return "redirect:/";
        }

        if (profRepo.findById(profId).isPresent()) {
            Professor prof = profRepo.findById(profId).get();
            model.addAttribute("prof", prof);

            model.addAttribute("timeslotForm", new TimeslotForm());
            model.addAttribute("activeProjects", prof.getActiveProjects());
            model.addAttribute("archivedProjects", prof.getArchivedProjects());
            model.addAttribute("timeslot", TimeSlotHandling.fromTimeslotToMap(prof.getAvailability()));

            model.addAttribute("projectForm", new Project());

            return "TeacherUI";
        }
        else {
            return "redirect:/";
        }
    }

    @PostMapping("/professorView/{id}/createProject/")
    public String profCreateProject(@CookieValue(value = "role", defaultValue = "noRole") String role,
                                    @CookieValue(value = "id", defaultValue = "-1") String givenId,
                                    @ModelAttribute Project project, @PathVariable("id") Long profId, Model model){

        if (HandleUsers.checkIfProf(givenId, role, profId)){
            logger.warn("ID: " + givenId + " attempted to login to professor " + profId);
            return "redirect:/";
        }

        Professor prof = profRepo.findById(profId).get();

        Project new_proj = new Project(project.getName(), project.getDescription(), Project.ACTIVE_PROJ);

        new_proj.setDueDate(project.getDueDate());
        new_proj.setNumStudents(project.getNumStudents());

        new_proj.setProfessor(prof);
        prof.addProject(new_proj);

        profRepo.save(prof);
        projectRepo.save(new_proj);

        return "redirect:/professorView/" + profId;
    }

    @PostMapping("/professorView/createAvailability/{id}")
    public String createAvailability(@ModelAttribute TimeslotForm timeslot, @PathVariable("id") Long profId, Model model){
        String new_timeslot = TimeSlotHandling.convertToTimeslot(TimeSlotHandling.createTimeList(timeslot));

        if (profRepo.findById(profId).isPresent()) {
            Professor prof = profRepo.findById(profId).get();
            prof.setAvailability(new_timeslot);
            profRepo.save(prof);
        }

        return "redirect:/professorView/" + profId;
    }

    private String profFallbackView(@PathVariable("id") Long profId, Model model){
        return "ErrorUI";
    }
}
