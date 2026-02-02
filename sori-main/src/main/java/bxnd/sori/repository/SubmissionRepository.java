package bxnd.sori.repository;

import bxnd.sori.entity.SubmittedAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<SubmittedAssignment, Long> {
    List<SubmittedAssignment> findByTitle(String title);
}
