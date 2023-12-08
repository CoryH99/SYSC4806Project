package sysc4806.project.demo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Entity(name="project")
public class Project {

    public final static String ACTIVE_PROJ = "ACTIVE";
    public final static String ARCHIVE = "ARCHIVED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int numStudents;
    private int currentStudents;
    private String dueDate;
    private String status;

    private String file;

    @JsonBackReference
    @ManyToOne
    private Professor professor;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Student> students;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "programRestrictions", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "programRestrictions", nullable = false)
    private List<String> programRestrictions;

    public void setId(Long id) {
        this.id = id;
    }

    public Project(){
        this.currentStudents = 0;
    }

    public Project(String name, String description, Professor professor, String programRestrictions, String dueDate, int numStudents) {
        this.name = name;
        this.professor = professor;
        this.description = description;
        this.programRestrictions = List.of(programRestrictions.split(", "));
        this.numStudents = numStudents;
        this.dueDate = dueDate;
        this.currentStudents = 0;
    }

    //For testing only
    public Project(String name, String description){
        this.name = name;
        this.description = description;
    }

    public Project(String name, String description, String status){
        this.name = name;
        this.description = description;
        this.status = status;
        this.currentStudents = 0;
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

    public void addProgramRestriction(String programRestriction) {
        this.programRestrictions.add(programRestriction);
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public void setCurrentStudents(int currentStudents) {
        this.currentStudents = currentStudents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setReport(String file) throws IOException {
        this.file = file.substring(file.lastIndexOf("\\")+1);
    }

    public String getReport(){
        return file;
    }

    public void removeStudent(Student s){
        this.currentStudents--;
        students.remove(s);
    }

    public void addStudent(Student s){
        this.currentStudents++;
        students.add(s);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", numStudents=" + numStudents +
                ", currentStudents=" + currentStudents +
                ", dueDate='" + dueDate + '\'' +
                ", status='" + status + '\'' +
                ", professor=" + professor +
                ", students=" + students +
                ", programRestrictions=" + programRestrictions +
                '}';
    }
}
