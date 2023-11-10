package sysc4806.project.demo;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity(name="project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int numStudents;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "programRestrictions", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "programRestrictions", nullable = false)
    private List<String> programRestrictions;

    public void setId(Long id) {
        this.id = id;
    }

    public Project(){}

    public Project(String name, String description, String programRestrictions, int numStudents) {
        this.description = description;
        this.programRestrictions = List.of(programRestrictions.split(", "));
        this.numStudents = numStudents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getProgramRestrictions() {
        return programRestrictions;
    }

    public void setProgramRestrictions(List<String> programRestrictions) {
        this.programRestrictions = programRestrictions;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
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



}
