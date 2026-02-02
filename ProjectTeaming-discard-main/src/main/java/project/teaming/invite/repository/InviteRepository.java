package project.teaming.invite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.teaming.invite.entity.Invite;

public interface InviteRepository extends JpaRepository<Invite, Long> {
}
