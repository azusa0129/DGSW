package project.teaming.project.dto;

import project.teaming.member.entity.Member;
import project.teaming.project.entity.Project;

import java.util.stream.Collectors;

public record ProjectResponse(
        Integer id,
        String title,
        String content,
        String projectManager,
        String projectMember
) {
    public static ProjectResponse of(Project project) {
        String memberNames = project.getProjectMember().stream()
                .map(Member::getUsername)
                .collect(Collectors.joining(", "));

        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getContent(),
                project.getProjectManager(),
                memberNames
        );
    }
}