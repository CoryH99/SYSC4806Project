package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;
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

            // Check if the provided password matches the hashed password
            if (passwordEncoder.matches(password, student.getPasswordHash())) {
                // Passwords match, redirect to student view
                return "redirect:/studentView/" + student.getId();
            } else {
                // Invalid password, return to login page with an error message
                return "redirect:/loginStudent?error=Invalid password";
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

    @GetMapping("/registerStudent")
    public String viewRegisterStudent(Model model){

        model.addAttribute("studentForm", new Student());

        return "registerStudent";
    }

    @PostMapping("/registerStudent/register")
    public String registerStudent(@ModelAttribute Student studentForm, @RequestParam String password, Model model){

        // Hash the password
        String hashedPassword = passwordEncoder.encode(password);

        // Set the hashed password in the student entity
        studentForm.setPassword(hashedPassword);

        // Save the student
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
