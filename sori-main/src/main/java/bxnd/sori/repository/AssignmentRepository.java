package bxnd.sori.repository;

import bxnd.sori.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment> findByTitle(String title);

    Assignment findByDeadline(LocalDateTime deadline);
}
