package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentViewController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping("/studentView/{id}")
    public String specificStudentView(@PathVariable("id") Long studId, Model model){

        // Constants
        model.addAttribute("URGENT_LEVEL", "Urgent");

        if (studentRepo.findById(studId).isPresent()) {
            Student student = studentRepo.findById(studId).get();
            model.addAttribute("student", student);
        } else {
            return "redirect:/";
        }
        model.addAttribute("projects", projectRepo.findAll());

        return "StudentUI";
    }

}
