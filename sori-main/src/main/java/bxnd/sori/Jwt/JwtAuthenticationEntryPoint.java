package bxnd.sori.Jwt;

import bxnd.sori.exception.ApiResponseError;
import bxnd.sori.exception.errorcode.AuthErrorCode;
import bxnd.sori.exception.errorcode.ErrorCode;
import bxnd.sori.exception.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Throwable cause = authException.getCause();
        ErrorCode errorCode = AuthErrorCode.AUTHENTICATION_FAILED;

        BusinessException authException2 = new BusinessException(errorCode, cause);
        ApiResponseError errorResponse = ApiResponseError.of(authException2);

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
