package sysc4806.project.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sysc4806.project.demo.security.HandleUsers;

@Controller
public class ProfessorViewController {

    Logger logger = LoggerFactory.getLogger(CoordinatorViewController.class);

    @Autowired
    private ProfessorRepository profRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping("/professorView/{id}")
    public String specificProfessorView(@CookieValue(value = "role", defaultValue = "noRole") String role,
                                        @CookieValue(value = "studId", defaultValue = "-1") String givenId,
                                        @PathVariable("id") Long profId, Model model){

        logger.info("found cookies: " + role + " and " + givenId);
        if (HandleUsers.checkIfProf(givenId, role, profId)){
            logger.warn("ID: " + givenId + " attempted to login to professor " + profId);
            return "redirect:/";
        }

        if (profRepo.findById(profId).isPresent()) {
            Professor prof = profRepo.findById(profId).get();
            model.addAttribute("prof", prof);

            model.addAttribute("activeProjects", prof.getActiveProjects());
            model.addAttribute("archivedProjects", prof.getArchivedProjects());

            model.addAttribute("projectForm", new Project());

            return "TeacherUI";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/professorView/{id}/createProject/")
    public String profCreateProject(@CookieValue(value = "role", defaultValue = "noRole") String role,
                                    @CookieValue(value = "studId", defaultValue = "-1") String givenId,
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
}
