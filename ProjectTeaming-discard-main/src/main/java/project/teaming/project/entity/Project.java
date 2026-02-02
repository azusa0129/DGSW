package project.teaming.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import project.teaming.invite.exception.AlreadyProjectMemberException;
import project.teaming.member.entity.Member;

import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String title; //제목
    @Column(length = 500)
    private String content; // 콘텐츠 설명
    @Column(nullable = false)
    private String projectManager; // 본명

    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "member_username")
    )
    private List<Member> projectMember; // 배열

    public void update(String title, String content, String projectManager) {
        this.title = title;
        this.content = content;
        this.projectManager = projectManager;
    }

    public void addMember(Member member) {
        if (this.projectMember.contains(member)) {
            throw new AlreadyProjectMemberException("이미 프로젝트의 멤버입니다.");
        }
        this.projectMember.add(member);
    }
}

