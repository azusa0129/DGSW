package project.teaming.project.dto;

public record UpdateProject(
        Integer id,
        String title,
        String content,
        String projectManager,
        String projectMember
) {
}
