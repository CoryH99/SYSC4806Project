package sysc4806.project.demo.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import sysc4806.project.demo.Student;

@Entity
public class Message {

    @JsonBackReference
    @ManyToOne
    private Student receiver;
    private String date;
    private String level;
    private String content;
    @Id
    @GeneratedValue
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Student getReceiver() {
        return receiver;
    }

    public void setReceiver(Student receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "receiver=" + receiver +
                ", date='" + date + '\'' +
                ", level='" + level + '\'' +
                ", content='" + content + '\'' +
                ", id=" + id +
                '}';
    }
}
