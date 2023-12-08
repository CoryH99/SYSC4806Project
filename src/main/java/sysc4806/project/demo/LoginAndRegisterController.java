package sysc4806.project.demo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import sysc4806.project.demo.forms.LoginForm;
import sysc4806.project.demo.forms.MessageForm;

import java.util.Optional;

@Controller
public class LoginAndRegisterController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProfessorRepository profRepo;


//    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homeView(Model model){
        return "home";
    }

    @GetMapping("/loginStudent")
    public String loginStudent(@RequestParam("error") Optional<String> error, Model model){
        model.addAttribute("numberOfStudents", studentRepo.count());
        error.ifPresent(s -> model.addAttribute("error", s));
        model.addAttribute("loginForm", new LoginForm());
        return "loginStudent";
    }

    @PostMapping("/loginStudent")
    public String processLoginStudent(@ModelAttribute LoginForm loginForm, HttpServletResponse response) {
        Optional<Student> optionalStudent = studentRepo.findById(loginForm.getId());

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            // Compare the entered password with the password associated with the student
            if (loginForm.getPassword().equals(student.getPassword())) {

                Cookie cookie = new Cookie("role", Student.STUDENT_ROLE);
                Cookie anotherCookie = new Cookie("studId", loginForm.getId().toString());
                response.addCookie(cookie);
                response.addCookie(anotherCookie);

                return "redirect:/studentView/" + loginForm.getId();
            } else {
                // Passwords do not match, return to login page with an error message
                return "redirect:/loginStudent?error=Incorrect password";
            }
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

    @PostMapping("/loginProfessor")
    public String processLoginProfessor(@RequestParam Long id, @RequestParam String profPassword) {
        Optional<Professor> optionalProfessor = profRepo.findById(id);

        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();
            // Compare the entered password with the password associated with the student
            if (profPassword.equals(professor.getProfPassword())) {
                return "redirect:/ProfessorUI";
            } else {
                // Passwords do not match, return to login page with an error message
                return "redirect:/loginProfessor?error=Incorrect password";
            }
        } else {
            // Student not found, return to login page with an error message
            return "redirect:/loginProfessor?error=Professor not found";
        }
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
