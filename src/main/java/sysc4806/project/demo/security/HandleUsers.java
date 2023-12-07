package sysc4806.project.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sysc4806.project.demo.Professor;
import sysc4806.project.demo.ProfessorRepository;
import sysc4806.project.demo.Student;
import sysc4806.project.demo.StudentRepository;

@Service
public class HandleUsers {

    @Autowired
    private StudentRepository studRepo;

    @Autowired
    private ProfessorRepository profRepo;

    public boolean checkIfStudent(String role){
        return role.equals(Student.STUDENT_ROLE);
    }

    public boolean checkIfProf(String role){
        return role.equals(Professor.PROF_ROLE);
    }

}
