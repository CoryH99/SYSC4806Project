package sysc4806.project.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {

    Student findById(long id);

    Student getById(long id);

    List<Student> findAll();
}
