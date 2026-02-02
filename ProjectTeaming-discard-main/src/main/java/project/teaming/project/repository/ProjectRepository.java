package project.teaming.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.teaming.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
