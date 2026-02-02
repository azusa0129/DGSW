package project.teaming.invite.dto;

public record InviteResponseDto (
        Integer id,
        String projectManagerUsername,
        String projectMemberUsername,
        boolean accepted
) {

}
