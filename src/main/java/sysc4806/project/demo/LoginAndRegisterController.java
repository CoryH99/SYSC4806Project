package sysc4806.project.demo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sysc4806.project.demo.forms.LoginForm;
import sysc4806.project.demo.forms.RegistrationForm;
import sysc4806.project.demo.security.HandleUsers;

import java.util.Optional;

@Controller
public class LoginAndRegisterController {

    Logger logger = LoggerFactory.getLogger(CoordinatorViewController.class);

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ProfessorRepository profRepo;


//    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homeView(Model model, HttpServletResponse response){
        response.addCookie(HandleUsers.deleteCookie("role"));
        response.addCookie(HandleUsers.deleteCookie("id"));
        return "home";
    }

    @GetMapping("/loginStudent")
    public String loginStudent(@RequestParam("error") Optional<String> error, Model model){
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

                response.addCookie(HandleUsers.createCookie("role", Student.STUDENT_ROLE));
                response.addCookie(HandleUsers.createCookie("id", loginForm.getId().toString()));

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
    public String loginProfessor(@RequestParam("error") Optional<String> error, Model model){
        error.ifPresent(s -> model.addAttribute("error", s));
        model.addAttribute("loginForm", new LoginForm());
        return "loginProf";
    }

    @PostMapping("/loginProfessor")
    public String processLoginProfessor(@ModelAttribute LoginForm loginForm, HttpServletResponse response) {
        Optional<Professor> optionalProfessor = profRepo.findById(loginForm.getId());

        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();
            // Compare the entered password with the password associated with the student
            if (loginForm.getPassword().equals(professor.getProfPassword())) {

                response.addCookie(HandleUsers.createCookie("role", Professor.PROF_ROLE));
                response.addCookie(HandleUsers.createCookie("id", loginForm.getId().toString()));
                return "redirect:/professorView/" + loginForm.getId();
            } else {
                // Passwords do not match, return to login page with an error message
                logger.warn("Incorrect password: " + loginForm.getPassword() + " instead of " + professor.getProfPassword());
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
    public String registerStudent(@ModelAttribute RegistrationForm studentForm, Model model){

        Student student = new Student(studentForm.getName(),studentForm.getProgram(), studentForm.getPassword());
        studentRepo.save(student);

        return "redirect:/studentView/" + studentForm.getId();
    }

    @GetMapping("/registerProfessor")
    public String viewRegisterProf(Model model){

        model.addAttribute("professorForm", new Professor());

        return "registerProfessor";
    }

    @PostMapping("/registerProfessor/register")
    public String registerProf(@ModelAttribute RegistrationForm professorForm, Model model, HttpServletRequest request){

        Professor prof = new Professor(professorForm.getName(),professorForm.getProfPassword());

        profRepo.save(prof);
        String send_to = "/professorView/" + professorForm.getId();
        System.out.println("register success");

        return "redirect:" + send_to;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session (clear user authentication)
        request.getSession().invalidate();

        // Clear cookies related to authentication (if any)
        clearAuthenticationCookies(response);

        System.out.println("logout success");
        // Redirect to the login page or any other appropriate page
        return "home";
    }

    private void clearAuthenticationCookies(HttpServletResponse response) {
        // Clear the "role" and "studId" cookies
        Cookie roleCookie = new Cookie("role", null);
        roleCookie.setMaxAge(0);
        roleCookie.setPath("/");
        response.addCookie(roleCookie);

        Cookie studIdCookie = new Cookie("studId", null);
        studIdCookie.setMaxAge(0);
        studIdCookie.setPath("/");
        response.addCookie(studIdCookie);
    }
}
