package project.teaming.member.jwt;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.stereotype.Component;
import project.teaming.member.dto.CreateTokenRequest;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    @Getter
    private final SecretKey key; // 토큰에 서명할 비밀키
    private final long validity = 3600000; // 토큰이 유효한 시간, 밀리초 기준, 3600000 = 1시간

    public JwtProvider() {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .load();

        String secret = dotenv.get("JWT_SECRET");

        if (secret == null || secret.isEmpty()) {
            throw new IllegalStateException("JWT_SECRET가 잘못되었거나 찾을 수 없습니다.");
        }

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public String createToken(CreateTokenRequest request) { // 토큰 생성, Member 받아 JWT토큰으로 만들고 이를 리턴함
        Date now = new Date(); // 현재시간
        Date exp = new Date(now.getTime() + validity); // 만료시간 = 현재시간 + 아까 정한 validity(1시간)

        return Jwts
                .builder()
                .subject(request.username())

                .claim("name", request.name())
                .claim("grade", request.grade())
//                .claim("role", request.role())

                .issuedAt(now) // 현재 시간
                .expiration(exp) // 만료 시간
                .signWith(key) // HS256알고리즘 사용 및 키를 이용해 서명
                .compact(); // 결과적으로 문자열 형태의 토큰값 생성
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser() // 토큰 안에 있는 정보를 꺼내기 위한 parser생성
                .verifyWith(key) // 토큰의 성명을 확인하기 위한 비밀 키
                .build()
                .parseSignedClaims(token) // 토큰의 내용을 검사하고 해석함 (토큰을 파싱함)
                .getPayload(); // 토큰에 있는 payload(내용)값을 꺼냄
        return claims.getSubject(); // JWT 토큰 안에 있는 값을 문자열타입으로 꺼냄
    } // claims는 실제 토큰에 담겨있는 정보(payload)임


    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("role", String.class);
    }

    public boolean validateToken(String token) { // 토큰 검증 및 예외
        try {
            Jws <Claims> jwsClaims = Jwts.parser()
                    .verifyWith(key) // 비밀 키를 이용해 서명 검증
                    .build() // 객체 생성
                    .parseSignedClaims(token); // 서명된 JWT토큰 검증 및 추출

            Claims claims = jwsClaims. getPayload();
            Date exp = claims.getExpiration();

            Date now = new Date();
            return exp != null && exp.after(now); // 만약 토큰의 만료시간이 null이 아니고 현재시간 보다 이후면 true반환

        } catch (Exception e) { // 모든 예외에 대해 false 반환
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            return jwt.substring(7);
        }
        return null;
    }
}