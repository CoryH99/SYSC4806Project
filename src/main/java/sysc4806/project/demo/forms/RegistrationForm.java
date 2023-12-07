package sysc4806.project.demo.forms;

public class RegistrationForm {

    private long id;
    private String content;

    public RegistrationForm(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public RegistrationForm(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
