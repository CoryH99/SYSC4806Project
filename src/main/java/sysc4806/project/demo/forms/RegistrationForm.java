package sysc4806.project.demo.forms;

public class RegistrationForm {

    private long id;
    private String name;

    private String program;

    private String profPassword;

    private String password;

    public String getName(){return name;}

    public void setName(String name) {this.name = name;}

    public String getProfPassword(){return profPassword;}

    public void setProfPassword(String profPassword){this.profPassword = profPassword;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public Long getId() { return id;}

    public void setId(Long id){this.id=id;}

    public String getProgram(){return program;}

    public void setProgram(String program){this.program = program;}
}
