package project.teaming.member.dto;

import project.teaming.member.entity.Major;

public record SignUpRequest(
        String username,
        String name,
        String password,
        String email,
        int grade,
        Major mainMajor,
        Major subMajor
) {
}
