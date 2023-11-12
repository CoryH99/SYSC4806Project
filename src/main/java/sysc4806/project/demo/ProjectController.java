package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private StudentRepository studentRepo;

    @PostMapping("/project/create")
    public Project newProject(@RequestBody Project proj){
        return projectRepo.save(proj);
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
