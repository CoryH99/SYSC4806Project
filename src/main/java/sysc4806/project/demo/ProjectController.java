package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired ProfessorRepository profRepo;

    @PostMapping("/project/createProject")
    public Project newProject(@RequestBody Project proj){
        return projectRepo.save(proj);
    }

    @GetMapping("/project/getProjects")
    public List<Project> getProjects(){
        return projectRepo.findAll();
    }

    @DeleteMapping("/project/delete")
    public void deleteProject(@RequestParam Long id){
        if (projectRepo.findById(id).isPresent()) {
            Project target = projectRepo.findById(id).get();
            List<Student> studentsInProj = studentRepo.findByProject(target);

            for (Student stud : studentsInProj){
                stud.setProject(null);
                studentRepo.save(stud);
            }

            if (target.getProfessor() != null){
                Professor prof = target.getProfessor();
                prof.removeProject(target);
                profRepo.save(prof);
            }

            projectRepo.deleteById(id);
        }

    }

    @PutMapping("/project/addRestriction")
    public Project addProjectRestrict(@RequestParam Long id, @RequestParam String restrict){
        Project p = projectRepo.findById(id).get();
        p.addProgramRestriction(restrict);
        return projectRepo.save(p);
    }

    @DeleteMapping("/project/removeRestriction")
    public Project removeProjectRestriction(@RequestParam Long id, @RequestParam String restrict){
        Project p = projectRepo.findById(id).get();
        p.removeProjectRestriction(restrict);
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

    @PutMapping("/project/activateProject")
    public Project activateProject(@RequestParam long projID){
        Project project = projectRepo.findById(projID);

        project.setStatus(Project.ACTIVE_PROJ);
        return projectRepo.save(project);
    }

    @PutMapping("/project/archiveProject")
    public Project archiveProject(@RequestParam long projID){
        Project project = projectRepo.findById(projID);

        project.setStatus(Project.ARCHIVE);
        return projectRepo.save(project);
    }

}
