package bxnd.sori.service;

import bxnd.sori.Jwt.JwtProvider;
import bxnd.sori.dto.login.LoginRequest;
import bxnd.sori.dto.signup.SignupRequest;
import bxnd.sori.dto.signup.SignupResponse;
import bxnd.sori.entity.Member;
import bxnd.sori.exception.errorcode.AuthErrorCode;
import bxnd.sori.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public SignupResponse signup(SignupRequest request) {
        if (memberRepository.findByUserNm(request.username()).isPresent()) {
            // throw new RuntimeException("이미 존재하는 사용자입니다.");
            throw AuthErrorCode.ALREADY_EXISTS_USERNAME.defaultException();
        }

        if(memberRepository.findByEmail(request.email()).isPresent()) {
            throw AuthErrorCode.ALREADY_EXISTS_EMAIL.defaultException();
        }

        Member member = Member.builder()
                .userNm(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .role("USER")
                .build();

        memberRepository.save(member);

        return new SignupResponse();
    }

    public String login(LoginRequest request) {
        Member member = memberRepository.findByUserNm(request.username())
                .orElseThrow(AuthErrorCode.USER_NOT_FOUND::defaultException);

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw AuthErrorCode.INVALID_CREDENTIALS.defaultException();
        }

        return jwtProvider.createAccessToken(member.getUserNm());
    }
}