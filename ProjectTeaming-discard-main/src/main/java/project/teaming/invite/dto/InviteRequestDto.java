package project.teaming.invite.dto;

public record InviteRequestDto (
        String projectMemberUsername,
        Integer projectId
) {

}