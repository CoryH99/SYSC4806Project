package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoordinatorViewController {

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping("/coordinatorView")
    public String coordinatorView(Model model){

        model.addAttribute("projects", projectRepo.findAll());

        return "CoordinatorUI";
    }
}
