package sysc4806.project.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Controller
@EnableHystrix
public class CoordinatorViewController {

    @GetMapping("/coordinatorView/{timeout}")
    @HystrixCommand(fallbackMethod="defaultCoordinatorView")
    public String coordinatorView(@PathVariable("timeout") Long timeout, Model model){

//        model.addAttribute("projects", projectRepo.findAll());
        URI uri = URI.create("http://localhost:8080/project/getProjects/timeout/" + timeout);
        RestTemplate restTemplate = new RestTemplate();

        Project[] projects = restTemplate.getForObject(uri, Project[].class);
        model.addAttribute("projects", projects);

        return "CoordinatorUI";
    }

    private String defaultCoordinatorView(@PathVariable("timeout") Long timeout, Model model){
        Project[] projects = {};

        model.addAttribute("projects", projects);
        model.addAttribute("demo_message", "Default coordinator view used!");

        return "CoordinatorUI";
    }


}
