package project.teaming.invite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.teaming.invite.dto.AcceptInviteRequestDto;
import project.teaming.invite.dto.InviteRequestDto;
import project.teaming.invite.dto.InviteResponseDto;
import project.teaming.invite.mapper.InviteMapper;
import project.teaming.invite.service.InviteService;
import project.teaming.member.repository.MemberRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invites")
@CrossOrigin(origins = "*")
public class InviteController {

    private final InviteService inviteService;
    private final MemberRepository memberRepository;

    // 1. 초대 보내기 (POST /invites)
    @PostMapping
    public ResponseEntity<Void> sendInvite(
            @RequestBody InviteRequestDto inviteRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        inviteService.sendInvite(userDetails, inviteRequestDto);
        return ResponseEntity.ok().build();
    }

    // 2. 초대 수락 (POST /invites/accept)
    @PostMapping("/accept")
    public ResponseEntity<Void> acceptInvite(
            @RequestBody AcceptInviteRequestDto acceptInviteRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        inviteService.acceptInvite(userDetails, acceptInviteRequestDto);
        return ResponseEntity.ok().build();
    }

    // 3. 초대 거절 (DELETE /invites/refuse)
    @DeleteMapping("/refuse")
    public ResponseEntity<Void> refuseInvite(
            @RequestBody AcceptInviteRequestDto acceptInviteRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        inviteService.refuseInvite(userDetails, acceptInviteRequestDto);
        return ResponseEntity.noContent().build(); // DELETE 요청에 적절한 응답
    }

    @GetMapping
    public ResponseEntity<List<InviteResponseDto>> getInvitesByUsername(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<InviteResponseDto> inviteDtos = inviteService.getAllInvitesByUsername(userDetails).stream()
                .map(invite -> InviteMapper.toDto(invite))
                .toList();

        return ResponseEntity.ok(inviteDtos);
    }
}
