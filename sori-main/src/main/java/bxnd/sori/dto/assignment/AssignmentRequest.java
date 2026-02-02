package bxnd.sori.dto.assignment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AssignmentRequest (
    @NotBlank(message = "과제 제목은 필수 입력 값입니다.")
    String title,
    @NotBlank(message = "과제 내용은 필수 입력 값입니다.")
    String content,
    @NotBlank(message = "마감 기한은 필수 입력 값입니다.")
    String dueDate
) {}
