package sysc4806.project.demo.security;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sysc4806.project.demo.Professor;
import sysc4806.project.demo.ProfessorRepository;
import sysc4806.project.demo.Student;
import sysc4806.project.demo.StudentRepository;

@Service
public class HandleUsers {


    public static boolean checkIfStudent(String givenId, String role, Long studId){
        return Long.parseLong(givenId) != studId || !role.equals(Student.STUDENT_ROLE);
    }

    public static boolean checkIfProf(String givenId, String role, Long profId){
        return Long.parseLong(givenId) != profId || !role.equals(Professor.PROF_ROLE);
    }

    public static Cookie createCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        return cookie;
    }

    public static Cookie deleteCookie(String key){
        Cookie cookieToDelete = createCookie(key, "");
        cookieToDelete.setMaxAge(0);
        return cookieToDelete;
    }

}
