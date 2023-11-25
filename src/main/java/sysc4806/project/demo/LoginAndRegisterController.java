package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginAndRegisterController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProfessorRepository profRepo;

    @GetMapping("/")
    public String homeView(Model model){
        return "home";
    }

    @GetMapping("/loginStudent")
    public String loginStudent(Model model){
        model.addAttribute("numberOfStudents", studentRepo.count());
        return "loginStudent";
    }

    @GetMapping("/loginProfessor")
    public String loginProfessor(Model model){
        model.addAttribute("numberOfProfessors", profRepo.count());
        return "loginProf";
    }
}
