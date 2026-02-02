package bxnd.sori.exception.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  String getCode();
  String defaultMessage();
  HttpStatus defaultHttpStatus();
  Exception defaultException();
  Exception defaultException(Throwable cause);
  Exception defaultException(String message);
}
