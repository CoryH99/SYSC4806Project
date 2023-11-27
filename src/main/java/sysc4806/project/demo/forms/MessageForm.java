package sysc4806.project.demo.forms;

public class MessageForm {

    private long id;
    private String content;

    public MessageForm(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public MessageForm(){}

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
