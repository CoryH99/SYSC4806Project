package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void assignProject(@RequestParam Long studentID, @RequestParam Long projectID){
        if (studentRepo.existsById(studentID) && projectRepo.existsById(projectID)){
            Student s = studentRepo.findById(studentID).get();
            Project p = projectRepo.findById(projectID).get();
            if (p.getNumStudents() >= p.getCurrentStudents() || p.getStudents().contains(s)){
                //not sure how to return an error
            }
            else {
                s.setProject(p);
            }
        }
        else{
            //not quite sure what to put in for else but oops something went wrong or smth
        }
    }
}
