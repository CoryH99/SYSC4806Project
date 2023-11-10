package sysc4806.project.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findById(long id);

    List<Project> findAll();

}
