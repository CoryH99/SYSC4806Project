package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectViewController {

    @Autowired
    ProjectRepository projectRepo;
    @GetMapping("/projects/{projID}")
    public String projectView(@PathVariable("projID") Long projID, Model model){
        model.addAttribute("proj", projectRepo.findAll());
        return "ProjectPage";
    }

}
