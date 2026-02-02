package project.teaming.invite.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import project.teaming.invite.dto.AcceptInviteRequestDto;
import project.teaming.invite.dto.InviteRequestDto;
import project.teaming.invite.entity.Invite;

import java.util.List;

public interface InviteService {
    void sendInvite(@AuthenticationPrincipal UserDetails userDetails, InviteRequestDto dto);
    void acceptInvite(UserDetails userDetails, AcceptInviteRequestDto dto);
    void refuseInvite(UserDetails userDetails, AcceptInviteRequestDto dto);
    List<Invite> getAllInvitesByUsername(UserDetails userDetails);
    Invite findInviteByIdOrElseThrow(Long id);
}
