package bxnd.sori.dto.assignment;


public record AssignmentResponse (
    Long id,
    String title,
    String content,
    String dueDate
) {}
