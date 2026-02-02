package project.teaming.invite.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.teaming.invite.entity.Invite;
import project.teaming.invite.exception.AlreadyProjectMemberException;
import project.teaming.invite.exception.NotInviteOwnerException;
import project.teaming.member.entity.Member;
import project.teaming.member.repository.MemberRepository;
import project.teaming.member.service.MemberService;
import project.teaming.project.entity.Project;
import project.teaming.project.exception.ProjectNotFoundException;
import project.teaming.project.repository.ProjectRepository;
import project.teaming.project.service.ProjectService;

@Component
@RequiredArgsConstructor
public class InviteValidator {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ProjectService projectService;
    private final MemberService memberService;

    public Member validateInviter(String username, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("프로젝트 없음"));

        if (!project.getProjectManager().equals(username)) {
            throw new NotInviteOwnerException("프로젝트 팀장만 초대 가능");
        }

        return memberService.findMemberByUsernameOrElseThrow(username);
    }

    public Member validateInvitee(Invite invite, String username) {
        if (!invite.getProjectMember().getUsername().equals(username)) {
            throw new NotInviteOwnerException("본인만 초대를 수락하거나 거절할 수 있습니다.");
        }

        Project project = invite.getProject();
        Member projectMember = invite.getProjectMember();
        if (project.getProjectMember().contains(projectMember)) {
            throw new AlreadyProjectMemberException("이미 프로젝트의 멤버입니다.");
        }

        return memberService.findMemberByUsernameOrElseThrow(username);
    }
}
