package sysc4806.project.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ProfessorController {

    Logger logger = LoggerFactory.getLogger(CoordinatorViewController.class);

    @Autowired
    private ProfessorRepository profRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @PostMapping("/professor/createProfessor")
    @ResponseBody
    public Professor newProfessor(@RequestBody Professor p){
        return profRepo.save(p);
    }


    @PostMapping("/{professorId}/projects")
    public ResponseEntity<String> addProjectToProfessor(
            @PathVariable Long professorId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam int numStudents,
            @RequestParam String dueDate) {

        // Find the professor
        Optional<Professor> optionalProfessor = profRepo.findById(professorId);

        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();

            // Create a new Project
            Project project = new Project(name, description, professor, "", dueDate, numStudents);

            // Save the project
            projectRepo.save(project);

            // Add the project to the professor's list of projects
            professor.addProject(project);
            profRepo.save(professor);

            return new ResponseEntity<>("Project added to professor successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Professor not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/professor/getProfessors")
    public List<Professor> getProfessors(){
        return profRepo.findAll();
    }

    @PutMapping("/professor/setAvailability")
    @ResponseBody
    public Professor changeAvailable(@RequestParam String available, @RequestParam Long id){
        Professor prof = profRepo.findById(id).get();
        prof.setAvailability(available);
        return profRepo.save(prof);
    }
    @PutMapping("/professor/assignProject")
    public ResponseEntity<Professor> assignProject(@RequestParam Long profID, @RequestParam Long projectID){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        if (profRepo.existsById(profID) && projectRepo.existsById(projectID)){

            try {
                Professor prof = profRepo.findById(profID).get();
                Project proj = projectRepo.findById(projectID).get();
                proj.setProfessor(prof);
                prof.addProject(proj);
                projectRepo.save(proj);
                Professor saved_prof = profRepo.save(prof);
                return new ResponseEntity<Professor>(saved_prof, headers, HttpStatus.OK);
            } catch (HttpMessageNotWritableException e){
                logger.error(e.getMessage());
            }
            return new ResponseEntity<Professor>(null, headers, HttpStatus.EXPECTATION_FAILED);
        }
        else{
            return new ResponseEntity<Professor>(null, headers, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/professor/setProjects")
    public ResponseEntity<Professor> setProject(@RequestBody List<Project> proj, @RequestParam Long profID){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (profRepo.existsById(profID)){
            Professor prof = profRepo.findById(profID).get();
            prof.setProjects(proj);
            return new ResponseEntity<Professor>(profRepo.save(prof), headers, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Professor>(null, headers, HttpStatus.NOT_FOUND);
        }
    }
}
