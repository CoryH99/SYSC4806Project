package sysc4806.project.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfessorViewController {

    @Autowired
    private ProfessorRepository profRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping("/profView")
    public String profView(Model model){

        model.addAttribute("projects", projectRepo.findAll());

        return "TeacherUI";
    }

    @GetMapping("/professorView/{id}")
    public String specificProfessorView(@PathVariable("id") Long profId, Model model){

        model.addAttribute("projects", projectRepo.findAll());

        return "TeacherUI";
    }
}
