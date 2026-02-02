package project.teaming.invite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.teaming.member.entity.Member;
import project.teaming.project.entity.Project;

@Entity
@Getter
@Table(name = "Invites")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "manager", nullable = false)
    @ManyToOne
    private Member projectManager;

    @JoinColumn(name = "member", nullable = false)
    @ManyToOne
    private Member projectMember;

    @JoinColumn(name = "project", nullable = false) // 어떤 프로젝트 초대인지
    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private boolean accepted = false;

    public void accept() {
        this.accepted = true;
    }

    public Invite(Member projectManager, Member projectMember, Project project) {
        this.projectManager = projectManager;
        this.projectMember = projectMember;
        this.project = project;
        this.accepted = false;
    }
}
