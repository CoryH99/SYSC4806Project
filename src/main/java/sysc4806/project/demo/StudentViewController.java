package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sysc4806.project.demo.forms.TimeslotForm;
import sysc4806.project.demo.presentationHandling.TimeSlotHandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        model.addAttribute("timeslotForm", new TimeslotForm());

        if (studentRepo.findById(studId).isPresent()) {
            Student student = studentRepo.findById(studId).get();
            model.addAttribute("student", student);
            model.addAttribute("timeslot", TimeSlotHandling.fromTimeslotToMap(student.getTimeslot()));

        } else {
            return "redirect:/";
        }
        model.addAttribute("projects", projectRepo.findByStatus(Project.ACTIVE_PROJ));

        return "StudentUI";
    }

    @PostMapping("/studentView/createTimeSlot/{id}")
    public String createTimeSlot(@ModelAttribute TimeslotForm timeslot, @PathVariable("id") Long studId, Model model){
        List<String> weekSchedule = new ArrayList<>();
        weekSchedule.add(timeslot.getMonday());
        weekSchedule.add(timeslot.getTuesday());
        weekSchedule.add(timeslot.getWednesday());
        weekSchedule.add(timeslot.getThursday());
        weekSchedule.add(timeslot.getFriday());
        String new_timeslot = TimeSlotHandling.convertToTimeslot(weekSchedule);

        if (studentRepo.findById(studId).isPresent()) {
            Student student = studentRepo.findById(studId).get();
            student.setTimeslot(new_timeslot);
            studentRepo.save(student);
        }

        return "redirect:/studentView/" + studId;
    }

}
