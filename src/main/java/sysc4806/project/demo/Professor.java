package sysc4806.project.demo;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import sysc4806.project.demo.presentationHandling.TimeSlotHandling;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String availability;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Project> projects;

    public Professor(String name, String availability){
        this.name = name;
        this.availability = availability;
        this.projects = new ArrayList<>();
    }

    public Professor() {
        this.projects = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability() {
        if (availability != null && !availability.isEmpty()){
            return availability;
        } else {
            return TimeSlotHandling.DEFAULT_TIME;
        }
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void addProject(Project project){
        projects.add(project);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void removeProject(Project project){
        this.projects.remove(project);
    }

    public List<Project> getActiveProjects(){
        List<Project> active = new ArrayList<>();

        for (Project proj : this.projects){
            if (proj.getStatus().equals(Project.ACTIVE_PROJ)){
                active.add(proj);
            }
        }

        return active;

    }
    
    public List<Project> getArchivedProjects(){
        List<Project> archived = new ArrayList<>();
        for (Project proj : this.projects){
            if (proj.getStatus().equals(Project.ARCHIVE)){
                archived.add(proj);
            }
        }
        return archived;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", availability='" + availability + '\'' +
                ", projects=" + projects +
                '}';
    }
}
