package project.teaming.member.dto;

/** 이메일 관련 **/
public record EmailCheckRequest(
		String email,
		String authNum
) {
}
