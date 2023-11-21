package sysc4806.project.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
//@EnableCircuitBreaker
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private StudentRepository studentRepo;

//    @GetMapping
//    @HystrixCommand(fallbackMethod = "fallbackMessage")
//    public String cloudProductList() {
//        RestTemplate restTemplate = new RestTemplate();
//        URI uri = URI.create("http://localhost:8090/products");
//        return restTemplate.getForObject(uri, String.class);
//    }

    public String fallbackMessage() {
        return "Test";
    }

    @PostMapping("/project/createProject")
    public Project newProject(@RequestBody Project proj){
        return projectRepo.save(proj);
    }

    @GetMapping("/project/getProjects")
    public List<Project> getProjects(){
        return projectRepo.findAll();
    }

    @GetMapping("/project/getProjects/timeout/{timeout}")
    public List<Project> getProjects(@PathVariable("timeout") Long timeout) throws InterruptedException {
        Thread.sleep(timeout);
        return projectRepo.findAll();
    }

    @DeleteMapping("/project/delete")
    public void deleteProject(@RequestParam Long id){
        projectRepo.deleteById(id);
    }

    @PutMapping("/project/editRestrictions")
    public Project editProjectRestrict(@RequestParam Long id, @RequestBody List<String> restrict){
        Project p = projectRepo.findById(id).get();
        p.setProgramRestrictions(restrict);
        return projectRepo.save(p);
    }

    @PutMapping("/project/setDueDate")
    public Project setDueDate(@RequestParam String date, @RequestParam Long id){
        Project p = projectRepo.findById(id).get();
        p.setDueDate(date);
        return projectRepo.save(p);
    }

    @DeleteMapping("/project/removeStudent")
    public Project removeStudent(@RequestParam Long stuID, @RequestParam Long projID){
        Project p = projectRepo.findById(projID).get();
        Student s = studentRepo.findById(stuID).get();
        if (p.getStudents().contains(s)){
            p.removeStudent(s);
        }
        return projectRepo.save(p);
    }

    @GetMapping("/project/listByStatus")
    public List<Project> listProjects(@RequestParam String status){
        return projectRepo.findByStatus(status);
    }
}
