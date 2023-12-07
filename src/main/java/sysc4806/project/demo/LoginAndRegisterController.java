package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import sysc4806.project.demo.forms.MessageForm;

import java.util.Optional;

@Controller
public class LoginAndRegisterController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProfessorRepository profRepo;


    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homeView(Model model){
        return "home";
    }

    @GetMapping("/loginStudent")
    public String loginStudent(Model model){
        model.addAttribute("numberOfStudents", studentRepo.count());
        return "loginStudent";
    }

    @PostMapping("/loginStudent")
    public String processLoginStudent(@RequestParam Long id, @RequestParam String password) {
        Optional<Student> optionalStudent = studentRepo.findById(id);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            return null;
        } else {
            // Student not found, return to login page with an error message
            return "redirect:/loginStudent?error=Student not found";
        }
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
        // Save the student
        studentRepo.save(studentForm);

        String send_to = "/studentView/" + studentForm.getId();

        return "redirect:" + send_to;
    }

    @GetMapping("/registerProfessor")
    public String viewRegisterProf(Model model){

        model.addAttribute("professorForm", new Professor());

        return "registerProfessor";
    }

    @PostMapping("/registerProfessor/register")
    public String registerProf(@ModelAttribute Professor professorForm, Model model){

        profRepo.save(professorForm);
        String send_to = "/professorView/" + professorForm.getId();

        return "redirect:" + send_to;
    }

}
