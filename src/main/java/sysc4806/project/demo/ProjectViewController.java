package sysc4806.project.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sysc4806.project.demo.forms.BookRoomForm;
import sysc4806.project.demo.messages.Message;
import sysc4806.project.demo.messages.MessageRepository;
import sysc4806.project.demo.presentationHandling.TimeSlotHandling;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProjectViewController {

    Logger logger = LoggerFactory.getLogger(ProjectViewController.class);

    @Autowired
    ProjectRepository projectRepo;

    @Autowired
    MessageRepository mRepo;

    @Autowired
    private StudentRepository studRepo;

    @GetMapping("/projects/{projID}")
    public String projectView(@PathVariable("projID") Long projID, Model model){
        model.addAttribute("proj", projectRepo.findAll());
        return "ProjectPage";
    }

    @GetMapping("/projects/coordinator/{projID}")
    public String coordinatorProjectView(@PathVariable("projID") Long projID, Model model){
        Optional<Project> projectRetrieval = projectRepo.findById(projID);

        if (projectRetrieval.isPresent()){
            Project project = projectRetrieval.get();
            Professor prof = project.getProfessor();

            List<Student> students = project.getStudents();
            List<HashMap<String, String>> studentTimeslots = new ArrayList<>();
            for (Student stud: students){
                studentTimeslots.add(TimeSlotHandling.fromTimeslotToMap(stud.getTimeslot()));
            }

            model.addAttribute("bookRoomForm", new BookRoomForm());
            model.addAttribute("profTimeslot", TimeSlotHandling.fromTimeslotToMap(prof.getAvailability()));
            model.addAttribute("professor", prof);
            model.addAttribute("studentTimeslots", studentTimeslots);
            model.addAttribute("students", students);
            model.addAttribute("proj", project);
            return "CoordinatorProjectView";
        } else {
            return "redirect:/coordinatorView";
        }
    }

    @PostMapping("/projects/coordinator/{projID}/bookRoom")
    public String bookRoom(@PathVariable("projID") Long projID, @ModelAttribute BookRoomForm bookRoomForm, Model model){

        Optional<Project> projectRetrieval = projectRepo.findById(projID);

        if (projectRetrieval.isPresent()){
            Project project = projectRetrieval.get();

            /* Set project presentation time */
            project.setPresentationDetails(bookRoomForm.getRoom() + ": " + bookRoomForm.getTime());
            Project saved_proj = projectRepo.save(project);

            /* Tell the students when the presentation is */
            // Get the current date as a string
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date today = new Date();
            String dateStr = formatter.format(today);
            String level = Message.URGENT_STATUS;

            String content = bookRoomForm.getRoom() + " is booked for " + bookRoomForm.getTime() + " for your project's oral presentation";
            for (Student stud: saved_proj.getStudents()){
                Message message = new Message(dateStr, level, content);
                message.setReceiver(stud);
                stud.addMessage(message);
                logger.info("Sending booking message to: " + stud.getName());
                mRepo.save(message);
                studRepo.save(stud);
            }
        }

        return "redirect:/projects/coordinator/" + projID;

    }

}
