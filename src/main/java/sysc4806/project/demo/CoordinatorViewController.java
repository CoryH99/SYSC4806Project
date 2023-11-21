package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Controller
public class CoordinatorViewController {

    @GetMapping("/coordinatorView")
    public String coordinatorView(Model model){

//        model.addAttribute("projects", projectRepo.findAll());
        URI uri = URI.create("http://localhost:8080/project/getProjects");
        RestTemplate restTemplate = new RestTemplate();

        Project[] projects = restTemplate.getForObject(uri, Project[].class);
        model.addAttribute("projects", projects);

        return "CoordinatorUI";
    }
}
