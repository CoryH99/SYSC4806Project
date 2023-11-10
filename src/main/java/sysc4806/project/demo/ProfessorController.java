package sysc4806.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfessorController {

    @Autowired
    private ProfessorRepository profRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @PostMapping("/professor/newProf")
    @ResponseBody
    public Professor newProfessor(@RequestBody Professor p){
        return profRepo.save(p);
    }

    @PutMapping("/professor/available")
    @ResponseBody
    public Professor changeAvailable(@RequestParam String available, @RequestParam Long id){
        Professor prof = profRepo.findById(id).get();
        prof.setAvailability(available);
        return profRepo.save(prof);
    }
    @PutMapping("/professor/assignProject")
    public void assignProject(@RequestParam Long profID, @RequestParam Long projectID){
        if (profRepo.existsById(profID) && projectRepo.existsById(projectID)){
            Professor prof = profRepo.findById(profID).get();
            Project proj = projectRepo.findById(projectID).get();
            prof.addProject(proj);
        }
        else{
            //not quite sure what to put in for else but oops something went wrong or smth
        }
    }

    @PutMapping("/professor/setProjects")
    public void setProject(@RequestBody List<Project> proj, @RequestParam Long profID){
        if (profRepo.existsById(profID)){
            Professor prof = profRepo.findById(profID).get();
            prof.setProjects(proj);
        }
        else{
            //once again, still unsure what to do
        }
    }
}
