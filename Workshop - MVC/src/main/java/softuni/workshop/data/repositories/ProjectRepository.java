package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.workshop.data.entities.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String name);

    List<Project> findAllByFinishedTrue();
}
