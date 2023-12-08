package sysc4806.project.demo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import sysc4806.project.demo.messages.Message;

@Entity
public class Student {

    public static final String STUDENT_ROLE = "STUDENT";

    @Id
    @GeneratedValue
    private Long id;

    private String program;
    private String name;
    @JsonBackReference
    @ManyToOne
    private Project project;
    private String timeslot;

    @NotNull
    private String password;

    @JsonManagedReference
    @OneToMany
    private List<Message> messages;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Student() {this.messages = new ArrayList<>();}

    public Student(String name, String program, String timeslot, String password) {
        this.name = name;
        this.program = program;
        this.timeslot = timeslot;
        this.password = password;
        this.messages = new ArrayList<>();
    }

    public Student(String name, String program, String password){
        this(name, program,"", password);
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {return password;}


    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(0, message);
    }

    @Override   
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", program='" + program + '\'' +
                ", name='" + name + '\'' +
                ", project=" + project +
                ", timeslot='" + timeslot + '\'' +
                '}';
    }
}

