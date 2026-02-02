package project.teaming.project.dto;

public record DeleteProjectRequest(
        Integer id,
        String title,
        String content
) {
}
