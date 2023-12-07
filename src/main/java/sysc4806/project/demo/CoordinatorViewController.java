package sysc4806.project.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sysc4806.project.demo.messages.Message;
import sysc4806.project.demo.forms.MessageForm;
import sysc4806.project.demo.messages.MessageRepository;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@EnableHystrix
public class CoordinatorViewController {

    Logger logger = LoggerFactory.getLogger(CoordinatorViewController.class);

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private StudentRepository studRepo;

    @Autowired
    private MessageRepository mRepo;

    @GetMapping("/coordinatorView")
    @HystrixCommand(fallbackMethod="timeoutView")
    public String coordinatorView(Model model){

        model.addAttribute("projects", projectRepo.findByStatus(Project.ACTIVE_PROJ));
        model.addAttribute("students_no_project", studRepo.findByProjectIsNull());

        model.addAttribute("messageForm", new MessageForm());

        return "CoordinatorUI";
    }

    @PostMapping("/coordinator/sendMessage")
    public String sendStudMessage(@ModelAttribute MessageForm messageForm, Model model){

        // Get the current date as a string
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date today = new Date();
        String dateStr = formatter.format(today);

        long studId = messageForm.getId();

        String level = Message.URGENT_STATUS;
        Message message = new Message(dateStr, level, messageForm.getContent());

        Student targetStud = studRepo.findById(studId);

        message.setReceiver(targetStud);
        targetStud.addMessage(message);

        mRepo.save(message);
        studRepo.save(targetStud);

        model.addAttribute("projects", projectRepo.findByStatus(Project.ACTIVE_PROJ));
        model.addAttribute("students_no_project", studRepo.findByProjectIsNull());

        return "redirect:/coordinatorView";
    }

    private String timeoutView(@PathVariable("timeout") Long timeout, Model model){
        return "ErrorUI";
    }
}
