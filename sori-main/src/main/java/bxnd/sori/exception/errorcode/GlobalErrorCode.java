package bxnd.sori.exception.errorcode;

import bxnd.sori.exception.exception.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
  INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_ARGUMENT("INVALID_ARGUMENT", "유효하지 않는 인자입니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;

  @Override
  public String defaultMessage() {
    return message;
  }

  @Override
  public HttpStatus defaultHttpStatus() {
    return httpStatus;
  }

  @Override
  public RuntimeException defaultException() {
    return new BusinessException(this);
  }

  @Override
  public RuntimeException defaultException(Throwable cause) {
    return new BusinessException(this, cause);
  }

  @Override
  public Exception defaultException(String message) {
    return new BusinessException(this, message);
  }
}
