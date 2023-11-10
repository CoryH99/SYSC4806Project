package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @PostMapping("/newStudent")
    @ResponseBody
    public Student newStudent(@RequestBody Student s){
        return studentRepo.save(s);
    }

    @PutMapping("/timeslot")
    @ResponseBody
    public void changeAvailable(@RequestParam String slot, @RequestParam Long id){
        Student s = studentRepo.findById(id).get();
        s.setTimeslot(slot);
        studentRepo.save(s);
    }

    @PutMapping("/assignProject")
    public void assignProject(@RequestParam Long studentID, @RequestParam Long projectID){
        if (studentRepo.existsById(studentID) && projectRepo.existsById(projectID)){
            Student s = studentRepo.findById(studentID).get();
            Project p = projectRepo.findById(projectID).get();
            s.setProject(p);
        }
    }
}
