package sysc4806.project.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProjectRepository projectRepo;

    Logger logger = LoggerFactory.getLogger(CoordinatorViewController.class);

    @PostMapping("/student/createStudent")
    @ResponseBody
    public Student newStudent(@RequestBody Student s){
        return studentRepo.save(s);
    }

    @PutMapping("/student/timeslot")
    @ResponseBody
    public Student changeAvailable(@RequestParam String slot, @RequestParam Long id){
        Student s = studentRepo.findById(id).get();
        s.setTimeslot(slot);
        return studentRepo.save(s);
    }

    @GetMapping("/student/getStudents")
    public List<Student> getStudents(){ return studentRepo.findAll();}

    @PutMapping("/student/assignProject")
    public ResponseEntity<Student> assignProject(@RequestParam Long studentID, @RequestParam Long projectID){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (studentRepo.existsById(studentID) && projectRepo.existsById(projectID)){
            Student s = studentRepo.findById(studentID).get();
            Project p = projectRepo.findById(projectID).get();
            if (p.getNumStudents() == p.getCurrentStudents() || p.getStudents().contains(s)){
                logger.error("NOT ADDING STUDENT, because: studentInProject:" + p.getStudents().contains(s) + " or projectFull: " + (p.getNumStudents() <= p.getCurrentStudents()));
                return new ResponseEntity<Student>(null, headers, HttpStatus.EXPECTATION_FAILED);
            }
            else {
                s.setProject(p);
                p.addStudent(s);
                projectRepo.save(p);
                return new ResponseEntity<Student>(studentRepo.save(s), headers, HttpStatus.OK);
            }
        }
        else{
            logger.error("NOT ADDING STUDENT, STUDENT OR PROJECT DOES NOT EXIST");
            return new ResponseEntity<Student>(null, headers, HttpStatus.NOT_FOUND);
        }
    }
}
