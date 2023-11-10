package sysc4806.project.demo;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String description;
    private String programRestrictions;
    private int numStudents;

    public void setId(Long id) {
        this.id = id;
    }

    public Project(){}

    public Project(String description, String programRestrictions, int numStudents) {
        this.description = description;
        this.programRestrictions = programRestrictions;
        this.numStudents = numStudents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgramRestrictions() {
        return programRestrictions;
    }

    public void setProgramRestrictions(String programRestrictions) {
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

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", programRestrictions='" + programRestrictions + '\'' +
                ", numStudents=" + numStudents +
                '}';
    }
}
