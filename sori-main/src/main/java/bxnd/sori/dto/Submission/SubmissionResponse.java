package bxnd.sori.dto.Submission;

public record SubmissionResponse(
        Long id,
        String title,
        String content,
        String username,
        boolean Deadline,
        boolean isChecked
)
{ }
