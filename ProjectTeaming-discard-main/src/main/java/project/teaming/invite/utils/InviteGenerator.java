package project.teaming.invite.utils;

import org.springframework.stereotype.Component;
import project.teaming.invite.entity.Invite;
import project.teaming.member.entity.Member;
import project.teaming.project.entity.Project;

@Component
public class InviteGenerator {
    public Invite generateInvite(Member manager, Member invitee, Project project) {
        return new Invite(manager, invitee, project);
    }
}
