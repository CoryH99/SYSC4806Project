package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sysc4806.project.demo.forms.MessageForm;

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

    @GetMapping("/registerStudent")
    public String viewRegisterStudent(Model model){

        model.addAttribute("studentForm", new Student());

        return "registerStudent";
    }

    @PostMapping("/registerStudent/register")
    public String registerStudent(@ModelAttribute Student studentForm, Model model){

        Student newStud = studentRepo.save(studentForm);
        String send_to = "/studentView/" + newStud.getId();

        return "redirect:" + send_to;
    }

    @GetMapping("/registerProfessor")
    public String viewRegisterProf(Model model){

        model.addAttribute("professorForm", new Professor());

        return "registerProfessor";
    }

    @PostMapping("/registerProfessor/register")
    public String registerProf(@ModelAttribute Professor professorForm, Model model){

        Professor newProf = profRepo.save(professorForm);
        String send_to = "/professorView/" + newProf.getId();

        return "redirect:" + send_to;
    }

}
