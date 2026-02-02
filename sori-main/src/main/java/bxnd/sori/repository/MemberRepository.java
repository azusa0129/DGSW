package bxnd.sori.repository;

import bxnd.sori.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserNm(String userNm);
    Optional<Member> findByEmail(String email);
}