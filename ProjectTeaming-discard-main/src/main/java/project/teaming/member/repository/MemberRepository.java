package project.teaming.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.teaming.member.entity.Major;
import project.teaming.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    List<Member> findAllByMainMajor(Major mainMajor);
    List<Member> findAllBySubMajor(Major mainMajor);
    List<Member> findAllByMainMajorAndSubMajor(Major mainMajor, Major subMajor);

    Optional<Member> findByUsername(String username);

    @Query("SELECT m.name FROM Member m")
    List<String> findAllNames();

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByGrade(int grade);
}
