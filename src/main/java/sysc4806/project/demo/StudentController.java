package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @PostMapping("/student/newStudent")
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

    @PutMapping("/student/assignProject")
    public ResponseEntity<Student> assignProject(@RequestParam Long studentID, @RequestParam Long projectID){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (studentRepo.existsById(studentID) && projectRepo.existsById(projectID)){
            Student s = studentRepo.findById(studentID).get();
            Project p = projectRepo.findById(projectID).get();
            if (p.getNumStudents() >= p.getCurrentStudents() || p.getStudents().contains(s)){
                return new ResponseEntity<Student>(null, headers, HttpStatus.NOT_FOUND);
            }
            else {
                s.setProject(p);
                return new ResponseEntity<Student>(studentRepo.save(s), headers, HttpStatus.OK);
            }
        }
        else{
            return new ResponseEntity<Student>(null, headers, HttpStatus.NOT_FOUND);
        }
    }
}
