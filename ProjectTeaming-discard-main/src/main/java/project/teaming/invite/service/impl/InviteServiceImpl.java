package project.teaming.invite.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.teaming.invite.dto.AcceptInviteRequestDto;
import project.teaming.invite.dto.InviteRequestDto;
import project.teaming.invite.entity.Invite;
import project.teaming.invite.exception.InviteNotFoundException;
import project.teaming.invite.repository.InviteRepository;
import project.teaming.invite.service.InviteService;
import project.teaming.invite.utils.InviteGenerator;
import project.teaming.invite.utils.InviteValidator;
import project.teaming.member.entity.Member;
import project.teaming.member.repository.MemberRepository;
import project.teaming.member.service.MemberService;
import project.teaming.project.entity.Project;
import project.teaming.project.repository.ProjectRepository;
import project.teaming.project.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {

    private final InviteRepository inviteRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final InviteGenerator inviteGenerator;
    private final InviteValidator inviteValidator;
    private final MemberService memberService;
    private final ProjectService projectService;

    @Override
    @Transactional
    public void sendInvite(UserDetails userDetails, InviteRequestDto inviteRequestDto) {

        // 1. 초대하는 멤버가 프로젝트 팀장인지 확인
        Member projectManager = inviteValidator.validateInviter(userDetails.getUsername(), inviteRequestDto.projectId());

        // 2. 초대받을 멤버 조회
        Member projectMember = memberService.findMemberByUsernameOrElseThrow(inviteRequestDto.projectMemberUsername());

        // 3. 프로젝트 조회
        Project project = projectService.findProjectByIdOrElseThrow(inviteRequestDto.projectId());

        // 4. 초대 객체 생성 및 저장
        Invite invite = inviteGenerator.generateInvite(projectManager, projectMember, project);

        inviteRepository.save(invite);
    }

    @Override
    @Transactional
    public void acceptInvite(UserDetails userDetails, AcceptInviteRequestDto dto) {
        // 1. 초대 정보 조회
        Invite invite = findInviteByIdOrElseThrow(dto.inviteId());

        // 2. 초대 수락 자격 확인 (본인만 수락 가능)
        Member projectMember = inviteValidator.validateInvitee(invite, userDetails.getUsername());

        Project project = invite.getProject();

        // 3. 초대 수락 처리
        invite.accept();

        project.addMember(projectMember);

        // 4. 저장
        inviteRepository.save(invite);
        projectRepository.save(project);
    }

    @Transactional
    @Override
    public void refuseInvite(UserDetails userDetails, AcceptInviteRequestDto dto) {
        // 1. 초대 정보 조회
        Invite invite = findInviteByIdOrElseThrow(dto.inviteId());

        // 2. 초대 수락 자격 확인 (본인만 수락 가능)
        inviteValidator.validateInvitee(invite, userDetails.getUsername());

        inviteRepository.delete(invite);
    }

    @Override
    public List<Invite> getAllInvitesByUsername(UserDetails userDetails) {
        return inviteRepository.findAll().stream()
            .filter(invite -> invite.getProjectMember().getUsername().equals(userDetails.getUsername()))
            .collect(Collectors.toList());
    }

    @Override
    public Invite findInviteByIdOrElseThrow(Long id) {

        return inviteRepository.findById(id)
                .orElseThrow(() -> new InviteNotFoundException("초대가 존재하지 않습니다."));
    }
}
