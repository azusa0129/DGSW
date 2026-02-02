package project.teaming.member.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // 스프링 빈 등록
@RequiredArgsConstructor // 자동 의존성 주입
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberDetailService memberDetailService;


    @Override
    // protected는 같은 클래스 혹은 상속받은 클래스에서만 사용 가능(= 외부에서 호출하지 못함)
    protected void doFilterInternal(@NonNull HttpServletRequest request, // 클라이언트가 보낸 HTTP 요청
                                    @NonNull HttpServletResponse response, // 클라이언트로 보낼 HTTP 응답
                                    @NonNull FilterChain filterChain) throws ServletException, IOException { // 위의 요청과 응답을 넘기는 FilterChain객체 선언, 예외 던지기

        String token = jwtProvider.resolveToken(request); // 클라이언트가 보낸 HTTP요청 전체에서 토큰값을 꺼내고, jwtProvider에 따라서 토큰 추출

        if(token != null && jwtProvider.validateToken(token)) { // 만약 토큰값이 null이 아니고, 만료되지 않은 토큰인 경우 아래 실행 (= 토큰이 존재하고 유효한 경우에만)
            String username = jwtProvider.getUsername(token); // 토큰 안에 들어있는 username을 꺼냄
            UserDetails userDetails = memberDetailService.loadUserByUsername(username); // 위에서 끄집어낸 username이 DB에 있는지 조회함
            UsernamePasswordAuthenticationToken authentication = // 비밀번호 없이 권한만 있는 인증 객체 생성
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 요청을 기반으로 추가 정보를 넣음(IP, 세션ID 등)
            SecurityContextHolder.getContext().setAuthentication(authentication); // 인증된 객체를 SecurityContext에 저장해서 요청이 인증된 사용자로 동작하도록 설정
        }
        // HTTP요청과, 추출해낸 토큰이 들어있는 doFilter를 filterChain을 이용해서 넘김
        filterChain.doFilter(request, response); // 인증이 끝난 HTTP요청과 응답을 다음 필터로 요청을 넘김
    }
}
