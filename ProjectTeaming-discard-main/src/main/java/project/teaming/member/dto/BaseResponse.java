package project.teaming.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

/** 이메일 보내는거와 관련있음 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseResponse {
	private boolean success;
	private String message;
	private int status;

	// 성공 응답
	public static BaseResponse ok(String message) {
		return BaseResponse.<Void>builder()
				.success(true)
				.message(message)
				.status(HttpStatus.OK.value())
				.build();
	}

	// 실패 응답
	public static <T> BaseResponse of(HttpStatus status, String message) {
		return BaseResponse.<T>builder()
				.success(false)
				.message(message)
				.status(status.value())
				.build();
	}
}
