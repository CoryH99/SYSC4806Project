package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TestController {
    @Autowired
    private ProjectRepository repo;

    @Autowired
    private StudentRepository studRepo;

    @PostMapping("/testProj")
    public Project createProj(@RequestBody Project proj){
        return repo.save(proj);
    }

    @GetMapping("/getProj")
    public List<Project> getProjects(){
        return repo.findAll();
    }

    @GetMapping("/getStudents")
    public List<Student> getStudents(){ return studRepo.findAll();}

    @PostMapping("/createStudent")
    public Student createStudent(@RequestBody Student student){
        return studRepo.save(student);
    }

    @PutMapping("/assignProject")
    public ResponseEntity<Student> assignProject(@RequestParam Long studId, @RequestParam Long projId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Student stud;
        Project proj;


        Optional<Student> target = studRepo.findById(studId);

        if (target.isPresent()){
            stud = target.get();
            Optional<Project> projTarget = repo.findById(projId);

            if(projTarget.isPresent()){
                proj = projTarget.get();
                stud.setProject(proj);
                return new ResponseEntity<Student>(studRepo.save(stud), headers, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Student>(null, headers, HttpStatus.NOT_FOUND);
    }

}
