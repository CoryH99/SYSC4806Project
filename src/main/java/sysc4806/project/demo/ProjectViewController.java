package sysc4806.project.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@EnableHystrix
public class ProjectViewController {

    @Autowired
    ProjectRepository projectRepo;
    @GetMapping("/projects/{projID}")
    @HystrixCommand(fallbackMethod="fallbackView")
    public String projectView(@PathVariable("projID") Long projID, Model model){
        model.addAttribute("proj", projectRepo.findAll());
        return "ProjectPage";
    }

    private String fallbackView(){
        return "ErrorUI";
    }

}
