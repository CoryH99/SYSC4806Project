package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private ProjectRepository repo;

    @PostMapping("/testProj")
    public Project createProj(@RequestBody Project proj){
        return repo.save(proj);
    }

    @GetMapping("/getProj")
    public List<Project> getProjects(){
        return repo.findAll();
    }

}
